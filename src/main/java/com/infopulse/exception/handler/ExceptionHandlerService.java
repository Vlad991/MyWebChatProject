package com.infopulse.exception.handler;

import com.infopulse.dto.ErrorInfo;
import com.infopulse.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerService {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserAlreadyExistsException.class,
            UserAlreadyBannedException.class,
            UserNotFoundException.class,
            UserLoginNotNullException.class,
            UserNotBannedException.class})
    @ResponseBody
    public ErrorInfo exceptionHandler(Exception ex) {
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString());
    }

}
