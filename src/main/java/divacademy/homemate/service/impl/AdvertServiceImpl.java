package divacademy.homemate.service.impl;

import divacademy.homemate.mapper.*;
import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.*;
import divacademy.homemate.model.entity.*;
import divacademy.homemate.model.enums.Exceptions;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.repository.*;
import divacademy.homemate.service.AdvertService;
import divacademy.homemate.service.UserService;
import divacademy.homemate.specification.AdvertSpecification;
import divacademy.homemate.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static divacademy.homemate.model.enums.Exceptions.NOT_FOUND;
import static divacademy.homemate.model.enums.Messages.MESSAGE_SUCCESSFULLY;

@Service
@RequiredArgsConstructor
public class AdvertServiceImpl implements AdvertService {
    private final AdvertMapper advertMapper;
    private final AdvertTypeRepository advertTypeRepository;
    private final AdvertDetailMapper advertDetailMapper;
    private final PropertyDetailMapper propertyDetailMapper;
    private final GenderRepository genderRepository;
    private final BuildingTypeRepository buildingTypeRepository;
    private final PropertyTypeRepository propertyTypeRepository;
    private final UserService userService;
    private final AdvertRepository advertRepository;
    private final MessageUtil messageUtil;
    private final PropertyTypeMapper propertyTypeMapper;
    private final AdvertTypeMapper advertTypeMapper;
    private final BuildingTypeMapper buildingTypeMapper;
    private final AdvertSpecification advertSpecification;
    private final SubscriptionRepository subscriptionRepository;
    @Override
    @Transactional
    public ResponseEntity<ConnectionResponse> getConnection(long advertId) {
        Advert advert = advertRepository.findByIdAndActive(advertId, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), advertId));

        User user = advert.getUser();
        User authenticatedUser = userService.getAuthenticatedUser();

        List<Subscription> allSubscriptionList = subscriptionRepository.findByUser(authenticatedUser);

        ConnectionResponse connectionResponse = new ConnectionResponse(advertId, user.getPhoneNumber(), user.getEmail());

        for (Subscription subscription : allSubscriptionList) {
            if (subscription.isItInSubs(advert)) {
                return ResponseEntity.ok(connectionResponse);
            }
        }

        Subscription subscription = subscriptionRepository.findByUserAndAvailableAndConfirm(authenticatedUser, true, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), "available subscription"));

        subscription.addAdvert(advert);

        return ResponseEntity.ok(connectionResponse);
    }

    @Override
    public ResponseEntity<List<BuildingTypeResponse>> getAllBuildingTypes() {
        List<BuildingType> buildingTypes = buildingTypeRepository.findAll();
        List<BuildingTypeResponse> buildingTypeResponses = buildingTypes.stream()
                .map(buildingTypeMapper::map).toList();
        return ResponseEntity.ok(buildingTypeResponses);
    }

    @Override
    public ResponseEntity<MessageResponse> createBuildingType(BuildingTypeRequest buildingTypeRequest) {
        BuildingType buildingType = buildingTypeMapper.map(buildingTypeRequest, new BuildingType());
        buildingTypeRepository.save(buildingType);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<Page<AdvertsResponse>> getAll(AdvertFilterRequest dto, int page, int size) {
        Specification<Advert> specification;

        if (isAllSearch(dto)) specification =
                advertSpecification.hasFirstPrice(dto.getFirstPrice())
                        .and(advertSpecification.hasSecondPrice(dto.getSecondPrice()))
                        .and(advertSpecification.hasBuildingTypeId(dto.getBuildingTypeId()))
                        .and(advertSpecification.hasGenderId(dto.getGenderId()))
                        .and(advertSpecification.isActive())
                        .and(advertSpecification.hasPropertyTypeId(dto.getPropertyTypeId()));

        else specification = advertSpecification.none();

        Page<Advert> advertPage = advertRepository.findAll(specification, PageRequest.of(page, size));
        Page<AdvertsResponse> advertResponsePage = advertPage.map(advertMapper::mapToAdverts);
        return ResponseEntity.ok(advertResponsePage);
    }

    @Override
    public ResponseEntity<MessageResponse> createAdvertType(AdvertTypeRequest advertTypeRequest) {
        AdvertType advertType = advertTypeMapper.map(advertTypeRequest, new AdvertType());
        advertTypeRepository.save(advertType);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<List<AdvertTypeResponse>> getAllAdvertType() {
        List<AdvertType> advertTypes = advertTypeRepository.findAll();
        List<AdvertTypeResponse> advertTypeResponses = advertTypes.stream().map(advertTypeMapper::map).toList();
        return ResponseEntity.ok(advertTypeResponses);
    }

    @Override
    public ResponseEntity<List<PropertyTypeResponse>> getAllProperties() {
        List<PropertyType> propertyTypes = propertyTypeRepository.findByAvailable(true);
        List<PropertyTypeResponse> propertyTypeResponses = propertyTypes.stream().map(propertyTypeMapper::map).toList();
        return ResponseEntity.ok(propertyTypeResponses);
    }

    @Override
    public ResponseEntity<MessageResponse> createProperty(AdvertPropertyTypeRequest propertyTypeRequest) {
        PropertyType propertyType = propertyTypeMapper.map(propertyTypeRequest, new PropertyType());
        propertyTypeRepository.save(propertyType);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> create(AdvertRequest advertRequest) {


        List<AdvertType> advertTypes = advertTypeRepository.findAllById(advertRequest.getAdvertTypesId());

        Long genderId = advertRequest.getAdvertDetail().getGenderId();
        Gender gender = genderRepository.findById(genderId).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), genderId));
        AdvertDetail advertDetail = advertDetailMapper.map(advertRequest.getAdvertDetail(), new AdvertDetail(gender));


        PropertyDetailRequest requestPropertyDetail = advertRequest.getPropertyDetail();
        BuildingType buildingType = buildingTypeRepository.findById(requestPropertyDetail.getBuildingTypeId()).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), requestPropertyDetail.getBuildingTypeId()));
        List<PropertyType> propertyTypeList = propertyTypeRepository.findAllById(requestPropertyDetail.getPropertyTypesId());

        PropertyDetail propertyDetail = propertyDetailMapper.map(advertRequest.getPropertyDetail(), new PropertyDetail(buildingType, propertyTypeList));


        User authenticatedUser = userService.getAuthenticatedUser();

        Advert advert = advertMapper.map(advertRequest, new Advert(advertTypes, advertDetail, propertyDetail, authenticatedUser));

        advertRepository.save(advert);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<AdvertResponse> getById(long id) {
        Advert advert = advertRepository.findByIdAndActive(id,true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), id));
        AdvertResponse advertResponse = advertMapper.map(advert);
        return ResponseEntity.ok(advertResponse);
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> delete(long id) {
        User authenticatedUser = userService.getAuthenticatedUser();
        boolean hasPermissionForDeleteById = authenticatedUser.getAuthorities()
                .stream().anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        Advert advert = advertRepository.findByIdAndActive(id,true).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(NOT_FOUND.getMessage(), NOT_FOUND.getStatus()), id));

        if (hasPermissionForDeleteById){
        advert.setActive(false);
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
        }

        if (advert.getUser().getId() == authenticatedUser.getId()) {
            advert.setActive(false);
            return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
        }
        throw new AccessDeniedException(Exceptions.ACCESS_DENIED_EXCEPTION.getMessage());
    }

    private boolean isAllSearch(Object dto) {
        return dto != null;
    }
}
