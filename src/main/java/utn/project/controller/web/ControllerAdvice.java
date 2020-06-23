package utn.project.controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import utn.project.dto.ErrorResponseDto;
import utn.project.exceptions.InvalidLoginException;
import utn.project.exceptions.UserException;
import utn.project.exceptions.ValidationException;

import java.text.ParseException;

public class ControllerAdvice extends ResponseEntityExceptionHandler{

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserException.class)
    public ErrorResponseDto handleUserNotExists(UserException exc) {
        return new ErrorResponseDto(3, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(4, "Not valid dates");
    }


}
