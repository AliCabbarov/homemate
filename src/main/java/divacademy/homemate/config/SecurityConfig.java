package divacademy.homemate.config;

import divacademy.homemate.entrypoint.CustomAccessDeniedHandler;
import divacademy.homemate.entrypoint.CustomAuthenticationEntryPoint;
import divacademy.homemate.filter.JwtAuthenticationFilter;
import divacademy.homemate.model.constant.EndPoints;
import divacademy.homemate.model.constant.Permissions;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final OAuth2ApplicationConfigurer oAuth2ApplicationConfigurer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                EndPoints.SWAGGER_V2,
                                EndPoints.SWAGGER_V3,
                                EndPoints.SWAGGER_V3_ALL,
                                EndPoints.SWAGGER_RESOURCE,
                                EndPoints.SWAGGER_RESOURCE_ALL,
                                EndPoints.SWAGGER_CONFIGURATION_UI,
                                EndPoints.SWAGGER_CONFIGURATION_SECURITY,
                                EndPoints.SWAGGER_UI,
                                EndPoints.SWAGGER_WEBJARS,
                                EndPoints.SWAGGER_UI_HTML).permitAll()

                        .requestMatchers(HttpMethod.POST, EndPoints.POST_USER).anonymous()
                        .requestMatchers(HttpMethod.GET, EndPoints.GET_CONFIRMATION_USER).anonymous()
                        .requestMatchers(HttpMethod.POST, EndPoints.POST_USER_DETAILS).hasAuthority(Permissions.POST_USER_DETAILS)
                        .requestMatchers(HttpMethod.GET, EndPoints.GET_ALL_USER).hasAuthority(Permissions.GET_ALL_USER)
                        .requestMatchers(HttpMethod.GET, EndPoints.GET_USER_BY_ID).hasAuthority(Permissions.GET_USER_BY_ID)
                        .requestMatchers(HttpMethod.DELETE, EndPoints.DELETE_USER_BY_ID).hasAuthority(Permissions.DELETE_USER_BY_ID)
                        .requestMatchers(HttpMethod.GET, EndPoints.GET_FORGOT_PASSWORD).anonymous()
                        .requestMatchers(HttpMethod.POST, EndPoints.POST_RESET_PASSWORD_CONFIRM).anonymous()
                        .requestMatchers(HttpMethod.POST, EndPoints.POST_CHANGE_PASSWORD).hasAuthority(Permissions.POST_CHANGE_PASSWORD)
                        .requestMatchers(HttpMethod.POST, EndPoints.POST_SUBSCRIPTION).hasAuthority(Permissions.POST_SUBSCRIPTION)
                        .requestMatchers(HttpMethod.POST, EndPoints.POST_SUBSCRIPTION_CONFIRM).hasAuthority(Permissions.POST_SUBSCRIPTION_CONFIRM)

                        .requestMatchers(HttpMethod.POST,EndPoints.POST_ADMIN).hasAuthority(Permissions.POST_ADMIN)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_ROLE).hasAuthority(Permissions.POST_ROLE)
                        .requestMatchers(HttpMethod.PUT,EndPoints.PUT_ROLE).hasAuthority(Permissions.PUT_ROLE)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_SUBSCRIPTION_TYPE).hasAuthority(Permissions.POST_SUBSCRIPTION_TYPE)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_SUBSCRIPTION_TYPE).hasAuthority(Permissions.GET_SUBSCRIPTION_TYPE)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_GENDER_TYPE).hasAuthority(Permissions.POST_GENDER_TYPE)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_GENDER_TYPE).hasAuthority(Permissions.GET_GENDER_TYPE)
                        .requestMatchers(HttpMethod.PATCH,EndPoints.PATCH_CHANGE_USER_ROLE).hasAuthority(Permissions.PATCH_CHANGE_USER_ROLE)

                        .requestMatchers(HttpMethod.POST,EndPoints.POST_ADVERT).hasAuthority(Permissions.POST_ADVERT)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_ALL_ADVERT).permitAll()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_ADVERT).permitAll()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_CONNECTION).hasAuthority(Permissions.GET_CONNECTION)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_PROPERTY_TYPE).hasAuthority(Permissions.POST_PROPERTY_TYPE)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_ALL_PROPERTY_TYPE).hasAuthority(Permissions.GET_ALL_PROPERTY_TYPE)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_BUILDING_TYPE).hasAuthority(Permissions.POST_BUILDING_TYPE)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_ALL_BUILDING_TYPE).hasAuthority(Permissions.GET_ALL_BUILDING_TYPE)
                        .requestMatchers(HttpMethod.POST,EndPoints.POST_ADVERT_TYPE).hasAuthority(Permissions.POST_ADVERT_TYPE)
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_ALL_ADVERT_TYPE).hasAuthority(Permissions.GET_ALL_ADVERT_TYPE)
                        .requestMatchers(HttpMethod.DELETE,EndPoints.DELETE_ADVERT_BY_ID).hasAuthority(Permissions.DELETE_ADVERT_BY_ID)

                        .requestMatchers(HttpMethod.POST,EndPoints.POST_LOGIN).anonymous()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_TOKEN_BY_REFRESH).anonymous()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_OAUTH_LOGIN).anonymous()

                        .requestMatchers(HttpMethod.POST,EndPoints.POST_FILE).authenticated()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_FILES_URLS).authenticated()
                        .requestMatchers(HttpMethod.GET,EndPoints.GET_FILES).authenticated()

                        .anyRequest().denyAll())


                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> httpSecurityOAuth2LoginConfigurer.successHandler(oAuth2ApplicationConfigurer.successHandler()))
                .exceptionHandling(handling-> handling
                        .authenticationEntryPoint(authenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler()))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return security.build();
    }
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/", corsConfiguration);
        return urlBasedCorsConfigurationSource;
    }


}
