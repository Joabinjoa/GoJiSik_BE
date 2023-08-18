package com.likelion.nsu.gojisik.handler;

import com.likelion.nsu.gojisik.dto.ErrorDto;
import com.likelion.nsu.gojisik.exception.DuplicateMemberException;
import com.likelion.nsu.gojisik.exception.NotFoundMemberException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ResponseStatus(CONFLICT)
    @ResponseBody
    @ExceptionHandler(value = { DuplicateMemberException.class })
    protected ErrorDto conflict(RuntimeException ex, WebRequest request) {
        return new ErrorDto(CONFLICT.value(), ex.getMessage());
    }

    @ResponseStatus(FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(value = { NotFoundMemberException.class, AccessDeniedException.class })
    protected ErrorDto forbidden(RuntimeException ex, WebRequest request) {
        return new ErrorDto(FORBIDDEN.value(), ex.getMessage());
    }
}
