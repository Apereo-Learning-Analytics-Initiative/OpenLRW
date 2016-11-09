/**
 * 
 */
package unicon.matthews.caliper.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

/**
 * @author ggilbert
 *
 */
public interface MongoEventRepository extends MongoRepository<MongoEvent, String> {

}
