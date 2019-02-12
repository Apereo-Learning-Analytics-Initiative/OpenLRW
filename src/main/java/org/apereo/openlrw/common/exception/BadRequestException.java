package org.apereo.openlrw.common.exception;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public BadRequestException(String message) {
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
