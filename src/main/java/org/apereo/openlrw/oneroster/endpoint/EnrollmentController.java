package org.apereo.openlrw.oneroster.endpoint;

import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.Enrollment;
import org.apereo.openlrw.oneroster.exception.EnrollmentNotFoundException;
import org.apereo.openlrw.oneroster.service.EnrollmentService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
     * DELETE /api/enrollments
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


    /**
     * GET /api/enrollments
     *
     * @param token
     * @param page
     * @param limit
     * @param orderBy
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<Enrollment> getEvents(
            JwtAuthenticationToken token,
            @RequestParam(value = "page", required = false, defaultValue = "0") String page,
            @RequestParam(value = "limit", required = false, defaultValue = "1000") String limit,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy
    ) throws Exception {
        UserContext userContext = (UserContext) token.getPrincipal();
        return enrollmentService.findAll(userContext.getTenantId(), userContext.getOrgId(), page, limit, orderBy);
    }



}
