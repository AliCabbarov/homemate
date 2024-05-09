package divacademy.homemate.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@AllArgsConstructor
@ToString
public class PropertyDetailRequest {
    private String address;
    private int floorCount;
    private int floor;
    private boolean gas;
    private boolean elevator;
    private Long buildingTypeId;
    private List<Long> propertyTypesId;
}
