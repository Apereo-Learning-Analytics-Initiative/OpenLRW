package org.apereo.openlrw.common.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apereo.openlrw.caliper.exception.CaliperNotFoundException;
import org.apereo.openlrw.caliper.exception.EventNotFoundException;
import org.apereo.openlrw.common.exception.BadRequestException;
import org.apereo.openlrw.common.exception.MessageResponse;
import org.apereo.openlrw.oneroster.exception.OneRosterNotFoundException;
import org.apereo.openlrw.xapi.exception.InvalidXAPIRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 * @author groybal <groybal@unicon.net>
 */
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private String buildDate() {
        return LocalDateTime.now().format(formatter);
    }

    private void logException(Exception e) {
        logger.debug("Exception message: {}", e.getMessage());
    }

    private void logError(MessageResponse error) {
        logger.error("Returning error: {}", error);
    }

    private void log(Exception e, MessageResponse error) {
        logException(e);
        logError(error);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MessageResponse genericExceptionHandler(HttpServletRequest request, Exception e) {
        MessageResponse response = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public MessageResponse handleNotImplementedException(HttpServletRequest request, NotImplementedException e) {
        MessageResponse response = new MessageResponse(HttpStatus.NOT_IMPLEMENTED, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNotImplementedException(HttpServletRequest request, ChangeSetPersister.NotFoundException e) {
        MessageResponse response = new MessageResponse(HttpStatus.NOT_FOUND, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(InvalidXAPIRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleInvalidRestRequestException(HttpServletRequest request, InvalidXAPIRequestException e) {
        MessageResponse response = new MessageResponse(HttpStatus.BAD_REQUEST, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError oe : e.getBindingResult().getAllErrors()) {
            if (oe instanceof FieldError) {
                final FieldError fe = (FieldError)oe;
                final String msg = String.format(
                        "Field error in object '%s' on field '%s': rejected value [%s].", fe.getObjectName(), fe.getField(), fe.getRejectedValue());
                errorMessages.add(msg);
            } else {
                errorMessages.add(oe.toString());
            }
        }
        MessageResponse response = new MessageResponse(HttpStatus.BAD_REQUEST, buildDate(), request, errorMessages);
        log(e, response);
        return response;
    }

    @ExceptionHandler(org.springframework.web.util.NestedServletException.class)
    public void test(NestedServletException e) {
        Throwable t = e.getCause();
        logger.debug("*************************** "+t.getClass().getName());
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleUnrecognizedPropertyException(final HttpServletRequest request, UnrecognizedPropertyException e) {
        String errorMessage = String.format("Unrecognized property: [%s].", e.getPropertyName());
        MessageResponse response = new MessageResponse(HttpStatus.BAD_REQUEST, buildDate(), request, errorMessage);
        log(e, response);
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleHttpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException e) {
        if (e.getCause() instanceof UnrecognizedPropertyException) {
            return handleUnrecognizedPropertyException(request, (UnrecognizedPropertyException)e.getCause());
        } else {
            MessageResponse response;
            if (e.getCause() instanceof JsonProcessingException) {
                JsonProcessingException jpe = (JsonProcessingException)e.getCause();
                response = new MessageResponse(HttpStatus.BAD_REQUEST, buildDate(), request, jpe.getOriginalMessage());
            } else {
                response = new MessageResponse(HttpStatus.BAD_REQUEST, buildDate(), request, e.getMessage());
            }
            log(e, response);
            return response;
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public MessageResponse runTimeExceptionHandler(HttpServletRequest request, Exception e) {
        if (e.getCause() instanceof OneRosterNotFoundException)
            return oneRosterExceptionHandler(request, e);
        else if (e.getCause() instanceof CaliperNotFoundException)
            return caliperExceptionHandler(request, e);
        else
            return genericExceptionHandler(request, e);
    }

    @ExceptionHandler(OneRosterNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse oneRosterExceptionHandler(HttpServletRequest request, Exception e) {
        MessageResponse response = new MessageResponse(HttpStatus.NOT_FOUND, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(CaliperNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse caliperExceptionHandler(HttpServletRequest request, Exception e) {
        MessageResponse response = new MessageResponse(HttpStatus.NOT_FOUND, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse eventExceptionHandler(HttpServletRequest request, Exception e) {
        MessageResponse response = new MessageResponse(HttpStatus.NOT_FOUND, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public MessageResponse genericBadRequestHandler(HttpServletRequest request, Exception e) {
        MessageResponse response = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR, buildDate(), request, e.getLocalizedMessage());
        log(e, response);
        return response;
    }

}
