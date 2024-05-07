package divacademy.homemate.service;

import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.model.dto.response.SubsResponse;
import divacademy.homemate.model.dto.response.UserResponse;
import divacademy.homemate.model.entity.Role;
import divacademy.homemate.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<MessageResponse> register(UserRequest userRequest);
    ResponseEntity<MessageResponse> registerUserByRole(UserRequest userRequest, Role role);

    ResponseEntity<MessageResponse> setUserDetail(UserDetailRequest userDetailRequest);

    ResponseEntity<MessageResponse> confirm(String token);


    ResponseEntity<MessageResponse> changePassword(PasswordRequest passwordRequest);

    ResponseEntity<MessageResponse> confirmPassword(PasswordRequest passwordRequest, String token);

    ResponseEntity<MessageResponse> forgotPassword(String emailOrPhone);

    ResponseEntity<Page<UserResponse>> getAll(int pageNumber,int pageSize);

    ResponseEntity<UserResponse> getById(long id);


    ResponseEntity<?> confirmSubs(long subsId, CardRequest cardRequest);

    ResponseEntity<SubsResponse> createSubs(SubsRequest subsRequest);


    ResponseEntity<MessageResponse> deleteById(long id);
    User getAuthenticatedUser();
}
