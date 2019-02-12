package org.apereo.openlrw.oneroster;

import org.apereo.model.oneroster.LineItem;
import org.apereo.model.oneroster.Link;
import org.apereo.openlrw.oneroster.service.repository.MongoLineItem;
import org.apereo.openlrw.oneroster.service.repository.MongoLineItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apereo.openlrw.FongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@WebAppConfiguration
public class MongoLineItemRepositoryTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private MongoLineItemRepository unit;

  @Test
  public void testSave() {
    Link classLink = new Link.Builder().withSourcedId("c-sid").withType("Class").build();
    LineItem li = new LineItem.Builder()
        .withSourcedId("li-sid")
        .withClass(classLink)
        .build();
    
    MongoLineItem mli
      = new MongoLineItem.Builder()
          .withTenantId("tenant-1")
          .withOrgId("org-1")
          .withClassSourcedId("c-sid")
          .withLineItem(li)
          .build();
    
    MongoLineItem saved = unit.save(mli);
    assertThat(saved, is(notNullValue()));
    assertThat(saved.getId(), is(notNullValue()));
  }
  
  @Test
  public void testFindByClass() {
  Link classLink = new Link.Builder().withSourcedId("c-sid").withType("Class").build();

  LineItem li
    = new LineItem.Builder()
      .withSourcedId("li-sid")
      .withClass(classLink)
      .build();
    
    MongoLineItem mli
      = new MongoLineItem.Builder()
          .withTenantId("tenant-1")
          .withOrgId("org-1")
          .withClassSourcedId("c-sid")
          .withLineItem(li)
          .build();
    
    unit.save(mli);

    Collection<MongoLineItem> found = unit.findByOrgIdAndClassSourcedId("org-1", "li-sid");
    
    assertThat(found, is(notNullValue()));
  }

}
