package org.apereo.openlrw.oneroster.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.apereo.openlrw.oneroster.exception.EnrollmentNotFoundException;
import org.apereo.openlrw.oneroster.service.EnrollmentService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private EnrollmentService enrollmentService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }


    /**
     * DELETE /api/enrollments/:id
     *
     * @param token
     * @param enrollmentId
     * @return HTTP Response 200
     * @throws EnrollmentNotFoundException 404 if not found
     */
    @RequestMapping(value = "/{enrollmentId:.+}", method = RequestMethod.DELETE)
    public ResponseEntity deleteEnrollment(JwtAuthenticationToken token, @PathVariable("enrollmentId") final String enrollmentId) throws EnrollmentNotFoundException {
        UserContext userContext = (UserContext) token.getPrincipal();
        if (enrollmentService.delete(userContext.getTenantId(), userContext.getOrgId(), enrollmentId))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
            throw new EnrollmentNotFoundException("Enrollment " + enrollmentId + " not found");
    }


    /**
     * DELETE /api/enrollments/
     *
     * @param token
     * @return HTTP Response 200
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteEnrollments(JwtAuthenticationToken token)  {
        UserContext userContext = (UserContext) token.getPrincipal();
        enrollmentService.deleteAll(userContext.getTenantId(), userContext.getOrgId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
