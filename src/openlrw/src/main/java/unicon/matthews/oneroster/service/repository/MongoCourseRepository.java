package unicon.matthews.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoCourseRepository extends MongoRepository<MongoCourse, String> {
  MongoCourse 
  findByTenantIdAndOrgIdAndCourseSourcedId(final String tenantId, final String orgId,
    final String courseSourcedId);
}
