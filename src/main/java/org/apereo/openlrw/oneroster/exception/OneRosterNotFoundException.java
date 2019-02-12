package org.apereo.openlrw.oneroster.exception;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class OneRosterNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message;

  public OneRosterNotFoundException(String message) {
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
