package divacademy.homemate.provider;

import divacademy.homemate.config.AuthenticationDetails;
import divacademy.homemate.model.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class DaoAuthenticationProviderDetails extends DaoAuthenticationProvider {
    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken successAuthentication =
                (UsernamePasswordAuthenticationToken) super.createSuccessAuthentication(principal, authentication, userDetails);
        User user = (User) userDetails;
        successAuthentication.setDetails(new AuthenticationDetails(null,
                user.getId(),
                user.getEmail() != null ? user.getEmail() : user.getPhoneNumber()));
        return successAuthentication;
    }
}
