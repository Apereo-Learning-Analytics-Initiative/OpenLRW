package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.oneroster.LineItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
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
}