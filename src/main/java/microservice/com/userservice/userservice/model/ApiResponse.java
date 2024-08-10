package microservice.com.userservice.userservice.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean success;
    private HttpStatus status;

}
