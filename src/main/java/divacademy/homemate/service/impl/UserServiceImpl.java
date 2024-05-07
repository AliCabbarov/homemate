package divacademy.homemate.service.impl;

import divacademy.homemate.client.StripeClient;
import divacademy.homemate.config.AuthenticationDetails;
import divacademy.homemate.mapper.SubscriptionMapper;
import divacademy.homemate.mapper.UserDetailMapper;
import divacademy.homemate.mapper.UserMapper;
import divacademy.homemate.model.constant.Permissions;
import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.ExceptionResponse;
import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.model.dto.response.SubsResponse;
import divacademy.homemate.model.dto.response.UserResponse;
import divacademy.homemate.model.entity.*;
import divacademy.homemate.model.enums.Exceptions;
import divacademy.homemate.model.enums.Messages;
import divacademy.homemate.model.enums.TokenType;
import divacademy.homemate.model.exception.ApplicationException;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.repository.*;
import divacademy.homemate.service.EmailService;
import divacademy.homemate.service.TokenService;
import divacademy.homemate.service.UserService;
import divacademy.homemate.util.EmailUtil;
import divacademy.homemate.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static divacademy.homemate.model.enums.Exceptions.NOT_FOUND;
import static divacademy.homemate.model.enums.Messages.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final EmailUtil emailUtil;
    private final MessageUtil messageUtil;
    private final TokenRepository tokenRepository;
    private final UserDetailMapper userDetailMapper;
    private final UserDetailRepository userDetailRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final SubsTypeRepository subsTypeRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final StripeClient stripeClient;
    @Value("${token.bearer.stripe}")
    private String stripeBearer;


    @Override
    public ResponseEntity<MessageResponse> register(UserRequest userRequest) {

        Role userRole = roleRepository.findRoleByName("ROLE_USER").orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), "ROLE_USER"));
        return registerUserByRole(userRequest, userRole);

    }

    public ResponseEntity<MessageResponse> registerUserByRole(UserRequest userRequest, Role role) {
        User mappedUser = userMapper.map(userRequest, new User());
        mappedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        mappedUser.setRole(role);

        User savedUser = userRepository.save(mappedUser);

        String confirmToken = tokenService.generateAndSaveConfirmationToken(savedUser);

        emailService.sendTo(savedUser.getEmail(), emailUtil.confirmEmailSubjectBuilder(confirmToken, "Customer"));

        return ResponseEntity.ok(MessageResponse.of(
                messageUtil.getMessage(MESSAGE_SEND_TO_EMAIL.getMessage()), MESSAGE_SEND_TO_EMAIL.getStatus()));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> confirm(String token) {
        Token foundedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.CONFIRMATION, token, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), token));
        foundedToken.isUsableOrElseThrow();
        User user = foundedToken.getUser();
        user.setEnabled(true);
        foundedToken.unusable();

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<MessageResponse> setUserDetail(UserDetailRequest userDetailRequest) {
        User authenticatedUser = getAuthenticatedUser();
        userDetailRepository.findByUser(authenticatedUser).ifPresentOrElse(userDetail -> {
            userDetail.getTableDetail().update();
            userDetailMapper.map(userDetailRequest, userDetail);
            userDetailRepository.save(userDetail);
        }, () -> {
            UserDetail userDetail = userDetailMapper.map(userDetailRequest, new UserDetail());
            userDetail.setUser(authenticatedUser);
            authenticatedUser.setUserDetail(userDetail);
            userRepository.save(authenticatedUser);
            userDetailRepository.save(userDetail);
        });
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<Page<UserResponse>> getAll(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<UserResponse> responsePage = userRepository.findAll(pageRequest)
                .map(userMapper::map);

        return ResponseEntity.ok(responsePage);
    }

    @Override
    public ResponseEntity<UserResponse> getById(long id) {
        boolean hasPermissionGetById = getAuthenticatedUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Permissions.GET_USER_BY_ID));
        if (hasPermissionGetById) {
            return ResponseEntity.ok(userMapper.map(getUserById(id)));
        } else {
            return ResponseEntity.ok(userMapper.map(getAuthenticatedUser()));
        }
    }

    @Override
    public ResponseEntity<MessageResponse> deleteById(long id) {
        boolean hasPermissionGetById = getAuthenticatedUser().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Permissions.DELETE_USER_BY_ID));
        User user;
        if (hasPermissionGetById) {
            user = getUserById(id);
        } else {
            user = getAuthenticatedUser();
        }
        user.setEnabled(false);
        userRepository.save(user);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<SubsResponse> createSubs(SubsRequest subsRequest) {
        User user = getAuthenticatedUser();
        SubsType subsType = subsTypeRepository
                .findByIdAndAvailable(subsRequest.getSubsTypeId(), true)
                .orElseThrow(() ->
                        NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()),
                                subsRequest.getSubsTypeId()));

        Subscription subscription = subscriptionRepository
                .findByUserAndAvailable(user, true).orElseGet(() ->
                        new Subscription(
                                user,
                                subsType
                        ));

        if (subscription.isAvailable() && subscription.isConfirm() && subscription.getCount() > 0) {
            throw ApplicationException.of(ExceptionResponse.of(Exceptions.ALREADY_YET_SUBS_EXCEPTION.getMessage(), Exceptions.ALREADY_YET_SUBS_EXCEPTION.getStatus()));
        }

        Subscription savedSubs = subscriptionRepository.save(subscription);

        SubsResponse subsResponse = subscriptionMapper.map(savedSubs);

        return ResponseEntity.ok(subsResponse);
    }

    @Override
    public ResponseEntity<?> confirmSubs(long subsId, CardRequest cardRequest) {
        Subscription subscription = subscriptionRepository.findByConfirmAndAvailableAndId(false, true, subsId).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()),
                        subsId));

        ResponseEntity<Object> payment = stripeClient.payment(
                stripeBearer,
                "pm_card_visa",
                "azn",
                String.valueOf(subscription.getAmount())
        );

        if (payment.getStatusCode().equals(HttpStatus.OK)) {
            subscription.setConfirm(true);
            subscriptionRepository.save(subscription);
        }

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<MessageResponse> forgotPassword(String emailOrPhone) {
        User user = userRepository.findByEmailOrPhoneNumber(emailOrPhone).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), emailOrPhone));

        String forgottenToken = tokenService.generateAndSaveForgottenToken(user);
        String subject = emailUtil.forgottenPasswordEmailSubjectBuilder(forgottenToken, "Customer");

        emailService.sendTo(user.getEmail(), subject);

        return ResponseEntity.ok(MessageResponse.of(Messages.MESSAGE_FORGOTTEN_PASSWORD_LINK_SENT_EMAIL.getMessage(), MESSAGE_FORGOTTEN_PASSWORD_LINK_SENT_EMAIL.getStatus()));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> confirmPassword(PasswordRequest passwordRequest, String token) {
        Token foundedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.FORGOTTEN_PASSWORD, token, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), token)
        );
        User user = foundedToken.getUser();
        user.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        foundedToken.unusable();
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<MessageResponse> changePassword(PasswordRequest passwordRequest) {
        User authenticatedUser = getAuthenticatedUser();

        authenticatedUser.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));

        userRepository.save(authenticatedUser);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    public User getAuthenticatedUser() {
        return userRepository.findByEmailOrPhoneNumber(((AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getUserEmailOrPhone()).orElseThrow();
    }

    private User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), id));
    }
}



