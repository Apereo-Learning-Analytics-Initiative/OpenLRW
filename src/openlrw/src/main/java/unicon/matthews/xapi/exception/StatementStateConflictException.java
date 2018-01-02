/**
 * 
 */
package unicon.matthews.xapi.exception;


/**
 * Exception indicating that a received XAPI request cannot be fulfilled due to a conflict with the state of the target statement.
 * @author Gary Roybal, groybal@unicon.net
 */
public class StatementStateConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StatementStateConflictException() {
	}

	public StatementStateConflictException(String message) {
		super(message);
	}

	public StatementStateConflictException(Throwable cause) {
		super(cause);
	}

	public StatementStateConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public StatementStateConflictException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
