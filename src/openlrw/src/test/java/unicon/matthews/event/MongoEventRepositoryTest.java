/**
 * 
 */
package unicon.matthews.event;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.caliper.Envelope;
import unicon.matthews.caliper.Event;
import unicon.matthews.caliper.service.repository.MongoEvent;
import unicon.matthews.caliper.service.repository.MongoEventRepository;
import unicon.matthews.event.caliper.requests.MediaEventTest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

/**
 * @author ggilbert
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@WebAppConfiguration
public class MongoEventRepositoryTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private MongoEventRepository unit;
    
  private Event mediaEvent;
  
  @Before
  public void init() throws JsonParseException, JsonMappingException, UnsupportedEncodingException, IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.findAndRegisterModules();
    mapper.setDateFormat(new ISO8601DateFormat());

    Envelope envelope = mapper.readValue(MediaEventTest.MEDIA_EVENT.getBytes("UTF-8"), Envelope.class);
    mediaEvent = envelope.getData().get(0);
  }
  
  @Test
  public void testSave() {
    MongoEvent mongoEvent =
        new MongoEvent.Builder()
        .withClassId("test-classid-1")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    MongoEvent saved = unit.save(mongoEvent);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getId(), is(notNullValue()));
  }

  @Test
  public void testFindOne() {
    
    unit.deleteAll();
    
    MongoEvent mongoEvent =
        new MongoEvent.Builder()
        .withClassId("test-classid-1")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    MongoEvent saved = unit.save(mongoEvent);

    MongoEvent found = unit.findOne(saved.getId());
    
    assertThat(found, is(notNullValue()));
    assertThat(found.getClassId(), is(equalTo("test-classid-1")));
  }
  
  @Test
  public void testFindByTenantIdAndOrgIdAndClassId() {
    
    unit.deleteAll();
    
    MongoEvent mongoEvent =
        new MongoEvent.Builder()
        .withClassId("test-classid-1")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    unit.save(mongoEvent);
    
    MongoEvent mongoEvent2 =
        new MongoEvent.Builder()
        .withClassId("test-classid-2")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    unit.save(mongoEvent2);


    Collection<MongoEvent> found = unit.findByTenantIdAndOrganizationIdAndClassId("test-tenantid-1", "test-orgid-1", "test-classid-1");
    
    assertThat(found, is(notNullValue()));
    assertThat(found.size(), is(equalTo(1)));
  }

  @Test
  public void testFindByTenantIdAndOrgIdAndClassIdAndUserId() {
    
    unit.deleteAll();
    
    MongoEvent mongoEvent =
        new MongoEvent.Builder()
        .withClassId("test-classid-1")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    unit.save(mongoEvent);
    
    MongoEvent mongoEvent2 =
        new MongoEvent.Builder()
        .withClassId("test-classid-2")
        .withOrganizationId("test-orgid-1")
        .withUserId("test-userid-1")
        .withTenantId("test-tenantid-1")
        .withEvent(mediaEvent)
        .build();
    
    unit.save(mongoEvent2);


    Collection<MongoEvent> found = unit.findByTenantIdAndOrganizationIdAndClassIdAndUserId("test-tenantid-1", "test-orgid-1", "test-classid-1", "test-userid-1");
    
    assertThat(found, is(notNullValue()));
    assertThat(found.size(), is(equalTo(1)));
  }

}
