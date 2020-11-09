package br.com.hogwarts.api;

import br.com.hogwarts.exception.CharacterNotFoundException;
import br.com.hogwarts.exception.InvalidHouseKeyException;
import br.com.hogwarts.model.Character;
import br.com.hogwarts.view.ErroView;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@ControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(CharacterNotFoundException.class)
    public ErroView handleCharacterNotFoundException(CharacterNotFoundException ex){
        return new ErroView("Requested character not found");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(InvalidHouseKeyException.class)
    public ErroView handleInvalidHouseKeyException(InvalidHouseKeyException ex){
        return new ErroView("Invalid house key supplied");
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErroView handleException(Exception ex) {
        return new ErroView(ex.getMessage());
    }

}
