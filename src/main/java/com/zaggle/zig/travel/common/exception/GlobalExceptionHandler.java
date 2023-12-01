package com.zaggle.zig.travel.common.exception;

import com.zaggle.zig.travel.common.dto.ErrorResponse;
import com.zaggle.zig.travel.common.dto.error.ValidationError;
import com.zaggle.zig.travel.common.helper.CommonMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private CommonMethods commonMethods;
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentException(MethodArgumentNotValidException exception) {
        ArrayList<ValidationError> errorDTOList = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            ValidationError errorDTO = new ValidationError(error.getField(), error.getDefaultMessage());
            errorDTOList.add(errorDTO);
        });

        return commonMethods.buildErrorResponse(errorDTOList, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorResponse handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

    @ExceptionHandler(FatalBeanException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFatalBeanException(FatalBeanException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

    @ExceptionHandler(JpaSystemException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleJpaSystemException(JpaSystemException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotWritableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse handleHttpMessageNotWritableException(HttpMessageNotWritableException exception) {
        return commonMethods.buildErrorResponse(null, exception.getMessage());
    }

}
