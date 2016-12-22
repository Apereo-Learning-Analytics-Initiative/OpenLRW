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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class used to hold info for a XAPI-call-related error.  This object will be JSONified and returned as the XAPI call response.
 * @author Gary Roybal, groybal@unicon.net
 */
public class XAPIErrorInfo {

    @JsonProperty private int status;
    @JsonProperty private String reason;
    @JsonProperty private String method;
    @JsonProperty private String path;
    @JsonProperty private Map<String, String[]> parameters;
    @JsonProperty private List<String> messages = new ArrayList<String>();

    public XAPIErrorInfo(final HttpStatus status, final HttpServletRequest request) {
        this.getDataFromHttpStatus(status);
        this.getDataFromRequest(request);
    }

    public XAPIErrorInfo(final HttpStatus status, final HttpServletRequest request, final Exception resultingException) {
        this(status, request);
        this.messages.add(ExceptionUtils.getRootCauseMessage(resultingException));
    }

    public XAPIErrorInfo(final HttpStatus status, final HttpServletRequest request, final String errorMessage) {
        this(status, request);
        if (errorMessage != null) {
            this.messages.add(errorMessage);
        }
    }

    public XAPIErrorInfo(final HttpStatus status, final HttpServletRequest request, final List<String> errorMessages) {
        this(status, request);
        if (errorMessages != null) {
            this.messages.addAll(errorMessages);
        }
    }

    public void overrideReason(final String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    private void getDataFromHttpStatus(final HttpStatus status) {
        this.status = status.value();
        this.reason = status.getReasonPhrase();
    }

    private void getDataFromRequest(final HttpServletRequest request) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.parameters = request.getParameterMap();
    }

}
