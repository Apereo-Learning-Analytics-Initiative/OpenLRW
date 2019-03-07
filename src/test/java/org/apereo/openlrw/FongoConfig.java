package org.apereo.openlrw;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author ggilbert
 * @author xchopin <xavier.chopin@univ-lorraine.fr>
 */
@Configuration
@EnableMongoRepositories
@ComponentScan(basePackages={"org.apereo.openlrw.oneroster.service.repository"})
public class FongoConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
      return "fongo-test";
  }


  @Override
  protected String getMappingBasePackage() {
      return "org.apereo.openlrw";
  }

  @Bean
  @Override
  public MongoClient mongoClient() {
      Fongo queued = new Fongo("fongo");
      return queued.getMongo();
  }

  @Bean
  public MongoTemplate mongoTemplate() throws Exception {
      return new MongoTemplate(mongoClient(), getDatabaseName());
  }
}
