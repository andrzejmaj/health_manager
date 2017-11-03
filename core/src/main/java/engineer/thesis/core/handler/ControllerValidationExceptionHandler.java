package engineer.thesis.core.handler;

import engineer.thesis.core.exception.PasswordNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String processValidation(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        return bindingResult.getFieldErrors().stream().map(err -> err.getField() + " " + err.getDefaultMessage()).collect(Collectors.joining("\n"));
    }

    @ExceptionHandler(PasswordNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String processPasswordException(PasswordNotValidException ex) {
        return ex.getMessage();
    }
}
