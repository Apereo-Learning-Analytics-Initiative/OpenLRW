package unicon.matthews.oneroster.exception;


/**
 * Represents a generic HTTP response for exceptions.
 *
 * @author stalele
 *
 */
public class ExceptionResponse {

	private String description;

	public ExceptionResponse(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
