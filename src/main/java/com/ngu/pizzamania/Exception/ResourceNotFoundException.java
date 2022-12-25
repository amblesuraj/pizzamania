package com.ngu.pizzamania.Exception;

import com.ngu.pizzamania.Model.ErrorResponse;

public class ResourceNotFoundException extends RuntimeException {
    ErrorResponse errorResponse;
    public ResourceNotFoundException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
