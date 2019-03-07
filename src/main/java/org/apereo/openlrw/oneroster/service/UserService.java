package org.apereo.openlrw.oneroster.service;

import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.apereo.model.oneroster.User;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.oneroster.exception.UserNotFoundException;
import org.apereo.openlrw.oneroster.service.repository.MongoUser;
import org.apereo.openlrw.oneroster.service.repository.MongoUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Service
public class UserService {
  private static Logger logger = LoggerFactory.getLogger(UserService.class);

  private MongoUserRepository mongoUserRepository;
  private final MongoOperations mongoOps;

  @Autowired
  public UserService(MongoUserRepository mongoUserRepository, MongoOperations mongoOperations) {
    this.mongoUserRepository = mongoUserRepository;
    this.mongoOps = mongoOperations;
  }

  public User findBySourcedId(final String tenantId, final String orgId, final String userSourcedId) throws UserNotFoundException {

    MongoUser mongoUser = mongoUserRepository.findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCase(tenantId, orgId, userSourcedId);
    if (mongoUser == null) {
      throw new UserNotFoundException("User not found");
    }

    return mongoUser.getUser();
  }

  /**
   * Finds and returns all the users that belong to a tenant and an organization given
   *
   * @param tenantId  an id of a tenant
   * @param orgId     an id of an organization
   * @return          the users
   */
  public Collection<MongoUser> findAll(final String tenantId, final String orgId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId))
      throw new IllegalArgumentException();

    return mongoUserRepository.findByTenantIdAndOrgId(tenantId, orgId);
  }

  /**
   * Deletes a user for its id given (combined with tenant and organization)
   * @param tenantId tenant id
   * @param orgId    organization id
   * @param userId   its Id 
   * @return         boolean (if user has been deleted)
   */
  public boolean delete(final String tenantId, final String orgId, final String userId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(userId))
      throw new IllegalArgumentException();

    return mongoUserRepository.deleteByTenantIdAndOrgIdAndUserSourcedIdIgnoreCase(tenantId, orgId, userId) > 0;
  }

  /**
   * Update a user for its id given
   * @param tenantId tenant id
   * @param orgId    organization id
   * @param userId   its Id
   * @param object   stringified JSON that has the fields and values to edit/add to the user
   * @return boolean
   */
  public boolean update(final String tenantId, final String orgId, final String userId, final String object) throws JSONException {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(userId) || StringUtils.isBlank(object))
      throw new IllegalArgumentException();

    final JSONObject obj = new JSONObject(object);

    if (obj.has("sourcedId"))
      throw new IllegalArgumentException("sourcedId attribute cannot be edited.");

    Iterator<String> keys = obj.keys();
    Query query = new Query();
    query.addCriteria(where("user.sourcedId").is(userId).and("orgId").is(orgId).and("tenantId").is(tenantId));

    while( keys.hasNext() ) {
      String key = keys.next();
      Object value = obj.get(key);
      Update update = null;

      if (value instanceof JSONObject && !key.equals("metadata"))
        continue;

      if (!(value instanceof JSONObject) && key.equals("metadata"))
        throw new IllegalArgumentException("metadata attribute has to be an object <String : String>");

      if (key.equals("metadata")) {
        JSONObject jsonMetadata = (JSONObject)obj.get(key);
        Map<String, Object> metadata = new HashMap<>();
        Iterator<?> iterator = jsonMetadata.keys();
        while (iterator.hasNext()) {
          String subKey = (String)iterator.next();
          Object subValue = jsonMetadata.get(subKey);
          metadata.put(subKey, subValue);
        }
        update = Update.update("user.metadata", metadata);
      }else{
        update = Update.update("user." + key, obj.get(key));
      }

      UpdateResult result = mongoOps.updateFirst(query, update, MongoUser.class);
      if (!result.isModifiedCountAvailable())
        return false;
    }

    return true;
  }

  public User save(final String tenantId, final String orgId, User user, boolean check) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || user == null)
      throw new IllegalArgumentException();

    MongoUser mongoUserToSave, existingMongoUser = null;

    if (check)
      existingMongoUser = mongoUserRepository.findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCase(tenantId, orgId, user.getSourcedId());

    if (existingMongoUser == null) {
      mongoUserToSave = new MongoUser.Builder()
              .withTenantId(tenantId)
              .withOrgId(orgId)
              .withUser(fromUser(user, tenantId))
              .build();
    } else {
      mongoUserToSave = new MongoUser.Builder()
              .withId(existingMongoUser.getId())
              .withTenantId(tenantId)
              .withOrgId(orgId)
              .withUser(fromUser(user, tenantId))
              .build();
    }

    MongoUser saved = mongoUserRepository.save(mongoUserToSave);

    return saved.getUser();
  }

  private User fromUser(User from, final String tenantId) {

    User user = null;

    if (from != null && StringUtils.isNotBlank(tenantId)) {
      Map<String, String> extensions = new HashMap<>();
      extensions.put(Vocabulary.TENANT, tenantId);

      Map<String, String> metadata = from.getMetadata();

      if (metadata != null && !metadata.isEmpty())
        extensions.putAll(metadata);

      String sourcedId = from.getSourcedId();

      if (StringUtils.isBlank(sourcedId))
        sourcedId = UUID.randomUUID().toString();

      user = new User.Builder()
             .withEmail(from.getEmail())
             .withFamilyName(from.getFamilyName())
             .withGivenName(from.getGivenName())
             .withIdentifier(from.getIdentifier())
             .withMetadata(metadata)
             .withPhone(from.getPhone())
             .withRole(from.getRole())
             .withSms(from.getSms())
             .withSourcedId(sourcedId)
             .withStatus(from.getStatus())
             .withUserId(from.getUserId())
             .withUsername(from.getUsername())
             .build();
    }

    return user;

  }
}
