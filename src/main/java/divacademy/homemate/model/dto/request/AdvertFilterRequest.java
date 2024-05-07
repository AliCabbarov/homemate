package divacademy.homemate.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdvertFilterRequest {
    private Double firstPrice;
    private Double secondPrice;
    private Long genderId;
    private Long buildingTypeId;
    private Long propertyTypeId;
}
