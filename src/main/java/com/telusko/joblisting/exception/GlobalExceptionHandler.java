package com.telusko.joblisting.exception;


import com.telusko.joblisting.exception.code.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //Implementing custom exception (EntityNotFoundException) not defined in ResponseEntityExceptionHandler
    // Will handle case when result given by Database is null or empty
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .statusMessage(ErrorNames.ENTITY_NOT_FOUND.getName())
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    // Will handle case when there is validation error
    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleValidationError(ValidationException ex) {
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .statusMessage(ErrorNames.VALIDATION_ERROR.getName())
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    //when we are not able to save in database
    @ExceptionHandler(EntityNotSavedException.class)
    public final ResponseEntity<Object> handleEntityNotSaved(EntityNotSavedException ex) {
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusMessage(ErrorNames.FAILED_TO_SAVE_IN_DB.getName())
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    //when there is duplicate in database
    @ExceptionHandler(DuplicateEntityException.class)
    public final ResponseEntity<Object> handleDuplicateEntity(DuplicateEntityException ex) {
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.CONFLICT)
                .statusMessage(ErrorNames.DUPLICATE_FOUND.getName())
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    @ExceptionHandler(RedisException.class)
    public final ResponseEntity<Object> handleRedisException(RedisException ex) {
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .statusMessage(ErrorNames.VALIDATION_ERROR.getName())
                .debugMessage(ex.getMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    // This will be thrown everytime there is JSON not readable exception , this is overriding method from extended class
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        // we take the exception and convert into ResponseEntity<ApiException>
        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .statusMessage(ErrorNames.MALFORMED_JSON_REQUEST.getName())
                .debugMessage(ex.getLocalizedMessage())
                .build();
        return buildResponseEntity(apiException);
    }

    // For handling @Valid validation exceptions
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        String errors = ex.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((error1, error2) -> error1 + " , " + error2).orElse("No Validation Found");

        ApiException apiException = ApiException.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .statusMessage(ErrorNames.VALIDATION_ERROR.getName())
                .debugMessage(errors)
                .build();

        return buildResponseEntity(apiException);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiException apiException) {
        return new ResponseEntity<>(apiException, apiException.getStatusCode());
    }
}
