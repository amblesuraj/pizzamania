package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobleExceptionController {

    /**
     * @param ex
     * @param webRequest
     * @return
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handlePizzaTypeNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorResponse errorResponse = ex.getErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(new Date());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> MethodArgsNotValidException(MethodArgumentNotValidException ex, WebRequest webRequest){
        Map<String,String> errorResponse = new HashMap<>();
                ex.getBindingResult()
                .getFieldErrors().forEach(
                    error -> errorResponse.put(error.getField(),error.getDefaultMessage())
                );
        return errorResponse;
    }



}
