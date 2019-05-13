package org.apereo.openlrw.security.indicator.endpoint;

import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.indicator.service.CustomIndicatorService;
import org.apereo.openlrw.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
@RequestMapping("/api/indicator/custom")
public class CustomIndicatorController {

    private CustomIndicatorService customIndicatorService;


    @Autowired
    public CustomIndicatorController(CustomIndicatorService customIndicatorService) {
        this.customIndicatorService = customIndicatorService;
    }

    /**
     * Set the status indicator
     *
     * @param token  JWT
     * @return       HTTP Response
     */
    @RequestMapping(method = RequestMethod.POST)

    public ResponseEntity<?> post(JwtAuthenticationToken token, @RequestBody String content) throws JSONException {
        UserContext userContext = (UserContext) token.getPrincipal(); // Check if user is logged
        JSONObject jsonObject = new JSONObject(content);
        String value = jsonObject.get("status").toString();
        boolean result = this.customIndicatorService.update(value);

        if (result) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("This status does not exist.", HttpStatus.BAD_REQUEST);
    }


}