package org.apereo.openlrw.entity.risk;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

@Document
public class RiskScore implements Serializable {
  private static final long serialVersionUID = 1L;
  
  @Id private String id;
  private String orgId;
  private String tenantId;
  private String userSourcedId;
  private String classSourcedId;
  private Instant dateTime;
  private String modelType;
  private String score;
  private String name;
  private String velocity;
  private boolean active;
  
  private RiskScore() {}

  public String getId() {
    return id;
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

  public boolean isActive() {
    return active;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RiskScore riskScore = (RiskScore) o;
    return active == riskScore.active &&
            Objects.equals(id, riskScore.id) &&
            Objects.equals(orgId, riskScore.orgId) &&
            Objects.equals(tenantId, riskScore.tenantId) &&
            Objects.equals(userSourcedId, riskScore.userSourcedId) &&
            Objects.equals(classSourcedId, riskScore.classSourcedId) &&
            Objects.equals(dateTime, riskScore.dateTime) &&
            Objects.equals(modelType, riskScore.modelType) &&
            Objects.equals(score, riskScore.score) &&
            Objects.equals(name, riskScore.name) &&
            Objects.equals(velocity, riskScore.velocity);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, orgId, tenantId, userSourcedId, classSourcedId, dateTime, modelType, score, name, velocity, active);
  }


  public static class Builder {
    private RiskScore _riskScore = new RiskScore();
    
    public Builder withId(String id) {
      _riskScore.id = id;
      return this;
    }

    public Builder withName(String name) {
        _riskScore.name = name;
        return this;
    }

    public Builder withVelocity(String velocity) {
        _riskScore.velocity = velocity;
        return this;
    }

    public Builder withOrgId(String orgId) {
      _riskScore.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _riskScore.tenantId = tenantId;
      return this;
    }
    
    public Builder withUserSourcedId(String userSourcedId) {
      _riskScore.userSourcedId = userSourcedId;
      return this;
    }
    
    public Builder withClassSourcedId(String classSourcedId) {
      _riskScore.classSourcedId = classSourcedId;
      return this;
    }

    public Builder withDateTime(Instant dateTime) {
      _riskScore.dateTime = dateTime;
      return this;
    }
    
    public Builder withModelType(String modelType) {
      _riskScore.modelType = modelType;
      return this;
    }
    
    public Builder withActive(boolean active) {
      _riskScore.active = active;
      return this;
    }
    
    public RiskScore build() {
      return _riskScore;
    }
  }

}
