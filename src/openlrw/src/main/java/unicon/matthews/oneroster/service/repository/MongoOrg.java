/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.entity.DataSync;
import unicon.matthews.oneroster.Org;

/**
 * @author ggilbert
 *
 */
@Document
public class MongoOrg implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String id;

  private String apiKey;
  private String apiSecret;
  private String tenantId;
  
  private Org org;
  
  private Set<DataSync> dataSyncs;
  
  private MongoOrg() {}

  public String getId() {
    return id;
  }

  public String getApiKey() {
    return apiKey;
  }

  public String getApiSecret() {
    return apiSecret;
  }

  public String getTenantId() {
    return tenantId;
  }

  public Org getOrg() {
    return org;
  }
  
  public Set<DataSync> getDataSyncs() {
    return dataSyncs;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
    result = prime * result + ((apiSecret == null) ? 0 : apiSecret.hashCode());
    result = prime * result + ((dataSyncs == null) ? 0 : dataSyncs.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((org == null) ? 0 : org.hashCode());
    result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MongoOrg other = (MongoOrg) obj;
    if (apiKey == null) {
      if (other.apiKey != null)
        return false;
    } else if (!apiKey.equals(other.apiKey))
      return false;
    if (apiSecret == null) {
      if (other.apiSecret != null)
        return false;
    } else if (!apiSecret.equals(other.apiSecret))
      return false;
    if (dataSyncs == null) {
      if (other.dataSyncs != null)
        return false;
    } else if (!dataSyncs.equals(other.dataSyncs))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (org == null) {
      if (other.org != null)
        return false;
    } else if (!org.equals(other.org))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoOrg _mongoOrg = new MongoOrg();
    
    public Builder withId(String id) {
      _mongoOrg.id = id;
      return this;
    }
    
    public Builder withApiKey(String apiKey) {
      _mongoOrg.apiKey = apiKey;
      return this;
    }
    
    public Builder withApiSecret(String apiSecret) {
      _mongoOrg.apiSecret = apiSecret;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoOrg.tenantId = tenantId;
      return this;
    }
    
    public Builder withOrg(Org org) {
      _mongoOrg.org = org;
      return this;
    }
    
    public Builder withDataSyncs(Set<DataSync> dataSyncs) {
      _mongoOrg.dataSyncs = dataSyncs;
      return this;
    }
    
    public MongoOrg build() {
      
      if (StringUtils.isBlank(_mongoOrg.tenantId) 
          || StringUtils.isBlank(_mongoOrg.apiKey) 
          || StringUtils.isBlank(_mongoOrg.apiSecret)
          || _mongoOrg.org == null)
      {
        throw new IllegalStateException(_mongoOrg.toString());
      }
      
      return _mongoOrg;
    }
  }
}
