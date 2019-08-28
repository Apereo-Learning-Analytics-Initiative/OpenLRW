package org.apereo.openlrw.events.endpoint;

import org.apereo.openlrw.caliper.Event;
import org.apereo.openlrw.events.service.EventService;
import org.apereo.openlrw.oneroster.service.OrgService;
import org.apereo.openlrw.security.auth.JwtAuthenticationToken;
import org.apereo.openlrw.security.model.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RestController
@RequestMapping("/api/events")
public class EventController {
  private static Logger logger = LoggerFactory.getLogger(EventController.class);

  private EventService eventService;

  @Autowired
  public EventController(EventService eventService, OrgService orgService) {
    this.eventService = eventService;
  }

  /**
   * Get all the events
   *
   * @param token
   * @param page
   * @param limit
   * @return
   */
   @RequestMapping(method = RequestMethod.GET)
   public Collection<Event> getEvents(
           JwtAuthenticationToken token,
           @RequestParam(value = "page", required = false, defaultValue = "0") String page,
           @RequestParam(value = "limit", required = false, defaultValue = "1000") String limit
   ) throws Exception {
     UserContext userContext = (UserContext) token.getPrincipal();
     return eventService.findAll(userContext.getTenantId(), userContext.getOrgId(), page, limit);
   }

}
