/**
 * 
 */
package unicon.matthews.oneroster;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * @author ggilbert
 *
 */
@JsonDeserialize(builder = User.Builder.class)
public final class User {
  
  private String sourcedId;
  private Status status;
  private Map<String, String> metadata;
  private String username;
  private String userId;
  private String givenName;
  private String familyName;
  private Role role;
  private String identifier;
  private String email;
  private String sms;
  private String phone;
  
  private User() {}

  public String getSourcedId() {
    return sourcedId;
  }

  public Status getStatus() {
    return status;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  public String getUsername() {
    return username;
  }

  public String getUserId() {
    return userId;
  }

  public String getGivenName() {
    return givenName;
  }

  public String getFamilyName() {
    return familyName;
  }

  public Role getRole() {
    return role;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getEmail() {
    return email;
  }

  public String getSms() {
    return sms;
  }

  public String getPhone() {
    return phone;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((familyName == null) ? 0 : familyName.hashCode());
    result = prime * result + ((givenName == null) ? 0 : givenName.hashCode());
    result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((phone == null) ? 0 : phone.hashCode());
    result = prime * result + ((role == null) ? 0 : role.hashCode());
    result = prime * result + ((sms == null) ? 0 : sms.hashCode());
    result = prime * result + ((sourcedId == null) ? 0 : sourcedId.hashCode());
    result = prime * result + ((status == null) ? 0 : status.hashCode());
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
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
    User other = (User) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (familyName == null) {
      if (other.familyName != null)
        return false;
    } else if (!familyName.equals(other.familyName))
      return false;
    if (givenName == null) {
      if (other.givenName != null)
        return false;
    } else if (!givenName.equals(other.givenName))
      return false;
    if (identifier == null) {
      if (other.identifier != null)
        return false;
    } else if (!identifier.equals(other.identifier))
      return false;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (phone == null) {
      if (other.phone != null)
        return false;
    } else if (!phone.equals(other.phone))
      return false;
    if (role != other.role)
      return false;
    if (sms == null) {
      if (other.sms != null)
        return false;
    } else if (!sms.equals(other.sms))
      return false;
    if (sourcedId == null) {
      if (other.sourcedId != null)
        return false;
    } else if (!sourcedId.equals(other.sourcedId))
      return false;
    if (status != other.status)
      return false;
    if (userId == null) {
      if (other.userId != null)
        return false;
    } else if (!userId.equals(other.userId))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    } else if (!username.equals(other.username))
      return false;
    return true;
  }
  
  public static class Builder {
    private User _user = new User();

    public Builder withSourcedId(String sourcedId) {
      _user.sourcedId = sourcedId;
      return this;
    }
    
    public Builder withStatus(Status status) {
      _user.status = status;
      return this;
    }
    
    public Builder withMetadata(Map<String, String> metadata) {
      _user.metadata = metadata;
      return this;
    }
    
    public Builder withUsername(String username) {
      _user.username = username;
      return this;
    }
    
    public Builder withUserId(String userId) {
      _user.userId = userId;
      return this;
    }
    
    public Builder withGivenName(String givenName) {
      _user.givenName = givenName;
      return this;
    }
    
    public Builder withFamilyName(String familyName) {
      _user.familyName = familyName;
      return this;
    }
    
    public Builder withRole(Role role) {
      _user.role = role;
      return this;
    }
    
    public Builder withIdentifier(String identifier) {
      _user.identifier = identifier;
      return this;
    }
    
    public Builder withEmail(String email) {
      _user.email = email;
      return this;
    }
    
    public Builder withSms(String sms) {
      _user.sms = sms;
      return this;
    }
    
    public Builder withPhone(String phone) {
      _user.phone = phone;
      return this;
    }
    
    public User build() {
      return _user;
    }
  }

}
