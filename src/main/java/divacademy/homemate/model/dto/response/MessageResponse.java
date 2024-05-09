package divacademy.homemate.model.dto.response;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Map;
import java.util.Objects;

@Getter
@AllArgsConstructor
@ToString
public class MessageResponse {
    private HttpStatus status;
    private String message;
    private String path;

    public static MessageResponse of(String message, HttpStatus httpStatus) {
        return new MessageResponse(httpStatus, message, getRequest().getRequestURI());
    }


    private static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))).getRequest();
    }
}
