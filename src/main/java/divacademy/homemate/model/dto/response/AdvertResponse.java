package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.AdvertType;
import divacademy.homemate.model.entity.TableDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
@Getter
@AllArgsConstructor
@ToString
public class AdvertResponse {
    private long id;
    private String description;
    private double amount;
    private List<AdvertTypeResponse> advertTypes;
    private AdvertDetailResponse advertDetail;
    private PropertyDetailResponse propertyDetail;
    private TableDetail tableDetail;
}
