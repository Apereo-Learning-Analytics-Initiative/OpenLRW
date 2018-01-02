package unicon.matthews.caliper.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import unicon.matthews.Vocabulary;
import unicon.matthews.caliper.Envelope;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.EventService;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.OrgService;

@RestController
@RequestMapping("/key/caliper")
public class ApiKeyOnlyCaliperController {
  private static Logger logger = LoggerFactory.getLogger(ApiKeyOnlyCaliperController.class);

  private EventService eventService;
  private OrgService orgService;
  
  @Autowired
  public ApiKeyOnlyCaliperController(EventService eventService, OrgService orgService) {
    this.eventService = eventService;
    this.orgService = orgService;
  }
  
  @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json;charset=utf-8")
  public ResponseEntity<?> post(@RequestHeader(value="Authorization") String authorization, @RequestBody Envelope envelope) throws OrgNotFoundException {
    Org org = orgService.findByApiKey(authorization);
    
    if (envelope != null) {
      List<Event> events = envelope.getData();
      List<String> ids = null;
      if (events != null && !events.isEmpty()) {
        ids = new ArrayList<>();
        for (Event event : events) {
          try {
            String savedId = this.eventService.save(org.getMetadata().get(Vocabulary.TENANT), org.getSourcedId(), event);
            ids.add(savedId);
          } catch (Exception e) {
            logger.error("Unable to save event {}",event);
            continue;
          }
        }
      }
      
      return new ResponseEntity<>(ids, null, HttpStatus.OK);
    }
    
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
  }

}
