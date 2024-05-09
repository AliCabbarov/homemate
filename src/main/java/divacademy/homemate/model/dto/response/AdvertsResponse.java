package divacademy.homemate.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
@ToString
public class AdvertsResponse {
    private long id;
    private String description;
    private double amount;
    private LocalDate expiredDay;
}
