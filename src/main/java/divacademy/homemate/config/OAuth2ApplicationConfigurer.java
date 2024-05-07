package divacademy.homemate.config;

import divacademy.homemate.model.entity.User;
import divacademy.homemate.repository.RoleRepository;
import divacademy.homemate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class OAuth2ApplicationConfigurer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Value("${app.host.ip}")
    private String APP_HOST;
    @Value("${app.host.port}")
    private String APP_PORT;

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

            Map<String, Object> attributes = oAuth2AuthenticationToken.getPrincipal().getAttributes();

            log.info("user details information load OAuth2: {}", attributes);
            String email = (String) attributes.get("email");

            User user = userRepository.findByEmailOrPhoneNumber(email).orElseGet(() ->
                    new User(email,
                            null,
                            null,
                            roleRepository.findRoleByName("ROLE_USER").orElseThrow()));

            user.setEnabled(true);
            User savedUser = userRepository.save(user);


            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = getUsernamePasswordAuthenticationToken(request, savedUser, email);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(usernamePasswordAuthenticationToken);


            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            redirectStrategy.sendRedirect(request, response, "http://" + APP_HOST + ":" + APP_PORT + "/api/v1/auth/oauth-login");

        };
    }

    private static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(HttpServletRequest request, User savedUser, String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        savedUser.getEmail(),
                        null,
                        savedUser.getAuthorities()
                );

        usernamePasswordAuthenticationToken.setDetails(new AuthenticationDetails(
                new WebAuthenticationDetails(request),
                savedUser.getId(),
                username
        ));
        return usernamePasswordAuthenticationToken;
    }
}
