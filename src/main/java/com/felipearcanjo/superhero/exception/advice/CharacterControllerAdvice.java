package com.felipearcanjo.superhero.exception.advice;

import com.felipearcanjo.superhero.dto.ErrorDTO;
import com.felipearcanjo.superhero.exception.custom.CharacterAlreadyExistsException;
import com.felipearcanjo.superhero.exception.custom.CharacterNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(basePackages = "com.felipearcanjo.superhero.controller")
public class CharacterControllerAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CharacterNotFoundException.class)
    public ErrorDTO handleCharacterNotFoundError(CharacterNotFoundException characterNotFoundException){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDTO.setMessage("Character Not Found");
        errorDTO.setTimestamp(LocalDateTime.now());
        errorDTO.setValidationErrors(getErrorsMap(List.of()));

        return errorDTO;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CharacterAlreadyExistsException.class)
    public ErrorDTO handleCharacterAlreadyExistsError(CharacterAlreadyExistsException characterAlreadyExistsException){
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDTO.setMessage("Character already exists with the given name and birthName");
        errorDTO.setTimestamp(LocalDateTime.now());
        errorDTO.setValidationErrors(getErrorsMap(List.of()));
        
        return errorDTO;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handleValidationErrors(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors()
                                                    .stream().map(FieldError::getDefaultMessage)
                                                    .toList();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDTO.setMessage("There Are Validation Errors");
        errorDTO.setTimestamp(LocalDateTime.now());
        errorDTO.setValidationErrors(getErrorsMap(errors));

        return errorDTO;
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("messages", errors);
        return errorResponse;
    }
}
