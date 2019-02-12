package org.apereo.openlrw.oneroster.exception;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class UserNotFoundException extends OneRosterNotFoundException{

  private static final long serialVersionUID = 1L;

  public UserNotFoundException(String message) {
    super(message);
  }
}
