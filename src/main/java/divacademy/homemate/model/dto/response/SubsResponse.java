package divacademy.homemate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SubsResponse {
    private long id;
    private String type;
    private String description;
    private double amount;
    private int count;
    private boolean confirm;
}
