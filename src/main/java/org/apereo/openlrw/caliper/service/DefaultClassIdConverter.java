package org.apereo.openlrw.caliper.service;

import org.apache.commons.lang3.StringUtils;
import org.apereo.openlrw.Vocabulary;
import org.apereo.openlrw.tenant.Tenant;
import org.springframework.stereotype.Component;
import org.apereo.openlrw.caliper.Event;
import org.apereo.openlrw.caliper.Group;
import org.apereo.openlrw.caliper.SubOrganizationOf;

import java.util.Map;

/**
 * @author ggilbert
 *
 */
@Component
public class DefaultClassIdConverter implements ClassIdConverter {

  @Override
  public String convert(Tenant tenant, Event event) {
    Group group = event.getGroup();
    if (group == null) {
      return null;
    }
    
    // temporary fix for NCSU issue, will be resolved soon
    if (group.getCourseNumber() != null && group.getId() != null && group.getId().contains("ncsu.edu")) {
      return group.getCourseNumber();
    }
    
    String convertedClassId = null;
    String groupId = null;
    String groupType = group.getType();
    if (isCourseSection(groupType)) {
      groupId = group.getId();
    }
    else {
      groupId = findCourseSectionId(group.getSubOrganizationOf());
    }
    
    if (StringUtils.isBlank(groupId)) {
      return null;
    }
    
    if (StringUtils.startsWith(groupId, "http")) {
      Map<String, String> tenantMetadata = tenant.getMetadata();
      if (tenantMetadata != null && !tenantMetadata.isEmpty()) {
        String tenantClassPrefix = tenantMetadata.get(Vocabulary.TENANT_CLASS_PREFIX);
        if (StringUtils.isNotBlank(tenantClassPrefix)) {
          String classIdAfterPrefix = StringUtils.substringAfter(groupId, tenantClassPrefix);
          if (StringUtils.startsWith(classIdAfterPrefix, "/")) {
            convertedClassId = StringUtils.substringAfter(classIdAfterPrefix, "/");
          }
          else {
            convertedClassId = classIdAfterPrefix;
          }
        }
      }
      else {
        convertedClassId = StringUtils.substringAfterLast(groupId, "/");
      }
    }
    else {
      convertedClassId = groupId;
    }
    
    return convertedClassId;
  }

  private boolean isCourseSection(String groupType) {
    if (StringUtils.isNotBlank(groupType) 
        && ("http://purl.imsglobal.org/caliper/v1/lis/CourseSection".equals(groupType)
            || StringUtils.contains(groupType, "CourseSection"))) {
      return true;
    }
    return false;
  }
  
  private String findCourseSectionId(SubOrganizationOf subOrganizationOf) {
    String courseSectionId = null;
    
    do {
      if (subOrganizationOf == null) {
        return null;
      }
      
      String type = subOrganizationOf.getType();
      if (isCourseSection(type)) {
        courseSectionId = subOrganizationOf.getId();
      }
      else {
        subOrganizationOf = subOrganizationOf.getSubOrganizationOf();
      }
    }
    while(StringUtils.isBlank(courseSectionId));
    
    return courseSectionId;
  }
}
