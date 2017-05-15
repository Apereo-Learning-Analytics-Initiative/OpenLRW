package unicon.matthews.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdminUserRepository extends MongoRepository<AdminUser, String> {

}
