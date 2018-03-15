package unicon.matthews.oneroster.service;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.User;
import unicon.matthews.oneroster.exception.UserNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoUser;
import unicon.matthews.oneroster.service.repository.MongoUserRepository;

@Service
public class UserService {
  private static Logger logger = LoggerFactory.getLogger(UserService.class);

  private MongoUserRepository mongoUserRepository;

  @Autowired
  public UserService(MongoUserRepository mongoUserRepository) {
    this.mongoUserRepository = mongoUserRepository;
  }

  public User findBySourcedId(final String tenantId, final String orgId, final String userSourcedId) throws UserNotFoundException {

    MongoUser mongoUser = mongoUserRepository.findByTenantIdAndOrgIdAndUserSourcedIdIgnoreCase(tenantId, orgId, userSourcedId);
    if (mongoUser == null) {
      throw new UserNotFoundException();
    }

    return mongoUser.getUser();
  }

  /**
   * Finds and returns all the users that belong to a tenant and an organization given.
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
   * Deletes a user for and id given
   * @param tenantId
   * @param orgId
   * @param userId
   * @return        if user has been deleted
   */
  public boolean delete(final String tenantId, final String orgId, final String userId) {
    if (StringUtils.isBlank(tenantId) || StringUtils.isBlank(orgId) || StringUtils.isBlank(userId))
      throw new IllegalArgumentException();

    return mongoUserRepository.deleteByTenantIdAndOrgIdAndUserSourcedIdIgnoreCase(tenantId, orgId, userId) == 1;
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
      if (metadata != null && !metadata.isEmpty()) {
        extensions.putAll(metadata);
      }

      String sourcedId = from.getSourcedId();
      if (StringUtils.isBlank(sourcedId)) {
        sourcedId = UUID.randomUUID().toString();
      }

      user
              = new User.Builder()
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