package divacademy.homemate.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
    @Value("${app.host.ip}")
    private String APP_HOST;
    @Value("${app.host.port}")
    private String APP_PORT;

    public String confirmEmailSubjectBuilder(String token, String username) {
        return "<p> Hi, " + username + ", <p>" +
                "<p>Thank you for registering with us," +
                "Please, follow the link below to complete your registration.<p>" +
                "<a href=\"" + createConfirmUrl(token) + "\">Verify your email to active your account<a>" +
                "<p> Thank you <br> Users Registration Portal Service";

    }

    private String createConfirmUrl(String token) {
        return "http://" + APP_HOST + ":" + APP_PORT + "/api/v1/users/confirmation?token=" + token;
    }

    private String createConfirmForgottenPass(String token) {
        return "http://" + APP_HOST + ":" + APP_PORT + "/api/v1/users/reset-password-confirm?token=" + token;
    }

    public String forgottenPasswordEmailSubjectBuilder(String token, String username) {
        return "<p> Hi, " + username + ", <p>" +
                "<p>Welcome will have been changed with this link according to which new password request!!" +
                "Please, click the link for change which your password and  complete your process which change of password.<p>" +
                "<a href=\"" + createConfirmForgottenPass(token) + "\">Verify your email to change password<a>";
    }
}
