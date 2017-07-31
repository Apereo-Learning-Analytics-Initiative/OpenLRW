/**
 * 
 */
package unicon.matthews.oneroster.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicon.matthews.Vocabulary;
import unicon.matthews.entity.DataSync;
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
  
  public Org findByApiKey(final String apiKey) throws OrgNotFoundException {
    MongoOrg mongoOrg = mongoOrgRepository.findByApiKey(apiKey);
    
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
  
  public DataSync findLatestDataSync(final String tenantId, final String orgSourcedId, final String syncType) {
    MongoOrg mongoOrg = mongoOrgRepository.findByTenantIdAndOrgSourcedId(tenantId, orgSourcedId);
    Set<DataSync> dataSyncs = mongoOrg.getDataSyncs();
    
    DataSync latestDataSync = null;
    
    if (dataSyncs != null && !dataSyncs.isEmpty()) {
      dataSyncs
        .stream()
        .filter(dataSync -> dataSync.getSyncType().equals(syncType))
        .collect(Collectors.toList())
        .sort(new Comparator<DataSync>() {

          @Override
          public int compare(DataSync o1, DataSync o2) {
            return o1.getSyncDateTime().compareTo(o2.getSyncDateTime());
          }
        });
    }
    
    return latestDataSync;
  }
  
  public void saveDataSync(final String tenantId, final String orgSourcedId, final DataSync dataSync) {
    MongoOrg mongoOrg = mongoOrgRepository.findByTenantIdAndOrgSourcedId(tenantId, orgSourcedId);
    Set<DataSync> dataSyncs = mongoOrg.getDataSyncs();
    if (dataSyncs == null) {
      dataSyncs = new HashSet<>();
    }
    
    dataSyncs.add(dataSync);
    
    MongoOrg updatedMongoOrg
      = new MongoOrg.Builder()
        .withApiKey(mongoOrg.getApiKey())
        .withApiSecret(mongoOrg.getApiSecret())
        .withDataSyncs(dataSyncs)
        .withId(mongoOrg.getId())
        .withOrg(mongoOrg.getOrg())
        .withTenantId(mongoOrg.getTenantId())
        .build();
    
    mongoOrgRepository.save(updatedMongoOrg);
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
