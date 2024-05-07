package divacademy.homemate.service;

import divacademy.homemate.model.dto.request.UserRequest;
import divacademy.homemate.model.dto.response.SubsTypeResponse;
import divacademy.homemate.model.dto.request.GenderRequest;
import divacademy.homemate.model.dto.request.RoleRequest;
import divacademy.homemate.model.dto.request.SubscriptionTypeRequest;
import divacademy.homemate.model.dto.response.GenderResponse;
import divacademy.homemate.model.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {

    ResponseEntity<MessageResponse> create(UserRequest request);

    ResponseEntity<MessageResponse> createRole(RoleRequest roleRequest);

    ResponseEntity<MessageResponse> updateRole(long id, RoleRequest roleRequest);

    ResponseEntity<MessageResponse> createSubsType(SubscriptionTypeRequest subscriptionRequest);

    ResponseEntity<List<SubsTypeResponse>> getSubType();

    ResponseEntity<List<GenderResponse>> getAllAvailableGender();

    ResponseEntity<MessageResponse> createGender(GenderRequest genderRequest);

    ResponseEntity<MessageResponse> changeRoleByUserId(long userId, long roleId);
}
