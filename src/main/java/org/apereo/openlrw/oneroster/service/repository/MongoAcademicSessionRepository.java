package org.apereo.openlrw.oneroster.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author stalele
 *
 */
public interface MongoAcademicSessionRepository extends MongoRepository<MongoAcademicSession, String> {
  MongoAcademicSession 
  findByTenantIdAndOrgIdAndAcademicSessionSourcedId(final String tenantId, final String orgId,
    final String academicSourcedId);
}
