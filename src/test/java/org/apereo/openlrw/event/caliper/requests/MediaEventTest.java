package org.apereo.openlrw.event.caliper.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apereo.openlrw.OpenLRW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.apereo.openlrw.caliper.v1p1.Envelope;


import static org.junit.Assert.assertNotNull;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class})
@WebAppConfiguration
public class MediaEventTest {

  @Autowired private ObjectMapper mapper;
  
  public static String MEDIA_EVENT =
    "{"+
      "\"sensor\": \"https://example.edu/sensor/001\","+
      "\"sendTime\": \"2015-09-15T11:05:01.000Z\","+
      "\"data\": ["+
        "{"+
          "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
          "\"type\": \"MediaEvent\","+
          "\"actor\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                "\"id\": \"https://example.edu/user/554433\","+
                "\"type\": \"Agent\","+
                "\"name\": null,"+
                "\"description\": null,"+
                "\"extensions\": { },"+
                "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
                "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
          "},"+
          "\"action\": \"Paused\","+
          "\"object\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"id\": \"https://example.com/super-media-tool/video/1225\","+
              "\"type\": \"VideoObject\","+
              "\"name\": \"American Revolution - Key Figures Video\","+
              "\"description\": null,"+
              "\"mediaType\": null,"+
              "\"learningObjectives\": ["+
                  "{"+
                      "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                      "\"id\": \"https://example.edu/american-revolution-101/personalities/learn\","+
                      "\"type\": \"LearningObjective\","+
                      "\"name\": null,"+
                      "\"description\": null,"+
                      "\"extensions\": { },"+
                      "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
                      "\"dateModified\": null"+
                   "}"+
                "],"+
              "\"keywords\": [ ],"+
              "\"isPartOf\": null,"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": \"2015-09-02T11:30:00.000Z\","+
              "\"datePublished\": null,"+
              "\"version\": \"1.0\","+
              "\"duration\": \"PT40M54S\""+
          "},"+
          "\"target\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"id\": \"https://example.com/super-media-tool/video/1225\","+
              "\"type\": \"MediaLocation\","+
              "\"name\": null,"+
              "\"description\": null,"+
              "\"mediaType\": null,"+
              "\"learningObjectives\": [ ],"+
              "\"keywords\": [ ],"+
              "\"isPartOf\": null,"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": null,"+
              "\"datePublished\": null,"+
              "\"version\": \"1.0\","+
              "\"currentTime\": \"PT30M54S\""+
          "},"+
          "\"generated\": null,"+
          "\"eventTime\": \"2015-09-15T10:15:00.000Z\","+
          "\"edApp\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"id\": \"https://example.com/super-media-tool\","+
              "\"type\": \"SoftwareApplication\","+
              "\"name\": \"Super Media Tool\","+
              "\"description\": null,"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
          "},"+
          "\"group\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"id\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001/group/001\","+
              "\"type\": \"Group\","+
              "\"name\": \"Discussion Group 001\","+
              "\"description\": null,"+
              "\"subOrganizationOf\": {"+
                    "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                    "\"id\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001\","+
                    "\"type\": \"CourseSection\","+
                    "\"courseNumber\": \"POL101\","+
                    "\"name\": \"American Revolution 101\","+
                    "\"description\": null,"+
                    "\"category\": null,"+
                    "\"academicSession\": \"Fall-2015\","+
                    "\"subOrganizationOf\": {"+
                      "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                      "\"id\": \"https://example.edu/politicalScience/2015/american-revolution-101\","+
                      "\"type\": \"CourseOffering\","+
                      "\"courseNumber\": \"POL101\","+
                      "\"name\": \"Political Science 101: The American Revolution\","+
                      "\"description\": null,"+
                      "\"academicSession\": \"Fall-2015\","+
                      "\"subOrganizationOf\": null,"+
                      "\"extensions\": { },"+
                      "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
                      "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
                    "},"+
                    "\"extensions\": { },"+
                    "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
                    "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
              "},"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": null"+
         "},"+
         "\"membership\": {"+
             "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
             "\"id\": \"https://example.edu/politicalScience/2015/american-revolution-101/roster/554433\","+
             "\"type\": \"Membership\","+
             "\"name\": \"American Revolution 101\","+
             "\"description\": \"Roster entry\","+
             "\"member\":  {"+
                "\"id\": \"https://example.edu/user/554433\","+
                "\"type\": \"Person\""+
             "},"+
             "\"organization\": {"+
                "\"id\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001\","+
                "\"type\": \"CourseSection\""+
             "},"+      
             "\"roles\": [\"http://purl.imsglobal.org/vocab/lis/v2/membership#Learner\"],"+
             "\"status\": \"http://purl.imsglobal.org/vocab/lis/v2/status#Active\","+
             "\"extensions\": { },"+
             "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
             "\"dateModified\": null"+
        "},"+
        "\"federatedSession\": null"+
      "}]}";

  @Test
  public void whenSerializeAllGood() throws Exception {
    Envelope envelope = mapper.readValue(MEDIA_EVENT.getBytes("UTF-8"), Envelope.class);
    assertNotNull(envelope);
  }

}
