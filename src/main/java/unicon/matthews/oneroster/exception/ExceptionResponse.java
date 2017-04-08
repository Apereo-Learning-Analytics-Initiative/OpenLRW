package unicon.matthews.oneroster.exception;


/**
 * @author stalele
 *
 */
public class ExceptionResponse {

	public ExceptionResponse(String description) {
		super();
		this.description = description;
	}

	private String description;

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
