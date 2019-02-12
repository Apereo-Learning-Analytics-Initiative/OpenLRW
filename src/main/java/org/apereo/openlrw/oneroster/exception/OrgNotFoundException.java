package org.apereo.openlrw.oneroster.exception;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class OrgNotFoundException extends OneRosterNotFoundException {

  private static final long serialVersionUID = 1L;

  public OrgNotFoundException(String message) {
    super(message);
  }
}
