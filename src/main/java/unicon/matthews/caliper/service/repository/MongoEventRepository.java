/**
 * 
 */
package unicon.matthews.caliper.service.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoEventRepository extends MongoRepository<MongoEvent, String> {
  
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassId(final String tenantId, final String orgId, final String classId);
  Collection<MongoEvent> findByTenantIdAndOrganizationIdAndClassIdAndUserId(final String tenantId, final String orgId, final String classId, final String userId);

}
