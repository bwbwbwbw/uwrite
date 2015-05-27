package edu.tongji.error;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by Breezewish on 5/27/15.
 */
public class ValidateError extends RestError {

    public ValidateError(BindException error) {
        String msg = "";
        List<FieldError> errors = error.getBindingResult().getFieldErrors();
        for (FieldError fieldError : errors) {
            msg += fieldError.getField() + ": " + fieldError.getDefaultMessage() + "\n";
        }
        this.setMessage(msg);
        this.setType("error.validate");
    }

}
