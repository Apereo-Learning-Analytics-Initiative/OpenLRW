package org.apereo.openlrw.admin.service.repository;

import org.apereo.openlrw.admin.AdminUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminUserRepository extends MongoRepository<AdminUser, String> {

    Optional<AdminUser> findByUsername(final String userName);

}
