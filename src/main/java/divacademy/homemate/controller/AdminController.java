package divacademy.homemate.controller;

import divacademy.homemate.model.dto.request.GenderRequest;
import divacademy.homemate.model.dto.request.RoleRequest;
import divacademy.homemate.model.dto.request.SubscriptionTypeRequest;
import divacademy.homemate.model.dto.request.UserRequest;
import divacademy.homemate.model.dto.response.GenderResponse;
import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.model.dto.response.SubsTypeResponse;
import divacademy.homemate.service.AdminService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admins")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {
    private final AdminService adminService;
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid UserRequest request){
        return adminService.create(request);
    }

    @PostMapping("/role")
    public ResponseEntity<MessageResponse> createRole(@RequestBody @Valid RoleRequest roleRequest) {
        return adminService.createRole(roleRequest);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<MessageResponse> updateRole(@PathVariable long id,
                                                      @RequestBody @Valid RoleRequest roleRequest) {
        return adminService.updateRole(id, roleRequest);
    }

    @PostMapping("/subscription-type")
    public ResponseEntity<MessageResponse> createSubsType(@RequestBody @Valid SubscriptionTypeRequest subscriptionRequest) {
        return adminService.createSubsType(subscriptionRequest);
    }

    @GetMapping("/subscription-type")
    public ResponseEntity<List<SubsTypeResponse>> getAllSubsType() {
        return adminService.getSubType();
    }

    @GetMapping("/genders")
    public ResponseEntity<List<GenderResponse>> getAllGender() {
        return adminService.getAllAvailableGender();
    }

    @PostMapping("/genders")
    public ResponseEntity<MessageResponse> createGender(@RequestBody @Valid GenderRequest genderRequest) {
        return adminService.createGender(genderRequest);

    }
    @PatchMapping("/change-role")
    public ResponseEntity<MessageResponse> changeRoleByUserId(@RequestParam long userId,
                                                              @RequestParam long roleId){
        return adminService.changeRoleByUserId(userId,roleId);
    }
}
