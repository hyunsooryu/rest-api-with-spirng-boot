package me.hyunsoo.product.user.exception;

import lombok.RequiredArgsConstructor;


public class NotLoginException extends RuntimeException{

    public NotLoginException(String message){
        super(message);
    }
}
