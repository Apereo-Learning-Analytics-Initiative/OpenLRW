/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import java.util.Collection;

import org.springframework.data.mongodb.repository.MongoRepository;

import unicon.matthews.oneroster.Status;

/**
 * @author ggilbert
 *
 */
public interface MongoEnrollmentRepository extends MongoRepository<MongoEnrollment, String> {
  Collection<MongoEnrollment> 
    findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCaseAndEnrollmentStatus(final String tenantId, final String orgId,
        final String userSourcedId, final Status status);
  
  Collection<MongoEnrollment>
    findByTenantIdAndOrgIdAndClassSourcedIdAndEnrollmentStatus(final String tenantId, final String orgId,
        final String classSourcedId, final Status status);
  
  MongoEnrollment 
    findByTenantIdAndOrgIdAndClassSourcedIdAndUserSourcedIdIgnoreCase(final String tenantId, final String orgId,
        final String classSourcedId, final String userSourcedId);
}
