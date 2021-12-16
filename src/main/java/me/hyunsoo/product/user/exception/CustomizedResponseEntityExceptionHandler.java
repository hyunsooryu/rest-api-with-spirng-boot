package me.hyunsoo.product.user.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.time.Instant;

//ERROR 처리

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    //전체적인 서버 에러 - 500X
    @ExceptionHandler(Exception.class)
    public final ResponseEntity handleAllExceptions(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder().
                timeStamp(Date.from(Instant.now()))
                .message(ex.getMessage())
                .details(request.getDescription(false)).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
    }

    //찾지 못했을 때
    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity handleNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timeStamp(Date.from(Instant.now()))
                .message(ex.getMessage())
                .details(request.getDescription(false)).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(NotLoginException.class)
    public final ResponseEntity handleNotLoginException(Exception ex, WebRequest request){
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/user/login")
                .build().toUri();

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .newLocation(location)
                .timeStamp(Date.from(Instant.now()))
                .message(ex.getMessage())
                .details(request.getDescription(false)).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
    }

    //handleMethodArgumentNotValid -> ResponseEntity에 대해

    //ResponseEntityExceptionHandler를 재정의해서 사용할 수 있다.!!! -2021-12-16
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ExceptionResponse exceptionResponse = ExceptionResponse.builder()
                .timeStamp(Date.from(Instant.now()))
                .message("Validation Failed")
                .details(ex.getBindingResult().toString()).build();

        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
