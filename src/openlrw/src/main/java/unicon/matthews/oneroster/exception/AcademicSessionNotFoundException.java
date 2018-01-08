package unicon.matthews.oneroster.exception;

/**
 * Represents an HTTP 404 Not Found Error for AcademicSession objects.
 *
 * @author stalelue
 *
 */
public class AcademicSessionNotFoundException extends OneRosterNotFoundException {

    private static final long serialVersionUID = 1L;

    public AcademicSessionNotFoundException(String message) {
      super(message);
    }

}
