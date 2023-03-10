package com.ngu.pizzamania.Exception;

import com.ngu.pizzamania.Model.ErrorResponse;

public class OutOfOrderQuantityException extends RuntimeException {
    ErrorResponse errorResponse;

    /**
     * @param errorResponse
     */
    public OutOfOrderQuantityException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    /**
     * @return
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
