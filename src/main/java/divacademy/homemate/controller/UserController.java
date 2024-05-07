package divacademy.homemate.controller;

import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.model.dto.response.SubsResponse;
import divacademy.homemate.model.dto.response.UserResponse;
import divacademy.homemate.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<MessageResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return userService.register(userRequest);
    }

    @GetMapping("/confirmation")
    public ResponseEntity<MessageResponse> confirm(@RequestParam String token) {
        return userService.confirm(token);
    }

    @PostMapping("/details")
    public ResponseEntity<MessageResponse> setUserDetails(@RequestBody @Valid UserDetailRequest userDetailRequest) {
        return userService.setUserDetail(userDetailRequest);
    }

    @GetMapping
    ResponseEntity<Page<UserResponse>> getAllUser(@RequestParam(required = false) int pageNumber, @RequestParam(required = false) int pageSize) {
        return userService.getAll(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        return userService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<MessageResponse> deleteUserById(@PathVariable long id) {
        return userService.deleteById(id);
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestParam String emailOrPhone) {
        return userService.forgotPassword(emailOrPhone);
    }

    @PostMapping("/reset-password-confirm")
    public ResponseEntity<MessageResponse> forgotPasswordConfirm(@RequestParam String token,
                                                                 @RequestBody PasswordRequest passwordRequest) {
        return userService.confirmPassword(passwordRequest, token);
    }

    @PostMapping("/change-password")
    public ResponseEntity<MessageResponse> changePassword(@RequestBody PasswordRequest passwordRequest) {
        return userService.changePassword(passwordRequest);
    }


    @PostMapping("/subs")
    public ResponseEntity<SubsResponse> createSubscription(@RequestBody SubsRequest subsRequest) {
        return userService.createSubs(subsRequest);
    }

    @PostMapping("/subs-confirm/{subs-id}")
    public ResponseEntity<?> confirmSubs(@PathVariable(name = "subs-id") long subsId, @RequestBody @Valid CardRequest cardRequest) {
        return userService.confirmSubs(subsId, cardRequest);
    }
}
