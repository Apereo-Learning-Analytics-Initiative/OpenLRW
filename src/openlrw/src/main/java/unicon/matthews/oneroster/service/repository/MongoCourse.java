package unicon.matthews.oneroster.service.repository;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import unicon.matthews.oneroster.Course;

@Document
public class MongoCourse implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id private String id;
  private String orgId;
  private String tenantId;
  private String courseSourcedId;
  private Course course;
  
  private MongoCourse() {}
  
  public String getId() {
    return id;
  }
  public String getOrgId() {
    return orgId;
  }
  public String getTenantId() {
    return tenantId;
  }
  public String getCourseSourcedId() {
    return courseSourcedId;
  }
  public Course getCourse() {
    return course;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((course == null) ? 0 : course.hashCode());
    result = prime * result + ((courseSourcedId == null) ? 0 : courseSourcedId.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
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
    MongoCourse other = (MongoCourse) obj;
    if (course == null) {
      if (other.course != null)
        return false;
    } else if (!course.equals(other.course))
      return false;
    if (courseSourcedId == null) {
      if (other.courseSourcedId != null)
        return false;
    } else if (!courseSourcedId.equals(other.courseSourcedId))
      return false;
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
    if (tenantId == null) {
      if (other.tenantId != null)
        return false;
    } else if (!tenantId.equals(other.tenantId))
      return false;
    return true;
  }

  public static class Builder {
    private MongoCourse _mongoCourse = new MongoCourse();
    
    public Builder withCourseSourcedId(String courseSourcedId) {
      _mongoCourse.courseSourcedId = courseSourcedId;
      return this;
    }
    
    public Builder withId(String id) {
      _mongoCourse.id = id;
      return this;
    }
    
    public Builder withCourse(Course course) {
      _mongoCourse.course = course;
      return this;
    }
    
    public Builder withOrgId(String orgId) {
      _mongoCourse.orgId = orgId;
      return this;
    }
    
    public Builder withTenantId(String tenantId) {
      _mongoCourse.tenantId = tenantId;
      return this;
    }
    
    public MongoCourse build() {
      
      if (StringUtils.isBlank(_mongoCourse.orgId)
          || StringUtils.isBlank(_mongoCourse.tenantId)
          || StringUtils.isBlank(_mongoCourse.courseSourcedId)
          || _mongoCourse.course == null) {
        throw new IllegalStateException(_mongoCourse.toString());
      }
      
      return _mongoCourse;
    }
  }

}
