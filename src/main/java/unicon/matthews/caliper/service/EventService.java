package unicon.matthews.caliper.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import unicon.matthews.caliper.ClassEventStatistics;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.exception.EventNotFoundException;
import unicon.matthews.caliper.service.repository.MongoEvent;
import unicon.matthews.caliper.service.repository.MongoEventRepository;
import unicon.matthews.common.exception.BadRequestException;
import unicon.matthews.tenant.Tenant;
import unicon.matthews.tenant.service.repository.TenantRepository;

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

  @Autowired private MongoOperations mongoOps;

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
              .withEventTime(toBeSaved.getEventTime() != null ? toBeSaved.getEventTime() : LocalDateTime.now(ZoneId.of("UTC")))
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
    Collection<MongoEvent> mongoEvents = mongoEventRepository.findByTenantIdAndOrganizationIdAndClassIdAndUserIdIgnoreCase(tenantId, orgId, classId, userId);
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
    query.addCriteria(Criteria.where("userId").is(userId));
    query.addCriteria(Criteria.where("organizationId").is(orgId));
    query.addCriteria(Criteria.where("tenantId").is(tenantId));

    if (from.isEmpty() && !to.isEmpty()) {
      try {
        query.addCriteria(Criteria.where("event.eventTime").lt(dateFormat.parse(to)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    } else if (!from.isEmpty() && to.isEmpty()) {
      try {
        query.addCriteria(Criteria.where("event.eventTime").gt(dateFormat.parse(from)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    } else if (!from.isEmpty() && !to.isEmpty()) {
      try {
        query.addCriteria(Criteria.where("event.eventTime").lt(dateFormat.parse(to)));
        query.addCriteria(Criteria.where("event.eventTime").gt(dateFormat.parse(from)));
      } catch (Exception e) {
        throw new BadRequestException("Not able to parse the date, it has to be in the following format: `yyyy-MM-dd hh:mm` ");
      }
    }

    mongoEvents = mongoOps.find(query, MongoEvent.class);

    if (mongoEvents != null && !mongoEvents.isEmpty())
      return mongoEvents.stream().map(MongoEvent::getEvent).collect(Collectors.toList());

    throw new EventNotFoundException("Events not found.");
  }

}
