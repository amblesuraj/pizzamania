package com.ngu.pizzamania.Exception;

import com.ngu.pizzamania.Model.ErrorResponse;

public class ResourceNotFoundException extends RuntimeException {
    ErrorResponse errorResponse;

    /**
     * @param errorResponse
     */
    public ResourceNotFoundException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    /**
     * @return
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
