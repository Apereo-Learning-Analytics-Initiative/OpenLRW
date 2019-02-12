package org.apereo.openlrw.caliper.exception;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class CaliperNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public CaliperNotFoundException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}