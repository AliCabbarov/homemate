package divacademy.homemate.model.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConnectionResponse {
    private long advertId;
    private String phone;
    private String email;
}
