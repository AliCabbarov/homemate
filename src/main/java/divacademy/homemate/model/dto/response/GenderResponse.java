package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.TableDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class GenderResponse {
    private long id;
    private String name;
    private boolean available;
    private TableDetail tableDetail;
}
