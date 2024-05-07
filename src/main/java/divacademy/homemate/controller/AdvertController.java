package divacademy.homemate.controller;

import divacademy.homemate.model.dto.request.*;
import divacademy.homemate.model.dto.response.*;
import divacademy.homemate.service.AdvertService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/adverts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdvertController {
    private final AdvertService advertService;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid AdvertRequest advertRequest) {
        return advertService.create(advertRequest);
    }

    @GetMapping
    public ResponseEntity<Page<AdvertsResponse>> getAll(
            @RequestParam(required = false) Double firstPrice,
            @RequestParam(required = false) Double secondPrice,
            @RequestParam(required = false) Long genderId,
            @RequestParam(required = false) Long buildingTypeId,
            @RequestParam(required = false) Long propertyTypeId,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "0") int page) {
        return advertService.getAll(new AdvertFilterRequest(firstPrice, secondPrice, genderId, buildingTypeId, propertyTypeId), page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertResponse> getById(@PathVariable long id) {
        return advertService.getById(id);
    }

    @GetMapping("/connection/{advertId}")
    public ResponseEntity<ConnectionResponse> getConnection(@PathVariable long advertId) {
        return advertService.getConnection(advertId);
    }

    @PostMapping("/property-type")
    public ResponseEntity<MessageResponse> createPropertyType(@RequestBody  @Valid AdvertPropertyTypeRequest propertyTypeRequest) {
        return advertService.createProperty(propertyTypeRequest);
    }

    @GetMapping("/property-type")
    public ResponseEntity<List<PropertyTypeResponse>> getProperties() {
        return advertService.getAllProperties();
    }

    @PostMapping("/advert-type")
    public ResponseEntity<MessageResponse> createAdvertType(@RequestBody @Valid AdvertTypeRequest advertTypeRequest) {
        return advertService.createAdvertType(advertTypeRequest);
    }

    @GetMapping("/advert-type")
    public ResponseEntity<List<AdvertTypeResponse>> getAllAdvertType() {
        return advertService.getAllAdvertType();
    }

    @PostMapping("/building-types")
    public ResponseEntity<MessageResponse> createBuildingType(@RequestBody @Valid BuildingTypeRequest buildingTypeRequest) {
        return advertService.createBuildingType(buildingTypeRequest);
    }

    @GetMapping("/building-types")
    public ResponseEntity<List<BuildingTypeResponse>> getAllBuildingType() {
        return advertService.getAllBuildingTypes();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteAdvert(@PathVariable long id){
        return advertService.delete(id);
    }
}
