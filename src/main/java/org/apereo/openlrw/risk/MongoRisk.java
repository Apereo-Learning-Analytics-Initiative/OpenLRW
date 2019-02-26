package org.apereo.openlrw.risk;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Document
public class MongoRisk implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id private String sourcedId;
  private String orgId, tenantId, userSourcedId, classSourcedId, modelType, score, name, velocity;
  private Instant dateTime;
  private long timeZoneOffset;
  private boolean active;

  private Map<String, String> metadata;

  private MongoRisk() {}

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    MongoRisk mongoRisk = (MongoRisk) o;

    return new EqualsBuilder()
            .append(timeZoneOffset, mongoRisk.timeZoneOffset)
            .append(active, mongoRisk.active)
            .append(sourcedId, mongoRisk.sourcedId)
            .append(orgId, mongoRisk.orgId)
            .append(tenantId, mongoRisk.tenantId)
            .append(userSourcedId, mongoRisk.userSourcedId)
            .append(classSourcedId, mongoRisk.classSourcedId)
            .append(modelType, mongoRisk.modelType)
            .append(score, mongoRisk.score)
            .append(name, mongoRisk.name)
            .append(velocity, mongoRisk.velocity)
            .append(dateTime, mongoRisk.dateTime)
            .append(metadata, mongoRisk.metadata)
            .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
            .append(sourcedId)
            .append(orgId)
            .append(tenantId)
            .append(userSourcedId)
            .append(classSourcedId)
            .append(modelType)
            .append(score)
            .append(name)
            .append(velocity)
            .append(dateTime)
            .append(timeZoneOffset)
            .append(active)
            .append(metadata)
            .toHashCode();
  }

  public String getSourcedId() {
    return sourcedId;
  }

  public String getOrgId() {
    return orgId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public String getUserSourcedId() {
    return userSourcedId;
  }

  public String getClassSourcedId() {
    return classSourcedId;
  }

  public Instant getDateTime() {
    return dateTime;
  }

  public String getModelType() {
    return modelType;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public String getVelocity() {
    return velocity;
  }

  public void setVelocity(String velocity) {
    this.velocity = velocity;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setDateTime(Instant time){
      this.dateTime = time;
  }

  public boolean isActive() {
    return active;
  }

  public long getTimeZoneOffset() {
    return timeZoneOffset;
  }

  public void setTimeZoneOffset(long timeZoneOffset) {
    this.timeZoneOffset = timeZoneOffset;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }


  public static class Builder {
    private MongoRisk _Mongo_risk = new MongoRisk();
    
    public Builder withSourcedId(String sourcedId) {
      _Mongo_risk.sourcedId = sourcedId;
      return this;
    }

    public Builder withName(String name) {
        _Mongo_risk.name = name;
        return this;
    }

    public Builder withScore(String score) {
      _Mongo_risk.score = score;
      return this;
    }

    public Builder withVelocity(String velocity) {
        _Mongo_risk.velocity = velocity;
        return this;
    }

    public Builder withOrgId(String orgId) {
      _Mongo_risk.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _Mongo_risk.tenantId = tenantId;
      return this;
    }
    
    public Builder withUserSourcedId(String userSourcedId) {
      _Mongo_risk.userSourcedId = userSourcedId;
      return this;
    }
    
    public Builder withClassSourcedId(String classSourcedId) {
      _Mongo_risk.classSourcedId = classSourcedId;
      return this;
    }

    public Builder withDateTime(Instant dateTime) {
      _Mongo_risk.dateTime = dateTime;
      return this;
    }

    public Builder withTimeZoneOffset(long timeZoneOffset) {
      _Mongo_risk.timeZoneOffset = timeZoneOffset;
      return this;
    }
    
    public Builder withModelType(String modelType) {
      _Mongo_risk.modelType = modelType;
      return this;
    }
    
    public Builder withActive(boolean active) {
      _Mongo_risk.active = active;
      return this;
    }

    public Builder withMetadata(Map<String, String> metadata) {
      _Mongo_risk.metadata = metadata;
      return this;
    }
    
    public MongoRisk build() {
      return _Mongo_risk;
    }
  }

}
