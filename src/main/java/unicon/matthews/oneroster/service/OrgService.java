/**
 * 
 */
package unicon.matthews.oneroster.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.Vocabulary;
import unicon.matthews.oneroster.Org;
import unicon.matthews.oneroster.exception.OrgNotFoundException;
import unicon.matthews.oneroster.service.repository.MongoOrg;
import unicon.matthews.oneroster.service.repository.MongoOrgRepository;

/**
 * @author ggilbert
 *
 */
@Service
public class OrgService {
  private final MongoOrgRepository mongoOrgRepository;
  
  @Autowired
  public OrgService(MongoOrgRepository  mongoOrgRepository) {
    this.mongoOrgRepository = mongoOrgRepository;
  }
  
  public Org save(final String tenantId, Org org) {
    if (StringUtils.isBlank(tenantId) || org == null) {
      throw new IllegalArgumentException();
    }
    
    MongoOrg existingMongoOrg 
      = mongoOrgRepository.findByTenantIdAndOrgSourcedId(tenantId, org.getSourcedId());
    MongoOrg mongoOrgToSave = null;
    
    if (existingMongoOrg == null) {
      mongoOrgToSave = 
        new MongoOrg.Builder()
          .withApiKey(UUID.randomUUID().toString())
          .withApiSecret(UUID.randomUUID().toString())
          .withTenantId(tenantId)
          .withOrg(fromOrg(org, tenantId))
          .build();
    }
    else {
      mongoOrgToSave = 
        new MongoOrg.Builder()
          .withId(existingMongoOrg.getId())
          .withApiKey(existingMongoOrg.getApiKey())
          .withApiSecret(existingMongoOrg.getApiSecret())
          .withTenantId(tenantId)
          .withOrg(fromOrg(org, tenantId))
          .build();
    }
    
    MongoOrg saved = mongoOrgRepository.save(mongoOrgToSave);
    return saved.getOrg();
  }
  
  public Org findByApiKeyAndApiSecret(final String apiKey, final String apiSecret) throws OrgNotFoundException {
    MongoOrg mongoOrg = mongoOrgRepository.findByApiKeyAndApiSecret(apiKey, apiSecret);
    
    if (mongoOrg == null) {
      throw new OrgNotFoundException();
    }
    
    return fromOrg(mongoOrg.getOrg(), mongoOrg.getTenantId());
  }
  
  public Org findByTenantIdAndOrgSourcedId(final String tenantId, final String orgSourcedId) throws OrgNotFoundException {
    MongoOrg mongoOrg = mongoOrgRepository.findByTenantIdAndOrgSourcedId(tenantId, orgSourcedId);
    
    if (mongoOrg == null) {
      throw new OrgNotFoundException();
    }

    return fromOrg(mongoOrg.getOrg(), tenantId);
  }
  
  private Org fromOrg(Org from, final String tenantId) {
    
    Org org = null;
    
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
      
      org
        = new Org.Builder()
          .withSourcedId(sourcedId)
          .withStatus(from.getStatus())
          .withDateLastModified(from.getDateLastModified())
          .withIdentifier(from.getIdentifier())
          .withMetadata(extensions)
          .withName(from.getName())
          .withType(from.getType())
          .build();
    }
    
    return org;

  }
}
