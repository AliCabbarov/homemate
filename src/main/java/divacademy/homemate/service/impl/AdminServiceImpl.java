package divacademy.homemate.service.impl;

import divacademy.homemate.mapper.GenderMapper;
import divacademy.homemate.mapper.SubscriptionTypeMapper;
import divacademy.homemate.model.dto.request.UserRequest;
import divacademy.homemate.model.dto.response.*;
import divacademy.homemate.model.dto.request.GenderRequest;
import divacademy.homemate.model.dto.request.RoleRequest;
import divacademy.homemate.model.dto.request.SubscriptionTypeRequest;
import divacademy.homemate.model.entity.*;
import divacademy.homemate.model.enums.Exceptions;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.repository.*;
import divacademy.homemate.service.AdminService;
import divacademy.homemate.service.UserService;
import divacademy.homemate.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static divacademy.homemate.model.enums.Exceptions.NOT_FOUND;
import static divacademy.homemate.model.enums.Messages.MESSAGE_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final MessageUtil messageUtil;
    private final SubscriptionTypeMapper subscriptionTypeMapper;
    private final SubsTypeRepository subsTypeRepository;
    private final GenderMapper genderMapper;
    private final GenderRepository genderRepository;
    private final UserRepository userRepository;
    private final UserService userService;


    @Override
    public ResponseEntity<MessageResponse> create(UserRequest request) {
        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN").orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), "ROLE_ADMIN"));

        return userService.registerUserByRole(request, adminRole);
    }

    @Override
    public ResponseEntity<MessageResponse> createRole(RoleRequest roleRequest) {
        List<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermissionsId());
        Role role = new Role(roleRequest.getName(), permissions);
        roleRepository.save(role);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<MessageResponse> updateRole(long id, RoleRequest roleRequest) {
        Role role = roleRepository.findById(id).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(Exceptions.NOT_FOUND.getMessage(), Exceptions.NOT_FOUND.getStatus()), id));
        List<Permission> permissions = permissionRepository.findAllById(roleRequest.getPermissionsId());
        Role newRole = new Role(roleRequest.getName(), permissions);
        newRole.setId(role.getId());
        roleRepository.save(newRole);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<MessageResponse> createSubsType(SubscriptionTypeRequest subscriptionRequest) {
        SubsType subsType = subscriptionTypeMapper.map(subscriptionRequest, new SubsType());
        subsTypeRepository.save(subsType);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), HttpStatus.CREATED));
    }

    @Override
    public ResponseEntity<List<SubsTypeResponse>> getSubType() {
        List<SubsType> subsTypes = subsTypeRepository.findAll();
        List<SubsTypeResponse> subsTypeResponses = subsTypes.stream()
                .map(subscriptionTypeMapper::map)
                .toList();
        return ResponseEntity.ok(subsTypeResponses);
    }

    @Override
    public ResponseEntity<List<GenderResponse>> getAllAvailableGender() {
        List<Gender> genders = genderRepository.findAll();
        List<GenderResponse> genderResponses = genders.stream()
                .map(genderMapper::map)
                .toList();
        return ResponseEntity.ok(genderResponses);
    }

    @Override
    public ResponseEntity<MessageResponse> createGender(GenderRequest genderRequest) {
        Gender gender = genderMapper.map(genderRequest, new Gender());
        genderRepository.save(gender);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), HttpStatus.CREATED));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> changeRoleByUserId(long userId, long roleId) {
        User user = userRepository.findByIdAndEnabled(userId,true).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(Exceptions.NOT_FOUND.getMessage(), Exceptions.NOT_FOUND.getStatus()), userId));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(Exceptions.NOT_FOUND.getMessage(), Exceptions.NOT_FOUND.getStatus()), roleId));
        user.setRole(role);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), HttpStatus.CREATED));
    }
}
