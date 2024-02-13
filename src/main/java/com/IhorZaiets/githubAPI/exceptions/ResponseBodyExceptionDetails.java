package com.IhorZaiets.githubAPI.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBodyExceptionDetails {

    private Integer statusCode;
    private Timestamp timestamp;
    private String message;
    private String trace;

    public ResponseBodyExceptionDetails(Integer statusCode, Timestamp timestamp, String message, String trace) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.trace = trace;
    }

    public ResponseBodyExceptionDetails(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ResponseBodyExceptionDetails(HttpStatus statusCode, String message) {
        this.statusCode = statusCode.value();
        this.message = message;
    }
}
