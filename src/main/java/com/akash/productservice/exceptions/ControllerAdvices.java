package com.akash.productservice.exceptions;

import com.akash.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdvices {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException notFoundExcpetion) {
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.NOT_FOUND, notFoundExcpetion.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UnableToCreateProductException.class)
    public ResponseEntity<ExceptionDto> handleUnableCreateProductException(UnableToCreateProductException unableToCreateProductException) {
        return new ResponseEntity(
                new ExceptionDto(HttpStatus.BAD_REQUEST, unableToCreateProductException.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
