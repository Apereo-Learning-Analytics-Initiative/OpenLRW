package unicon.matthews.oneroster.exception;

/**
 * Represents an HTTP 404 Not Found Error for Result objects.
 *
 * @author stalele
 *
 */
public class ResultNotFoundException extends OneRosterNotFoundException {

  private static final long serialVersionUID = 1L;

  public ResultNotFoundException(String message) {
    super(message);
  }

}
