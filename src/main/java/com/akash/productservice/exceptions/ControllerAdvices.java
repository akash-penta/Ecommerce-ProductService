package com.akash.productservice.exceptions;

import com.akash.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundExcpetion.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundExcpetion notFoundExcpetion) {
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.NOT_FOUND, notFoundExcpetion.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
