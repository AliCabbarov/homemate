package divacademy.homemate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BuildingTypeResponse {
    private long id;
    private String name;
    private String description;
    private boolean available;
}
