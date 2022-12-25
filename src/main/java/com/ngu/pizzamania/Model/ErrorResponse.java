package com.ngu.pizzamania.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String message;
    private HttpStatus httpStatus;
//    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss")
    private Integer statusCode;
    private Date timeStamp;
}
