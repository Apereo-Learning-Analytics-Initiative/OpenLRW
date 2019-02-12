package org.apereo.openlrw.oneroster.exception;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class LineItemNotFoundException extends OneRosterNotFoundException {

  private static final long serialVersionUID = 1L;

  public LineItemNotFoundException(String message) {
    super(message);
  }
}
