/**
 * 
 */
package unicon.matthews.caliper;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author ggilbert
 *
 */
public class ClassEventStatistics {
  private String classSourcedId;
  private Map<String, String> metadata;
  private Integer totalEvents;
  private Integer totalStudentEnrollments;
  private Map<String, Long> eventCountGroupedByDate;
  private Map<String, Map<String, Long>> eventCountGroupedByDateAndStudent;
  
  private ClassEventStatistics() {}
  
  public String getClassSourcedId() {
    return classSourcedId;
  }
  public Map<String, String> getMetadata() {
    return metadata;
  }
  public Integer getTotalEvents() {
    return totalEvents;
  }
  public Integer getTotalStudentEnrollments() {
    return totalStudentEnrollments;
  }
  public Map<String, Long> getEventCountGroupedByDate() {
    return eventCountGroupedByDate;
  }
  
  public Map<String, Map<String, Long>> getEventCountGroupedByDateAndStudent() {
    return eventCountGroupedByDateAndStudent;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((classSourcedId == null) ? 0 : classSourcedId.hashCode());
    result = prime * result + ((eventCountGroupedByDate == null) ? 0 : eventCountGroupedByDate.hashCode());
    result = prime * result + ((eventCountGroupedByDateAndStudent == null) ? 0 : eventCountGroupedByDateAndStudent.hashCode());
    result = prime * result + ((metadata == null) ? 0 : metadata.hashCode());
    result = prime * result + ((totalEvents == null) ? 0 : totalEvents.hashCode());
    result = prime * result + ((totalStudentEnrollments == null) ? 0 : totalStudentEnrollments.hashCode());
    return result;
  }

  @Override
  public boolean equals(java.lang.Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ClassEventStatistics other = (ClassEventStatistics) obj;
    if (classSourcedId == null) {
      if (other.classSourcedId != null)
        return false;
    } else if (!classSourcedId.equals(other.classSourcedId))
      return false;
    if (eventCountGroupedByDate == null) {
      if (other.eventCountGroupedByDate != null)
        return false;
    } else if (!eventCountGroupedByDate.equals(other.eventCountGroupedByDate))
      return false;
    if (eventCountGroupedByDateAndStudent == null) {
      if (other.eventCountGroupedByDateAndStudent != null)
        return false;
    } else if (!eventCountGroupedByDateAndStudent.equals(other.eventCountGroupedByDateAndStudent))
      return false;
    if (metadata == null) {
      if (other.metadata != null)
        return false;
    } else if (!metadata.equals(other.metadata))
      return false;
    if (totalEvents == null) {
      if (other.totalEvents != null)
        return false;
    } else if (!totalEvents.equals(other.totalEvents))
      return false;
    if (totalStudentEnrollments == null) {
      if (other.totalStudentEnrollments != null)
        return false;
    } else if (!totalStudentEnrollments.equals(other.totalStudentEnrollments))
      return false;
    return true;
  }

  public static class Builder {
    private ClassEventStatistics _classEventStatistics = new ClassEventStatistics();
    
    public Builder withClassSourcedId(String classSourcedId) {
      _classEventStatistics.classSourcedId = classSourcedId;
      return this;
    }
    
    public Builder withMetadata(Map<String,String> metadata) {
      _classEventStatistics.metadata = metadata;
      return this;
    }
    
    public Builder withTotalEvents(Integer totalEvents) {
      _classEventStatistics.totalEvents = totalEvents;
      return this;
    }
    
    public Builder withTotalStudentEnrollments(Integer totalStudentEnrollments) {
      _classEventStatistics.totalStudentEnrollments = totalStudentEnrollments;
      return this;
    }
    
    public Builder withEventCountGroupedByDate(Map<String,Long> eventCountGroupedByDate) {
      _classEventStatistics.eventCountGroupedByDate = eventCountGroupedByDate;
      return this;
    }
    
    public Builder withEventCountGroupedByDateAndStudent(Map<String, Map<String, Long>> eventCountGroupedByDateAndStudent) {
      _classEventStatistics.eventCountGroupedByDateAndStudent = eventCountGroupedByDateAndStudent;
      return this;
    }
    
    public ClassEventStatistics build() {
      return _classEventStatistics;
    }
  }
}
