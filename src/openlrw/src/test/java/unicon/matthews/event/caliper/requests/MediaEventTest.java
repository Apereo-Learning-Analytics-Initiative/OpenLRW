/**
 * 
 */
package unicon.matthews.event.caliper.requests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.Matthews;
import unicon.matthews.caliper.Envelope;
import unicon.matthews.caliper.Event;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class})
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
          "\"@type\": \"http://purl.imsglobal.org/caliper/v1/MediaEvent\","+
          "\"actor\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                "\"@id\": \"https://example.edu/user/554433\","+
                "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/Person\","+
                "\"name\": null,"+
                "\"description\": null,"+
                "\"extensions\": { },"+
                "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
                "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
          "},"+
          "\"action\": \"http://purl.imsglobal.org/vocab/caliper/v1/action#Paused\","+
          "\"object\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"@id\": \"https://example.com/super-media-tool/video/1225\","+
              "\"@type\": \"http://purl.imsglobal.org/caliper/v1/VideoObject\","+
              "\"name\": \"American Revolution - Key Figures Video\","+
              "\"description\": null,"+
              "\"objectType\": [ ],"+
              "\"alignedLearningObjective\": ["+
                  "{"+
                      "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                      "\"@id\": \"https://example.edu/american-revolution-101/personalities/learn\","+
                      "\"@type\": \"http://purl.imsglobal.org/caliper/v1/LearningObjective\","+
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
              "\"duration\": 1420"+
          "},"+
          "\"target\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"@id\": \"https://example.com/super-media-tool/video/1225\","+
              "\"@type\": \"http://purl.imsglobal.org/caliper/v1/MediaLocation\","+
              "\"name\": null,"+
              "\"description\": null,"+
              "\"objectType\": [ ],"+
              "\"alignedLearningObjective\": [ ],"+
              "\"keywords\": [ ],"+
              "\"isPartOf\": null,"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": null,"+
              "\"datePublished\": null,"+
              "\"version\": \"1.0\","+
              "\"currentTime\": 710"+
          "},"+
          "\"generated\": null,"+
          "\"eventTime\": \"2015-09-15T10:15:00.000Z\","+
          "\"edApp\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"@id\": \"https://example.com/super-media-tool\","+
              "\"@type\": \"http://purl.imsglobal.org/caliper/v1/SoftwareApplication\","+
              "\"name\": \"Super Media Tool\","+
              "\"description\": null,"+
              "\"extensions\": { },"+
              "\"dateCreated\": \"2015-08-01T06:00:00.000Z\","+
              "\"dateModified\": \"2015-09-02T11:30:00.000Z\""+
          "},"+
          "\"group\": {"+
              "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
              "\"@id\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001/group/001\","+
              "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/Group\","+
              "\"name\": \"Discussion Group 001\","+
              "\"description\": null,"+
              "\"subOrganizationOf\": {"+
                    "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                    "\"@id\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001\","+
                    "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/CourseSection\","+
                    "\"courseNumber\": \"POL101\","+
                    "\"name\": \"American Revolution 101\","+
                    "\"description\": null,"+
                    "\"category\": null,"+
                    "\"academicSession\": \"Fall-2015\","+
                    "\"subOrganizationOf\": {"+
                      "\"@context\": \"http://purl.imsglobal.org/ctx/caliper/v1/Context\","+
                      "\"@id\": \"https://example.edu/politicalScience/2015/american-revolution-101\","+
                      "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/CourseOffering\","+
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
             "\"@id\": \"https://example.edu/politicalScience/2015/american-revolution-101/roster/554433\","+
             "\"@type\": \"http://purl.imsglobal.org/caliper/v1/lis/Membership\","+
             "\"name\": \"American Revolution 101\","+
             "\"description\": \"Roster entry\","+
             "\"member\": \"https://example.edu/user/554433\","+
             "\"organization\": \"https://example.edu/politicalScience/2015/american-revolution-101/section/001\","+
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
    Event event = envelope.getData().get(0);
    
    assertTrue(envelope != null);
  }

}
