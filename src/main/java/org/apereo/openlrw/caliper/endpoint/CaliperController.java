package org.apereo.openlrw.caliper.endpoint;

import org.apereo.openlrw.caliper.service.EventService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.apereo.openlrw.caliper.Envelope;
import org.apereo.openlrw.caliper.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ggilbert
 *
 */
@RestController
@RequestMapping("/api/caliper")
public class CaliperController {
  private static Logger logger = LoggerFactory.getLogger(CaliperController.class);

  private EventService eventService;
  
  @Autowired
  public CaliperController(EventService eventService) {
    this.eventService = eventService;
  }
  
  @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
  public ResponseEntity<?> post(JwtAuthenticationToken token, @RequestBody Envelope envelope) {
    UserContext userContext = (UserContext) token.getPrincipal();
    
    if (envelope != null) {
      List<Event> events = envelope.getData();
      List<String> ids = null;
      if (events != null && !events.isEmpty()) {
        ids = new ArrayList<>();
        for (Event event : events) {
          try {
            String savedId = this.eventService.save(userContext.getTenantId(), userContext.getOrgId(), event);
            ids.add(savedId);
          } catch (Exception e) {
            logger.error("Unable to save event {}",event);
          }
        }
      }
      
      return new ResponseEntity<>(ids, null, HttpStatus.OK);
    }
    
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
