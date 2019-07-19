package com.infopulse.exception.handler;

import com.infopulse.dto.ErrorInfo;
import com.infopulse.exception.UserAlreadyExsistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerService {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserAlreadyExsistsException.class})
    public ErrorInfo userExist(HttpServletRequest request, Exception ex) {
        return new ErrorInfo().setTimestamp(System.currentTimeMillis())
                .setMessage(ex.getMessage())
                .setDeveloperMessage(ex.toString());
    }

}
