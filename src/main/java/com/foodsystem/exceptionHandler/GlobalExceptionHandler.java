package com.foodsystem.exceptionHandler;

import com.foodsystem.builder.ApiResponse;
import com.foodsystem.exceptions.ResourceNotFoundExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ApiResponse resourceNotFoundExceptions(ResourceNotFoundExceptions e)
    {
        return new ApiResponse.Builder().msg(e.getMessage()).code(HttpStatus.NOT_FOUND).success(false).build();
    }
}
