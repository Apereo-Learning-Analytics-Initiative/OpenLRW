package org.apereo.openlrw.risk;

import org.apereo.openlrw.FongoConfig;
import org.apereo.openlrw.OpenLRW;
import org.apereo.openlrw.risk.service.RiskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={OpenLRW.class, FongoConfig.class})
@WebAppConfiguration
public class RiskServiceTest {
    @Autowired
    private RiskService unit;

    @Test
    public void testSave() throws ParseException  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Instant date = sdf.parse("1995-02-26 00:35").toInstant();
        MongoRisk risk = new MongoRisk.Builder()
                .withClassSourcedId("class-id")
                .withUserSourcedId("user-id")
                .withName("Risk name")
                .withDateTime(date)
                .withVelocity("-1")
                .build();

        MongoRisk savedRisk = unit.save("tenant-1","org-1", risk,true);
        assertThat(savedRisk, is(notNullValue()));
        assertThat(savedRisk.getSourcedId(), is(notNullValue()));
    }


    @Test
    public void testFindByClassAndUser() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Instant date = sdf.parse("1995-02-26 00:38").toInstant();

        MongoRisk risk = new MongoRisk.Builder()
                .withClassSourcedId("class-id")
                .withUserSourcedId("user-id")
                .withName("Risk name")
                .withDateTime(date)
                .withVelocity("-1")
                .build();

        unit.save("tenant-1","org-1", risk, true);

        Collection<MongoRisk> found = unit.getRisksForUserAndClass("tenant-1","org-1", "class-id","user-id", "");
        ArrayList<MongoRisk> list = new ArrayList<>(found);

        assertThat(found, is(notNullValue()));
        assertThat(list.get(0).getClassSourcedId(), is(equalTo("class-id")));
        assertThat(list.get(0).getUserSourcedId(), is(equalTo("user-id")));
    }

    @Test
    public void testFindByClassAndUserAndDate() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Instant date = sdf.parse("2019-02-25 08:00").toInstant();

        MongoRisk risk = new MongoRisk.Builder()
                .withClassSourcedId("class-id")
                .withUserSourcedId("user-id")
                .withName("8am risk")
                .withDateTime(date)
                .withVelocity("-1")
                .build();
        unit.save("tenant-1","org-1", risk, true);

        date = sdf.parse("2019-02-25 18:00").toInstant();
        risk = new MongoRisk.Builder()
                .withClassSourcedId("class-id")
                .withUserSourcedId("user-id")
                .withName("6pm risk")
                .withDateTime(date)
                .withVelocity("1")
                .build();
        unit.save("tenant-1","org-1", risk, true);

        date = sdf.parse("2019-02-25 05:00").toInstant();
        risk = new MongoRisk.Builder()
                .withClassSourcedId("class-id")
                .withUserSourcedId("user-id")
                .withName("5am risk")
                .withDateTime(date)
                .withVelocity("-1")
                .build();
        unit.save("tenant-1","org-1", risk, true);


        Collection<MongoRisk> found = unit.getRisksForUserAndClass("tenant-1", "org-1", "class-id", "user-id", "");
        ArrayList<MongoRisk> list = new ArrayList<>(found);


        assertThat(found, is(notNullValue()));
        assertThat(list.size(), is(4));


        found = unit.getRisksForUserAndClass("tenant-1", "org-1", "class-id", "user-id", "2019-02-25 05:00");
       list = new ArrayList<>(found);

        assertThat(found, is(notNullValue()));
        assertThat(list.get(0).getName(), containsString("5am"));
        assertThat(list.size(), is(1));

        found = unit.getRisksForUserAndClass("tenant-1", "org-1", "class-id", "user-id", "latest");

        list = new ArrayList<>(found);

        assertThat(found, is(notNullValue()));
        assertThat(list.get(0).getName(), containsString("6pm"));
        assertThat(list.size(), is(1));

    }


}
