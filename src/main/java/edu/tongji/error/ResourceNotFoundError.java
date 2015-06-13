package edu.tongji.error;

/**
 * Created by Breezewish on 6/13/15.
 */
public class ResourceNotFoundError extends RestError {

    public ResourceNotFoundError(ResourceNotFoundException error) {
        this.setMessage(error.getMessage());
        this.setType("error.notfound");
    }
}
