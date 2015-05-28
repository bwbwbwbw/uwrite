package edu.tongji.error;

import org.slf4j.Logger;

/**
 * Created by Breezewish on 5/27/15.
 */
public class RestError {

    private Boolean error = true;

    private String type = "error.generic";

    private String message = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getError() {
        return error;
    }

    public RestError() {

    }

    public RestError(Exception error) {
        this.setMessage("Error " + error.getClass() + ": " + error.getMessage());
        this.setType("error.exception");
    }
}
