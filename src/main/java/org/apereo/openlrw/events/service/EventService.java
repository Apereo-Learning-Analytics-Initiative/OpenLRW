package org.apereo.openlrw.events.service;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apereo.openlrw.caliper.Agent;
import org.apereo.openlrw.events.caliper.exception.EventNotFoundException;
import org.apereo.openlrw.events.caliper.service.ClassIdConverter;
import org.apereo.openlrw.events.caliper.service.UserIdConverter;
import org.apereo.openlrw.events.service.repository.MongoEvent;
import org.apereo.openlrw.events.service.repository.MongoEventRepository;
import org.apereo.openlrw.common.exception.BadRequestException;
import org.apereo.openlrw.oneroster.service.repository.MongoEnrollmentRepository;
import org.apereo.openlrw.tenant.Tenant;
import org.apereo.openlrw.tenant.service.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.apereo.model.oneroster.Role;
import org.apereo.model.oneroster.Status;
import org.apereo.openlrw.caliper.ClassEventStatistics;
import org.apereo.openlrw.caliper.Event;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Service
public class EventService {
  private final TenantRepository tenantRepository;
  private final MongoEventRepository mongoEventRepository;
  private final UserIdConverter userIdConverter;
  private final ClassIdConverter classIdConverter;
  private final MongoOperations mongoOps;
  private final MongoEnrollmentRepository mongoEnrollmentRepository;

  @Autowired
  public EventService(
          TenantRepository tenantRepository,
          MongoEventRepository mongoEventRepository,
          UserIdConverter userIdConverter,
          ClassIdConverter classIdConverter,
          MongoOperations mongoOperations,
          MongoEnrollmentRepository mongoEnrollmentRepository) {
    this.tenantRepository = tenantRepository;
    this.mongoEventRepository = mongoEventRepository;
    this.userIdConverter = userIdConverter;
    this.classIdConverter = classIdConverter;
    this.mongoOps = mongoOperations;
    this.mongoEnrollmentRepository = mongoEnrollmentRepository;
  }
  
  public static final ImmutableList<String> STUDENT_ROLES_LIST =
		  ImmutableList.of("http://purl.imsglobal.org/vocab/lis/v2/membership#Learner", "student", "Student");


  /**
   * Save an event into the database
   *
   * @param tenantId
   * @param orgId
   * @param toBeSaved
   * @return
   */
  public String save(String tenantId, String orgId, Event toBeSaved) {

    if (StringUtils.isBlank(toBeSaved.getId())) {
      Long offset = TimeUnit.MILLISECONDS.toSeconds(TimeZone.getDefault().getRawOffset());

      toBeSaved = new Event.Builder()
              .withAction(toBeSaved.getAction())
              .withAgent(toBeSaved.getAgent())
              .withContext(toBeSaved.getContext())
              .withEdApp(toBeSaved.getEdApp())
              .withEventTime(toBeSaved.getEventTime() != null ? toBeSaved.getEventTime() : Instant.now())
              .withFederatedSession(toBeSaved.getFederatedSession())
              .withGenerated(toBeSaved.getGenerated())
              .withGroup(toBeSaved.getGroup())
              .withId(UUID.randomUUID().toString().replace("-", ""))
              .withMembership(toBeSaved.getMembership())
              .withObject(toBeSaved.getObject())
              .withTarget(toBeSaved.getTarget())
              .withType(toBeSaved.getType())
              .withTimeZoneOffset(offset)
              .build();
    }

    Tenant tenant = tenantRepository.findById(tenantId).orElse(null);

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

  /**
   * Get all the events
   *
   * @param tenantId
   * @param orgId
   * @return Collection<Event>
   */
  public Collection<Event> findAll(final String tenantId, final String orgId, String page, String limit) throws Exception {
    Pageable pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by("event.eventTime").descending());

    try {
        Collection<MongoEvent> mongoEvents = mongoEventRepository.findTopByTenantIdAndOrganizationIdOrderByEventEventTimeDesc(tenantId, orgId, pageRequest);

        if (mongoEvents != null && !mongoEvents.isEmpty()) {
            return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
        }

        return null;
    } catch (Exception e) {
        throw new Exception(e.getMessage());
    }

  }


