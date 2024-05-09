package divacademy.homemate.model.dto.request;

import divacademy.homemate.model.entity.Gender;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@ToString
public class AdvertDetailRequest {
    private LocalDate moveTime;
    private int livingCount;
    private int searchingCount;
    private Long genderId;
}        