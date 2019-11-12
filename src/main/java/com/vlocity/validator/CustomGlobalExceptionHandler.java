package com.vlocity.validator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorResponse> errors = new ArrayList<ErrorResponse>();
        
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        if (ex.getBindingResult().hasErrors()) {
            List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
            for (Object object : objectErrors) {
                if(object instanceof FieldError) {
                    FieldError fe = (FieldError) object;
                    errors.add(new ErrorResponse(fe.getField(), fe.getRejectedValue(), fe.getCode(), fe.getDefaultMessage()));
                }else if(object instanceof ObjectError) {
                	ObjectError oe = (ObjectError) object;
                	errors.add(new ErrorResponse(oe.getCode(), oe.getDefaultMessage()));
                }
            }
            body.put("errors", errors);
        }

        return new ResponseEntity<>(body, headers, status);

    }

}
