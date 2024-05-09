package divacademy.homemate.model.dto.response;

import divacademy.homemate.model.entity.TableDetail;
import divacademy.homemate.model.entity.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserResponse {
    private long id;
    private String email;
    private String phoneNumber;
    private String lastLoginIp;
    private String role;
    private UserDetailResponse userDetail;
    private TableDetail tableDetail;
}
