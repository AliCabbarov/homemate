package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.Gender;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class AdvertDetailResponse {
    private long id;
    private LocalDate moveTime;
    private int livingCount;
    private int searchingCount;
    private GenderResponse gender;
}
