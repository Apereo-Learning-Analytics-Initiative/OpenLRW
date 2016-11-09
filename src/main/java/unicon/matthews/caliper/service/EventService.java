/**
 * 
 */
package unicon.matthews.caliper.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.Group;
import unicon.matthews.caliper.service.repository.MongoEventRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class EventService {
  private final MongoEventRepository mongoEventRepository;
  
  @Autowired
  public EventService(MongoEventRepository mongoEventRepository) {
    this.mongoEventRepository = mongoEventRepository;
  }
  
  public Event save(String tenantId, String orgId, Event toBeSaved) {
    
//    MongoEvent mongoEvent
//      = new MongoEvent.Builder()
//        .withOrganizationId(orgId)
//        .withTenantId(tenantId)
    return null;
    
  }
  
  private String getUserId(Event event) {
    if (event.getAgent() == null || StringUtils.isBlank(event.getAgent().getId())) {
      throw new IllegalStateException(event.toString());
    }
    
    return event.getAgent().getId();
  }
  
  private String getClassId(Event event) {
    Group group = event.getGroup();
    
    if (StringUtils.isNotBlank(group.getType())) {
      
    }
    
    return null;
  }
}
