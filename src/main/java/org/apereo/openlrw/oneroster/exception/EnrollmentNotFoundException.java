package org.apereo.openlrw.oneroster.exception;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class EnrollmentNotFoundException extends OneRosterNotFoundException {

  private static final long serialVersionUID = 1L;

  public EnrollmentNotFoundException(String message) {
    super(message);
  }
}
