package unicon.matthews.oneroster.integration;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/** This class is created only for the integration testing
 * @author stalele
 *
 */
@JsonDeserialize(builder = Token.Builder.class)
public class Token implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  String token;
  String refreshToken;
  int status;
  String message;
  String errorCode;
  String timestamp;
  String error;
  
  private Token() {}

  public static class Builder {
    private Token _token = new Token();
    public Builder withToken(String token) {
      _token.token = token;
      return this;
    }

    public Builder withRefreshToken(String refreshToken) {
      _token.refreshToken = refreshToken;
      return this;
    }
    
    public Builder withStatus(int status) {
      _token.status = status;
      return this;
    }
    
    public Builder withMessage(String message) {
      _token.message = message;
      return this;
    }
    
    public Builder withErrorCode(String errorCode) {
      _token.errorCode = errorCode;
      return this;
    }
    
    public Builder withError(String error) {
      _token.error = error;
      return this;
    }
  
    public Builder withTimestamp(String timeStamp) {
      _token.timestamp = timeStamp;
      return this;
    }

    public Token build() {
      return _token;
    }
  }
}
