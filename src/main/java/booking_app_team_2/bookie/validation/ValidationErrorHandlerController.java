package booking_app_team_2.bookie.validation;

import booking_app_team_2.bookie.exception.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandlerController {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorMessage> handleValidationError(MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < errors.size(); i++) {
            stringBuilder.append(errors.get(i).getDefaultMessage());

            if (i != errors.size() - 1)
                stringBuilder.append("\n");
        }

        return new ResponseEntity<>(new ErrorMessage(stringBuilder.toString()), HttpStatus.BAD_REQUEST);
    }
}
