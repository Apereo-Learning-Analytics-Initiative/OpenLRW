package org.apereo.openlrw.common.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public class MessageResponse {

    private HttpStatus status;
    private List<String> messages;
    private String path, timestamp;

    public MessageResponse(HttpStatus status, String timestamp, HttpServletRequest request, String message) {
        this.messages = new ArrayList<>();
        this.status = status;
        this.messages.add(message);
        this.timestamp = timestamp;
        this.path = request.getQueryString() == null ? request.getRequestURI(): request.getRequestURI() + "?" + request.getQueryString();
    }

    public MessageResponse(HttpStatus status, String timestamp, HttpServletRequest request, List<String> messages) {
        this.messages = new ArrayList<>();
        this.status = status;
        this.messages = messages;
        this.timestamp = timestamp;
        this.path = request.getQueryString() == null ? request.getRequestURI(): request.getRequestURI() + "?" + request.getQueryString();
    }

    public MessageResponse(HttpStatus status, String timestamp, String message) {
        this.messages = new ArrayList<>();
        this.status = status;
        this.messages.add(message);
        this.timestamp = timestamp;
        this.path = "Path was disabled for this exception.";
    }

    public MessageResponse(HttpStatus status, String timestamp, List<String> messages) {
        this.messages = new ArrayList<>();
        this.status = status;
        this.messages = messages;
        this.timestamp = timestamp;
        this.path = "Path was disabled for this exception.";
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "status=" + status +
                ", path=" + path +
                ", timestamp=" + timestamp +
                "message=" + messages.toString() +
                '}';
    }
}