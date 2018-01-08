package unicon.matthews.oneroster.exception;


/**
 * Represents an HTTP exception response.
 *
 * @author stalele
 * @author xchopin
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
