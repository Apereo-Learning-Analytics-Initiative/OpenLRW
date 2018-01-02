package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.oneroster.Class;
import unicon.matthews.oneroster.LineItem;
import unicon.matthews.oneroster.exception.LineItemNotFoundException;
import unicon.matthews.oneroster.exception.UserNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class LineItemServiceTest {

  @Autowired
  private LineItemService lineItemService;
  
  @Test(expected=IllegalStateException.class)
  public void testSave() {
    LineItem li
      = new LineItem.Builder()
        .withAssignDate(LocalDateTime.now())
        .withSourcedId("li1")
        .withTitle("some li")
        .build();
    
    LineItem saved = lineItemService.save("t1", "o2", li);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getSourcedId(), is(notNullValue()));
  }
  
  @Test
  public void testFindByClass() throws UserNotFoundException, LineItemNotFoundException {
    
    Class klass
    = new Class.Builder()
      .withSourcedId("c-sid")
      .build();

    LineItem li
    = new LineItem.Builder()
      .withAssignDate(LocalDateTime.now())
      .withSourcedId("li1")
      .withTitle("some li")
      .withClass(klass)
      .build();
    
    lineItemService.save("t1", "o2", li);

    Collection<LineItem> lis = lineItemService.getLineItemsForClass("t1", "o2", "c-sid");
    
    assertThat(lis, is(notNullValue()));
  }

}