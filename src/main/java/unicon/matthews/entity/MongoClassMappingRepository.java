/**
 * 
 */
package unicon.matthews.entity;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 *
 */
public interface MongoClassMappingRepository extends MongoRepository<ClassMapping, String> {
  ClassMapping findByTenantIdAndOrganizationIdAndClassExternalId(String tenantId, String organizationId, String classExternalId);
}
