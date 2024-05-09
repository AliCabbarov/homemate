package divacademy.homemate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class PropertyTypeResponse {
    private long id;
    private String name;
    private String description;
    private boolean available;
}
