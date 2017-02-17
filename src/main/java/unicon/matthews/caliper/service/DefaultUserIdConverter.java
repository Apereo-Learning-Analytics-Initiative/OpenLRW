/**
 * 
 */
package unicon.matthews.caliper.service;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import unicon.matthews.Vocabulary;
import unicon.matthews.caliper.Agent;
import unicon.matthews.caliper.Event;
import unicon.matthews.tenant.Tenant;

/**
 * @author ggilbert
 *
 */
@Component
public class DefaultUserIdConverter implements UserIdConverter {

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
      }
    }
    else {
      convertedUserId = agentId;
    }
    
    return convertedUserId;
  }

}