package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.BuildingType;
import divacademy.homemate.model.entity.PropertyType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@AllArgsConstructor
@ToString
public class PropertyDetailResponse {
    private long id;
    private String address;
    private int floorCount;
    private int floor;
    private boolean gas;
    private boolean elevator;
    private BuildingTypeResponse buildingType;
    private List<PropertyTypeResponse> propertyTypes;
}
