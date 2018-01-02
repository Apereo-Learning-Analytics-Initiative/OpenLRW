package unicon.matthews.oneroster.exception;

public class OneRosterNotFoundException extends Exception {

  private static final long serialVersionUID = 1L;

  private String message;

  public OneRosterNotFoundException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