  /**
   * Get Events by EdApp (source)
   *
   * @param tenantId
   * @param orgId
   * @param edAppId
   * @param page
   * @param limit
   * @return Collection<Event>
   *
   * @throws EventNotFoundException
   */
  public Collection<Event> findByEdApp(final String tenantId, final String orgId, final String page, final String limit,  final String edAppId) throws EventNotFoundException {
    Pageable pageRequest = PageRequest.of(Integer.parseInt(page), Integer.parseInt(limit), Sort.by("event.eventTime").descending());

    Collection<MongoEvent> mongoEvents = mongoEventRepository.findTopByTenantIdAndOrganizationIdAndEventEdAppIdOrderByEventEventTimeDesc(tenantId, orgId, edAppId, pageRequest);
      if (mongoEvents != null && !mongoEvents.isEmpty()) {
        return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
      }

      throw new EventNotFoundException("Events not found.");
  }




  /**
   * Get all the events
   * @deprecated use findAll() method instead
   *
   * @param tenantId
   * @param orgId
   * @return
   */
  public Collection<Event> getEvents(final String tenantId, final String orgId) {
    Collection<MongoEvent> mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationId(tenantId, orgId);
    if (mongoEvents != null && !mongoEvents.isEmpty()) {
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
    }
    return null;
  }

