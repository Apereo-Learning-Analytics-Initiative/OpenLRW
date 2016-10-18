package unicon.matthews.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import unicon.matthews.entity.User;

/**
 * UserRepository
 * 
 * @author vladimir.stankovic
 *
 * Aug 16, 2016
 */
public interface UserRepository extends MongoRepository<User, Long> {
    public Optional<User> findByUsername(@Param("username") String username);
}
