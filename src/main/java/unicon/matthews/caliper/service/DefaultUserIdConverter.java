/**
 * 
 */
package unicon.matthews.caliper.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import unicon.matthews.Vocabulary;
import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Event;
import unicon.matthews.entity.MongoUserMappingRepository;
import unicon.matthews.entity.UserMapping;
import unicon.matthews.tenant.Tenant;

/**
 * @author ggilbert
 *
 */
@Component
public class DefaultUserIdConverter implements UserIdConverter {
  
  private MongoUserMappingRepository mongoUserMappingRepository;
  @Autowired
  public DefaultUserIdConverter(
      MongoUserMappingRepository mongoUserMappingRepository) {
    this.mongoUserMappingRepository = mongoUserMappingRepository;
  }

  @Override
  public String convert(Tenant tenant, Event event) {
    
    String convertedUserId = null;
    
    Agent agent = event.getAgent();
    String agentId = agent.getId();
        
    if (StringUtils.isNotBlank(agentId)
        && StringUtils.startsWith(agentId, "http")) {
      Map<String, String> tenantMetadata = tenant.getMetadata();
      if (tenantMetadata != null && !tenantMetadata.isEmpty()) {
        String tenantUserPrefix = tenantMetadata.get(Vocabulary.TENANT_USER_PREFIX);
        if (StringUtils.isNotBlank(tenantUserPrefix)) {
          String agentIdAfterPrefix = StringUtils.substringAfter(agentId, tenantUserPrefix);
          if (StringUtils.startsWith(agentIdAfterPrefix, "/")) {
            convertedUserId = StringUtils.substringAfter(agentIdAfterPrefix, "/");
          }
          else {
            convertedUserId = agentIdAfterPrefix;
          }
        }
      }
      else {
        convertedUserId = StringUtils.substringAfterLast(agentId, "/");
        
        if (agentId.contains("ncsu.edu")) {
          UserMapping userMapping = mongoUserMappingRepository.findByTenantIdAndUserExternalIdIgnoreCase(tenant.getId(), convertedUserId);
          if (userMapping != null && userMapping.getUserSourcedId() != null) {
            return userMapping.getUserSourcedId();
          }
          else {
            return convertedUserId;
          }
        }
        
      }
    }
    else {
      convertedUserId = agentId;
    }
    
    return convertedUserId;
  }

}