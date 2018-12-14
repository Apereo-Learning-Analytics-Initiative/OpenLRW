package unicon.matthews.oneroster.service.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public interface MongoClassRepository extends MongoRepository<MongoClass, String> {
  MongoClass findByTenantIdAndOrgIdAndClassSourcedId(final String tenantId, final String orgId, final String classSourcedId);
  Collection<MongoClass> findByTenantIdAndOrgId(final String tenantId, final String orgId);
  Collection<MongoClass> findByTenantIdAndOrgIdAndKlassCourseSourcedId(final String tenantId, final String orgId, final String courseSourcedId);
}
