/**
 * 
 */
package org.apereo.openlrw.caliper.service.repository;



import java.util.List;

import org.apereo.openlrw.admin.EventAlias;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * @author scody
 *
 */
public interface MongoEventAliasRepository extends MongoRepository<EventAlias, String> {
  List<EventAlias> findByTenantId(String tenantId);
}
