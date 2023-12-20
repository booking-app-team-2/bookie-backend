package booking_app_team_2.bookie.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpTransferException extends RuntimeException{
    private final HttpStatus httpStatus;

    public HttpTransferException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
