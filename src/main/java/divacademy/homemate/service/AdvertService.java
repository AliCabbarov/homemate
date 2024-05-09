package divacademy.homemate.service;

import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.*;
import divacademy.homemate.model.entity.Advert;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdvertService {
    ResponseEntity<ConnectionResponse> getConnection(long advertId);

    ResponseEntity<List<BuildingTypeResponse>> getAllBuildingTypes();

    ResponseEntity<MessageResponse> createBuildingType(BuildingTypeRequest buildingTypeRequest);

    ResponseEntity<Page<AdvertsResponse>> getAll(AdvertFilterRequest filterRequest, int page, int size);

    ResponseEntity<MessageResponse> createAdvertType(AdvertTypeRequest advertTypeRequest);

    ResponseEntity<List<AdvertTypeResponse>> getAllAdvertType();

    ResponseEntity<List<PropertyTypeResponse>> getAllProperties();

    ResponseEntity<MessageResponse> createProperty(AdvertPropertyTypeRequest propertyTypeRequest);

    ResponseEntity<MessageResponse> create(AdvertRequest advertRequest);

    ResponseEntity<AdvertResponse> getById(long id);

    ResponseEntity<MessageResponse> delete(long id);

    Advert findById(Long id);
}
