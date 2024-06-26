package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.TableDetail;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@AllArgsConstructor
@Getter
@ToString
public class UserDetailResponse {
    private long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private TableDetail tableDetail;
}
