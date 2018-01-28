package unicon.matthews.entity.risk;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ggilbert
 *
 */
@Document
public class RiskScore implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id private String id;
    private String orgId;
    private String tenantId;
    private String userSourcedId;
    private String classSourcedId;
    private LocalDateTime dateTime;
    private String modelType;
    private String score;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getModelType() {
        return modelType;
    }

    public String getScore() {
        return score;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((classSourcedId == null) ? 0 : classSourcedId.hashCode());
        result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modelType == null) ? 0 : modelType.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        result = prime * result + ((score == null) ? 0 : score.hashCode());
        result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
        result = prime * result + ((userSourcedId == null) ? 0 : userSourcedId.hashCode());
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
        RiskScore other = (RiskScore) obj;
        if (active != other.active)
            return false;
        if (classSourcedId == null) {
            if (other.classSourcedId != null)
                return false;
        } else if (!classSourcedId.equals(other.classSourcedId))
            return false;
        if (dateTime == null) {
            if (other.dateTime != null)
                return false;
        } else if (!dateTime.equals(other.dateTime))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modelType == null) {
            if (other.modelType != null)
                return false;
        } else if (!modelType.equals(other.modelType))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        if (score == null) {
            if (other.score != null)
                return false;
        } else if (!score.equals(other.score))
            return false;
        if (tenantId == null) {
            if (other.tenantId != null)
                return false;
        } else if (!tenantId.equals(other.tenantId))
            return false;
        if (userSourcedId == null) {
            if (other.userSourcedId != null)
                return false;
        } else if (!userSourcedId.equals(other.userSourcedId))
            return false;
        return true;
    }

    public static class Builder {
        private RiskScore _riskScore = new RiskScore();

        public Builder withId(String id) {
            _riskScore.id = id;
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

        public Builder withDateTime(LocalDateTime dateTime) {
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
