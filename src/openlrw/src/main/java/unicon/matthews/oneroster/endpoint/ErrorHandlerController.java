package unicon.matthews.oneroster.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import unicon.matthews.oneroster.exception.ExceptionResponse;
import unicon.matthews.oneroster.exception.OneRosterNotFoundException;
import unicon.matthews.oneroster.exception.ResultNotFoundException;

/**
 * @author stalele
 *
 */
@ControllerAdvice
public class ErrorHandlerController {
  private static Logger logger = LoggerFactory.getLogger(ErrorHandlerController.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGeneralException(Exception e) {
		
		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		logger.error(e.getMessage(),e);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}
	
	@ExceptionHandler(ResultNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResultNotFoundException(OneRosterNotFoundException e) {

		ExceptionResponse response = new ExceptionResponse(e.getMessage());
		logger.error(e.getMessage(),e);
		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);

	}

}
