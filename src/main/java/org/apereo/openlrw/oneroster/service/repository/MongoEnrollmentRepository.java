package org.apereo.openlrw.oneroster.service.repository;

import org.apereo.model.oneroster.Role;
import org.apereo.model.oneroster.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
public interface MongoEnrollmentRepository extends MongoRepository<MongoEnrollment, String> {
  MongoEnrollment findByTenantIdAndOrgIdAndClassSourcedIdAndUserSourcedIdIgnoreCase(final String tenantId, final String orgId, final String classSourcedId, final String userSourcedId);
  Integer countByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatusAndEnrollmentRole(final String tenantId, final String orgId, final String classSourcedId, final Status status, final Role role);
  Collection<MongoEnrollment> findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCaseAndEnrollmentStatus(final String tenantId, final String orgId, final String userSourcedId, final Status status);
  Collection<MongoEnrollment> findByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatus(final String tenantId, final String orgId, final String classSourcedId, final Status status);
  Collection<MongoEnrollment> findByTenantIdAndOrgIdAndEnrollmentRole(final String tenantId, final String orgId, final String Role);
  List<MongoEnrollment> findTopByTenantIdAndOrgIdOrderByEnrollmentBeginDateDesc(final String tenantId, final String orgId, Pageable pageRequest);
  List<MongoEnrollment> findTopByTenantIdAndOrgIdOrderByEnrollmentEndDateDesc(final String tenantId, final String orgId, Pageable pageRequest);
  List<MongoEnrollment> findTopByTenantIdAndOrgId(final String tenantId, final String orgId, Pageable pageRequest);
  Long deleteByTenantIdAndOrgIdAndEnrollmentSourcedId(String tenantId, String orgId, String sourcedId);
  Long deleteAllByTenantIdAndOrgId(String tenantId, String orgId);
}
