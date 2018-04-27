package unicon.matthews.caliper.service.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public interface MongoEventRepository extends MongoRepository<MongoEvent, String> {
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassId(final String tenantId, final String orgId, final String classId);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassIdAndEventMembershipRoles(final String tenantId, final String orgId, final String classId, final List<String> roles);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassIdAndUserIdIgnoreCase(final String tenantId, final String orgId, final String classId, final String userId);
  MongoEvent findByTenantIdAndOrganizationIdAndEventId(final String tenantId, final String orgId, final String eventId);
  Collection<MongoEvent> findByTenantIdAndOrganizationId(final String tenantId, final String orgId);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndUserIdIgnoreCase(final String tenantId, final String orgId, final String userId);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndUserIdIgnoreCaseAndEventEventTimeBetween(final String tenantId, final String orgId, final String userId, Date from, Date to);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndUserIdIgnoreCaseAndEventEventTimeAfter(final String tenantId, final String orgId, final String userId, Date from);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndUserIdIgnoreCaseAndEventEventTimeBefore(final String tenantId, final String orgId, final String userId, Date to);
}
