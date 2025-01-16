package lu.crx.financing.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<String> handlePaymentMethodExistsException(HttpServletRequest request,
            MethodArgumentNotValidException exception) {
        log.error("GlobalExceptionHandler: MethodArgumentNotValidException happened for the request: {}, reason: {}",
                request.getRequestURL().toString(), exception.getMessage());
        return ResponseEntity.status(BAD_REQUEST).body(exception.getMessage());
    }
}
