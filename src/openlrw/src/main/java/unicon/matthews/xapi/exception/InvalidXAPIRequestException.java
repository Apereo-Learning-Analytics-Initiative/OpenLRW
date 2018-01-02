/**
 * 
 */
package unicon.matthews.xapi.exception;


/**
 * Exception indicating that a received XAPI request was invalid.
 * @author Gary Roybal, groybal@unicon.net
 */
public class InvalidXAPIRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidXAPIRequestException() {
	}

	public InvalidXAPIRequestException(String message) {
		super(message);
	}

	public InvalidXAPIRequestException(Throwable cause) {
		super(cause);
	}

	public InvalidXAPIRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidXAPIRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
