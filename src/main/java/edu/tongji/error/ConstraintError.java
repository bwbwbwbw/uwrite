package edu.tongji.error;

/**
 * Created by Breezewish on 5/28/15.
 */
public class ConstraintError extends RestError {

    public ConstraintError(ConstraintException error) {
        this.setMessage(error.getMessage());
        this.setType("error.constraint");
    }

}
