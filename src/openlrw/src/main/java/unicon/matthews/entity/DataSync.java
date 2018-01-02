/**
 * 
 */
package unicon.matthews.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ggilbert
 *
 */
@Document
public class DataSync implements Serializable {
  
  public enum DataSyncType {
    canvas
  }
  
  public enum DataSyncStatus {
    fully_completed
  }
  
  
  private static final long serialVersionUID = 1L;
  
  @Id private String id;
  private String orgId;
  private String tenantId;
  private LocalDateTime syncDateTime;
  private String syncType;
  private DataSyncStatus syncStatus;
  
  private DataSync() {}
  
  public String getId() {
    return id;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public LocalDateTime getSyncDateTime() {
    return syncDateTime;
  }

  public String getSyncType() {
    return syncType;
  }

  public DataSyncStatus getSyncStatus() {
    return syncStatus;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
    result = prime * result + ((syncDateTime == null) ? 0 : syncDateTime.hashCode());
    result = prime * result + ((syncStatus == null) ? 0 : syncStatus.hashCode());
    result = prime * result + ((syncType == null) ? 0 : syncType.hashCode());
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
    DataSync other = (DataSync) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (orgId == null) {
      if (other.orgId != null)
        return false;
    } else if (!orgId.equals(other.orgId))
      return false;
    if (syncDateTime == null) {
      if (other.syncDateTime != null)
        return false;
    } else if (!syncDateTime.equals(other.syncDateTime))
      return false;
    if (syncStatus == null) {
      if (other.syncStatus != null)
        return false;
    } else if (!syncStatus.equals(other.syncStatus))
      return false;
    if (syncType == null) {
      if (other.syncType != null)
        return false;
    } else if (!syncType.equals(other.syncType))
      return false;
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    return true;
  }

  public static class Builder {
    private DataSync _dataSync = new DataSync();
    
    public Builder withId(String id) {
      _dataSync.id = id;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      _dataSync.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _dataSync.tenantId = tenantId;
      return this;
    }

    public Builder withSyncDateTime(LocalDateTime syncDateTime) {
      _dataSync.syncDateTime = syncDateTime;
      return this;
    }
    
    public Builder withSyncType(String syncType) {
      _dataSync.syncType = syncType;
      return this;
    }
    
    public Builder withSyncStatus(DataSyncStatus syncStatus) {
      _dataSync.syncStatus = syncStatus;
      return this;
    }
    
    public DataSync build() {
      return _dataSync;
    }
  }
}
