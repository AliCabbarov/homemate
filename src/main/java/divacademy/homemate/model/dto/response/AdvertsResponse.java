package divacademy.homemate.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class AdvertsResponse {
    private long id;
    private String description;
    private double amount;
    private LocalDate expiredDay;
}
