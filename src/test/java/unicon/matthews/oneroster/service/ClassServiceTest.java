package unicon.matthews.oneroster.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import unicon.matthews.FongoConfig;
import unicon.matthews.Matthews;
import unicon.matthews.oneroster.Class;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={Matthews.class,FongoConfig.class})
@WebAppConfiguration
public class ClassServiceTest {
  
  @Autowired
  private ClassService classService;
  
  @Test(expected=IllegalArgumentException.class)
  public void testSave() {
    Class c
      = new Class.Builder()
        .withSourcedId("c1")
        //.withTitle("some title") intentional
        .build();
    
    Class saved = classService.save("t1", "o2", c);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getSourcedId(), is(notNullValue()));
  }
  
}
