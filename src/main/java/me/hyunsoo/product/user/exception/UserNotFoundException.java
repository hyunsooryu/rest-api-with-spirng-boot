package me.hyunsoo.product.user.exception;


// 2XX -> Ok
// 3XX -> Redirect 301(Permanent) / 302(Instant)
// 4XX -> Client
// 5XX -> Server Error

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//NOT_FOUND 404
//@ResponseStatus를 Exception위에 붙여주면, 코드를 조절 가능
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
