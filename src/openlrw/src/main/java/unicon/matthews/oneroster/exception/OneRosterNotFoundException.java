package unicon.matthews.oneroster.exception;

/**
 * Represents a generic HTTP 404 Not Found Error for OneRoster models.
 *
 * @author ggilbert
 *
 */
public class OneRosterNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  private String message;

  public OneRosterNotFoundException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
