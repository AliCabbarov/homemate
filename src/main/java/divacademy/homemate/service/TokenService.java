package divacademy.homemate.service;

import divacademy.homemate.model.entity.Token;
import divacademy.homemate.model.entity.User;

public interface TokenService {
    String generateAndSaveRefreshToken(User user);
    String generateAndSaveConfirmationToken(User user);

    String generateAndSaveForgottenToken(User user);
}
