package org.apereo.openlrw.oneroster.service;

import org.apereo.openlrw.OpenLRW;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.apereo.model.oneroster.Class;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class,FongoConfig.class})
@WebAppConfiguration
public class ClassServiceTest {
  
  @Autowired
  private ClassService classService;
  
  @Test(expected=IllegalArgumentException.class)
  public void testSave() {
    Class c = new Class.Builder()
        .withSourcedId("c1")
        //.withTitle("some title") intentional
        .build();
    
    Class saved = classService.save("t1", "o2", c);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getSourcedId(), is(notNullValue()));
  }
  
}