  public Collection<Event> getEventsForClassAndUser(final String tenantId, final String orgId, final String classId, final String userId) {
    Collection<MongoEvent> mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassIdAndUserIdIgnoreCase(tenantId, orgId, classId, userId);
    if (mongoEvents != null && !mongoEvents.isEmpty()) {
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());
    }
    return null;
  }
  
  public ClassEventStatistics getEventStatisticsForClass(final String tenantId, final String orgId, final String classId, boolean studentsOnly) {	  
    Collection<MongoEvent> mongoEvents;

    if (studentsOnly)
      mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassIdAndEventMembershipRolesIn(tenantId, orgId, classId, STUDENT_ROLES_LIST);
    else
      mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassId(tenantId, orgId, classId);


    if (mongoEvents == null || mongoEvents.isEmpty()) {
      throw new RuntimeException();
    }

    Map<String, Long> studentsWithEventsCounted = mongoEvents.stream()
            .collect(Collectors.groupingBy(MongoEvent::getUserId, Collectors.counting()));

    Map<String, List<MongoEvent>> eventsByStudent = mongoEvents.stream()
            .collect(Collectors.groupingBy(MongoEvent::getUserId));

    Map<String,Map<String, Long>> eventCountGroupedByDateAndStudent = null;

    if (eventsByStudent != null) {
      eventCountGroupedByDateAndStudent = new HashMap<>();
      for (String key : eventsByStudent.keySet()) {
        Map<String, Long> eventCountByDate = eventsByStudent.get(key).stream().collect(Collectors.groupingBy(event -> StringUtils.substringBefore(event.getEvent().getEventTime().toString(), "T"), Collectors.counting()));
        eventCountGroupedByDateAndStudent.put(key, eventCountByDate);
      }
    }

    Map<String, Long> eventCountByDate = mongoEvents.stream().collect(Collectors.groupingBy(event -> StringUtils.substringBefore(event.getEvent().getEventTime().toString(), "T"), Collectors.counting()));        

    List<Long> eventCountPerStudent = new ArrayList<>();
    if (eventsByStudent != null) {        
	    for (String key : eventsByStudent.keySet()) {
	    	eventCountPerStudent.add(eventsByStudent.get(key).stream().count());
	    }
    }
    
    Collections.sort(eventCountPerStudent);    
    Integer studentEnrollmentCount = mongoEnrollmentRepository.countByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatusAndEnrollmentRole(tenantId, orgId, classId, Status.active, Role.student);
    Map<String, Long> eventTypeTotals = calculateEventTypeTotals(mongoEvents);
    Map<String,Double> eventTypeAverages = calculateEventTypeAverages(eventTypeTotals,studentEnrollmentCount);
    return new ClassEventStatistics.Builder()
      .withClassSourcedId(classId)
      .withTotalEvents(mongoEvents.size())
      .withStudentsWithEvents(studentsWithEventsCounted.keySet().size())
      .withTotalStudentEnrollments(studentEnrollmentCount)
      .withEventTypeTotals(eventTypeTotals)
      .withEventTypeAverages(eventTypeAverages)
      .withEventCountGroupedByDate(eventCountByDate)
      .withEventCountGroupedByDateAndStudent(eventCountGroupedByDateAndStudent)    
      .build();

  }

  /**
   * Calculates the total number of events by action type
   * Also cleans the URL off the verb completely
   */
  private Map<String, Long> calculateEventTypeTotals(Collection<MongoEvent> mongoEvents) {
    
    List<Event> events = mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toCollection(ArrayList::new));;
    
    Map<String, Long> eventTypeTotals = events.stream()
    .collect(Collectors.groupingBy(Event::getAction, Collectors.counting()));

    eventTypeTotals = cleanVerbs(eventTypeTotals);    
    return eventTypeTotals;
  }
  
  /**
   * Cleans the URL off the verbs
   */
  private Map<String, Long> cleanVerbs(Map<String, Long> events) {
    
    Set<String> keys = events.keySet();
    String[] array = keys.toArray(new String[keys.size()]);
    for (String origkey : array) {
      String newKey = origkey.substring(origkey.lastIndexOf("#") + 1);
      events.put(newKey, events.remove(origkey));
    }    
    return events;
  }
  
  /**
   * Calculates teh EventTypeAverages
   */
  private Map<String,Double> calculateEventTypeAverages(Map<String,Long> eventTypeTotals, Integer totalNumberStudents) {
    HashMap<String, Double> eventTypeAverages = new HashMap<>(); 
    for (Map.Entry<String, Long> eventTypeTotal: eventTypeTotals.entrySet()) {
      eventTypeAverages.put(eventTypeTotal.getKey(), eventTypeTotal.getValue()/totalNumberStudents.doubleValue());
    }
    return eventTypeAverages;
  }

  /**
   * Gets Events for a user given
   *
   * @param tenantId an id of a tenant
   * @param orgId an id of an organization
   * @param userId its id
   * @param from (optional) date (yyyy-MM-dd hh:mm) greater
   * @param to (optional) date (yyyy-MM-dd hh:mm) less
   * @return Events or null
   */
  public Collection<Event> getEventsForUser(final String tenantId, final String orgId, final String userId, final String from, final String to) throws EventNotFoundException, IllegalArgumentException, BadRequestException {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(userId))
      throw new IllegalArgumentException();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    Collection<MongoEvent> mongoEvents;
    Query query = new Query();
    query.addCriteria(where("userId").is(userId).and("organizationId").is(orgId).and("tenantId").is(tenantId));

    if (from.isEmpty() && !to.isEmpty()) {
      try {
        query.addCriteria(where("event.eventTime").lt(dateFormat.parse(to)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    } else if (!from.isEmpty() && to.isEmpty()) {
      try {
        query.addCriteria(where("event.eventTime").gt(dateFormat.parse(from)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    } else if (!from.isEmpty() && !to.isEmpty()) {
      try {
        query.addCriteria(where("event.eventTime").lt(dateFormat.parse(to)).gt(dateFormat.parse(from)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    }

    mongoEvents = mongoOps.find(query, MongoEvent.class);

    if (!mongoEvents.isEmpty())
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());

    throw new EventNotFoundException("Events not found.");
  }

}
