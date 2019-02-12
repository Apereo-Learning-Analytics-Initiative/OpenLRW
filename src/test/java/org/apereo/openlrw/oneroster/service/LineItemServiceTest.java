package org.apereo.openlrw.oneroster.service;

import org.apereo.model.oneroster.LineItem;
import org.apereo.model.oneroster.Link;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.oneroster.exception.LineItemNotFoundException;
import org.apereo.openlrw.oneroster.exception.UserNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.Collection;
import org.apereo.model.oneroster.Class;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
@WebAppConfiguration
public class LineItemServiceTest {

  @Autowired
  private LineItemService lineItemService;
  
  @Test(expected=IllegalStateException.class)
  public void testSave() {
    LineItem li
      = new LineItem.Builder()
        .withAssignDate(Instant.now())
        .withSourcedId("li1")
        .withTitle("some li")
        .build();
    
    LineItem saved = lineItemService.save("t1", "o2", li, true);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getSourcedId(), is(notNullValue()));
  }
  
  @Test
  public void testFindByClass() throws UserNotFoundException, LineItemNotFoundException {
    
    Class klass = new Class.Builder()
      .withSourcedId("c-sid")
      .build();

    Link classLink = new Link.Builder().withType("Class").withSourcedId("c-sid").build();

    LineItem li = new LineItem.Builder()
      .withAssignDate(Instant.now())
      .withSourcedId("li1")
      .withTitle("some li")
      .withClass(classLink)
      .build();
    
    lineItemService.save("t1", "o2", li, true);

    Collection<LineItem> lis = lineItemService.getLineItemsForClass("t1", "o2", "c-sid");
    
    assertThat(lis, is(notNullValue()));
  }

}