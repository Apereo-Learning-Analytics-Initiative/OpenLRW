/**
 * Copyright 2014 Unicon (R) Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0

 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 */
package unicon.matthews.xapi.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.NestedServletException;

import unicon.matthews.xapi.exception.InvalidXAPIRequestException;
import unicon.matthews.xapi.exception.StatementStateConflictException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

/**
 * Class that handles exceptions generated from XAPI controllers, returning proper HTTP codes and useful messages.
 * @author Gary Roybal, groybal@unicon.net
 */
@ControllerAdvice(annotations = RestController.class)
public class XAPIExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger(XAPIExceptionHandlerAdvice.class);

    public XAPIExceptionHandlerAdvice() {
    }

    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    public XAPIErrorInfo handleNotImplementedException(final HttpServletRequest request, final NotImplementedException e) {
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.NOT_IMPLEMENTED, request, e.getLocalizedMessage());
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public XAPIErrorInfo handleNotImplementedException(final HttpServletRequest request, final NotFoundException e) {
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.NOT_FOUND, request, e.getLocalizedMessage());
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(InvalidXAPIRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public XAPIErrorInfo handleInvalidRestRequestException(final HttpServletRequest request, final InvalidXAPIRequestException e) {
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.BAD_REQUEST, request, e.getLocalizedMessage());
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(StatementStateConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    public XAPIErrorInfo handleStatementStateConflictException(final HttpServletRequest request, final HttpServletResponse response, final StatementStateConflictException e) {
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.CONFLICT, request, e.getLocalizedMessage());
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public XAPIErrorInfo handleMethodArgumentNotValidException(final HttpServletRequest request, MethodArgumentNotValidException e) {
        final List<String> errorMessages = new ArrayList<String>();
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
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.BAD_REQUEST, request, errorMessages);
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public XAPIErrorInfo handleUnrecognizedPropertyException(final HttpServletRequest request, UnrecognizedPropertyException e) {
        final String errorMessage = String.format("Unrecognized property: [%s].", e.getPropertyName());
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.BAD_REQUEST, request, errorMessage);
        this.logException(e);
        this.logError(result);
        return result;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public XAPIErrorInfo handleHttpMessageNotReadableException(final HttpServletRequest request, HttpMessageNotReadableException e) {
        if (e.getCause() instanceof UnrecognizedPropertyException) {
            return this.handleUnrecognizedPropertyException(request, (UnrecognizedPropertyException)e.getCause());
        } else {
            XAPIErrorInfo result;
            if (e.getCause() instanceof JsonProcessingException) {
                final JsonProcessingException jpe = (JsonProcessingException)e.getCause();
                result = new XAPIErrorInfo(HttpStatus.BAD_REQUEST, request, jpe.getOriginalMessage());
            } else {
                result = new XAPIErrorInfo(HttpStatus.BAD_REQUEST, request, e);
            }
            this.logException(e);
            this.logError(result);
            return result;
        }
    }
    
    @ExceptionHandler(org.springframework.web.util.NestedServletException.class)
    public void test(NestedServletException e) {
    	Throwable t = e.getCause();
    	logger.debug("*************************** "+t.getClass().getName());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public XAPIErrorInfo exception(final HttpServletRequest request, Exception e) throws Exception {
        final String logMessageReferenceId = RandomStringUtils.randomAlphanumeric(8);
        final XAPIErrorInfo result = new XAPIErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR, request, "Unexpected error [reference ID: " + logMessageReferenceId + "].");
        logger.debug("Unexpected XAPI exception [refId: {}]: {}", logMessageReferenceId, e);
        this.logError(result);
        return result;
    }

    private void logException(final Exception e) {
        logger.debug("Exception message: {}", e.getMessage());
    }

    private void logError(final XAPIErrorInfo error) {
        logger.debug("Returning error: {}", error);
    }

}
