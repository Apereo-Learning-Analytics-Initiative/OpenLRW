/**
 * 
 */
package unicon.matthews.caliper.service.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoEventRepository extends MongoRepository<MongoEvent, String> {
  
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassId(final String tenantId, final String orgId, final String classId);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassIdAndEventMembershipRoles(final String tenantId, final String orgId, final String classId, final List<String> roles);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassIdAndUserId(final String tenantId, final String orgId, final String classId, final String userId);
  MongoEvent findByTenantIdAndOrganizationIdAndEventId(final String tenantId, final String orgId, final String eventId);
  Collection<MongoEvent> findByTenantIdAndOrganizationId(final String tenantId, final String orgId);
}
