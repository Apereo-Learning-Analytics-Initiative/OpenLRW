/**
 * 
 */
package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ggilbert
 *
 */
public class DataSync implements Serializable {
  private static final long serialVersionUID = 1L;
  private LocalDateTime syncDateTime;
  private String syncType;
  private String syncStatus;
  
  private DataSync() {}
  
  public LocalDateTime getSyncDateTime() {
    return syncDateTime;
  }

  public String getSyncType() {
    return syncType;
  }

  public String getSyncStatus() {
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
    result = prime * result + ((syncDateTime == null) ? 0 : syncDateTime.hashCode());
    result = prime * result + ((syncStatus == null) ? 0 : syncStatus.hashCode());
    result = prime * result + ((syncType == null) ? 0 : syncType.hashCode());
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
    return true;
  }

  public static class Builder {
    private DataSync _dataSync = new DataSync();
    
    public Builder withSyncDateTime(LocalDateTime syncDateTime) {
      _dataSync.syncDateTime = syncDateTime;
      return this;
    }
    
    public Builder withSyncType(String syncType) {
      _dataSync.syncType = syncType;
      return this;
    }
    
    public Builder withSyncStatus(String syncStatus) {
      _dataSync.syncStatus = syncStatus;
      return this;
    }
    
    public DataSync build() {
      return _dataSync;
    }
  }
}
