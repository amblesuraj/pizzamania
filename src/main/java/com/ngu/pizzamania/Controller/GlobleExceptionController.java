package com.ngu.pizzamania.Controller;

import com.ngu.pizzamania.Exception.ResourceNotFoundException;
import com.ngu.pizzamania.Model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class GlobleExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorResponse handlePizzaTypeNotFoundException(ResourceNotFoundException ex, WebRequest webRequest){
        ErrorResponse errorResponse = ex.getErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setTimeStamp(new Date());
        return errorResponse;
    }

}
