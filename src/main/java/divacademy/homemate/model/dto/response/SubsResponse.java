package divacademy.homemate.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SubsResponse {
    private long id;
    private String type;
    private String description;
    private double amount;
    private int count;
    private boolean confirm;
}
