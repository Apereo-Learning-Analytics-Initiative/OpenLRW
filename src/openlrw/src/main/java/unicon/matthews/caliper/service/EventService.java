/**
 * 
 */
package unicon.matthews.caliper.service;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.caliper.ClassEventStatistics;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.repository.MongoEvent;
import unicon.matthews.caliper.service.repository.MongoEventRepository;
import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.repository.TenantRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class EventService {
  private final TenantRepository tenantRepository;
  private final MongoEventRepository mongoEventRepository;
  private final UserIdConverter userIdConverter;
  private final ClassIdConverter classIdConverter;
  
  @Autowired
  public EventService(
      TenantRepository tenantRepository,
      MongoEventRepository mongoEventRepository, 
      UserIdConverter userIdConverter,
      ClassIdConverter classIdConverter) {
    this.tenantRepository = tenantRepository;
    this.mongoEventRepository = mongoEventRepository;
    this.userIdConverter = userIdConverter;
    this.classIdConverter = classIdConverter;
  }
  
  public String save(String tenantId, String orgId, Event toBeSaved) {
    
    if (StringUtils.isBlank(toBeSaved.getId())) {
      toBeSaved
        = new Event.Builder()
            .withAction(toBeSaved.getAction())
            .withAgent(toBeSaved.getAgent())
            .withContext(toBeSaved.getContext())
            .withEdApp(toBeSaved.getEdApp())
            .withEventTime(toBeSaved.getEventTime())
            .withFederatedSession(toBeSaved.getFederatedSession())
            .withGenerated(toBeSaved.getGenerated())
            .withGroup(toBeSaved.getGroup())
            .withId(UUID.randomUUID().toString())
            .withMembership(toBeSaved.getMembership())
            .withObject(toBeSaved.getObject())
            .withTarget(toBeSaved.getTarget())
            .withType(toBeSaved.getType())
            .build();
    }
    
    Tenant tenant = tenantRepository.findOne(tenantId);
    
    MongoEvent mongoEvent
      = new MongoEvent.Builder()
        .withClassId(classIdConverter.convert(tenant, toBeSaved))
        .withEvent(toBeSaved)
        .withOrganizationId(orgId)
        .withTenantId(tenantId)
        .withUserId(userIdConverter.convert(tenant, toBeSaved))
        .build();
    
    MongoEvent saved = mongoEventRepository.save(mongoEvent);
    return saved.getEvent().getId();
  }
  
  public Event getEventForId(final String tenantId, final String orgId, final String eventId) {
    MongoEvent mongoEvent = mongoEventRepository.findByTenantIdAndOrganizationIdAndEventId(tenantId, orgId, eventId);
    
    if (mongoEvent != null) {
      return mongoEvent.getEvent();
    }
    
    return null;
  }
  
  public Collection<Event> getEvents(final String tenantId, final String orgId) {
    Collection<MongoEvent> mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationId(tenantId, orgId);
    if (mongoEvents != null && !mongoEvents.isEmpty()) {
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
    }
    return null;
  }
  
  public Collection<Event> getEventsForClassAndUser(final String tenantId, final String orgId, final String classId, final String userId) {
    Collection<MongoEvent> mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassIdAndUserId(tenantId, orgId, classId, userId);
    if (mongoEvents != null && !mongoEvents.isEmpty()) {
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
    }
    return null;
  }
  
  public ClassEventStatistics getEventStatisticsForClass(final String tenantId, final String orgId, final String classId, boolean studentsOnly) {
    
    Collection<MongoEvent> mongoEvents = null;
    
    if (studentsOnly) {
      mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassIdAndEventMembershipRoles(tenantId, orgId, classId, Collections.singletonList("student"));
    }
    else {
      mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassId(tenantId, orgId, classId);
    }
    
    if (mongoEvents == null || mongoEvents.isEmpty()) {
      // TODO
      throw new RuntimeException();
    }
    
    Map<String, Long> studentsCounted = mongoEvents.stream()
        .collect(Collectors.groupingBy(event -> event.getUserId(), Collectors.counting()));
    
    Map<String, List<MongoEvent>> eventsByStudent = mongoEvents.stream()
        .collect(Collectors.groupingBy(event -> event.getUserId()));
    
    Map<String,Map<String, Long>> eventCountGroupedByDateAndStudent = null;
    
    if (eventsByStudent != null) {
      eventCountGroupedByDateAndStudent = new HashMap<>();
      for (String key : eventsByStudent.keySet()) {
        Map<String, Long> eventCountByDate = eventsByStudent.get(key).stream()
            .collect(Collectors.groupingBy(event -> StringUtils.substringBefore(event.getEvent().getEventTime().toString(), "T"), Collectors.counting()));
        eventCountGroupedByDateAndStudent.put(key, eventCountByDate);
      }
    }

    Map<String, Long> eventCountByDate = mongoEvents.stream()
        .collect(Collectors.groupingBy(event -> StringUtils.substringBefore(event.getEvent().getEventTime().toString(), "T"), Collectors.counting()));

    ClassEventStatistics classEventsStats
     = new ClassEventStatistics.Builder()
    .withClassSourcedId(classId)
    .withTotalEvents(mongoEvents.size())
    .withTotalStudentEnrollments(studentsCounted.keySet().size())
    .withEventCountGroupedByDate(eventCountByDate)
    .withEventCountGroupedByDateAndStudent(eventCountGroupedByDateAndStudent)
    .build();

    
    return classEventsStats;

  }
}
