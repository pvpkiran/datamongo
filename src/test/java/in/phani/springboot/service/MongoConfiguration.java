package in.phani.springboot.service;

import in.phani.springboot.repository.OilfoxRepository;

import com.github.fakemongo.Fongo;
import com.lordofthejars.nosqlunit.mongodb.MongoDbConfiguration;
import com.lordofthejars.nosqlunit.mongodb.SpringMongoDbRule;
import com.mongodb.MockMongoClient;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by panikiran on 16.11.17.
 */
@Configuration
@EnableMongoRepositories(basePackageClasses = OilfoxRepository.class)
public class MongoConfiguration extends AbstractMongoConfiguration {

  @Bean
  protected String getDatabaseName() {
    return "test";
  }

  @Bean
  public Mongo mongo() {
    return new Fongo(getDatabaseName()).getMongo();
  }

  static SpringMongoDbRule getSpringMongoDbRule() {
    MongoDbConfiguration mongoDbConfiguration = new MongoDbConfiguration();
    mongoDbConfiguration.setDatabaseName("test");
    MongoClient mongo = MockMongoClient.create(new Fongo("test"));
    mongoDbConfiguration.setMongo(mongo);
    return new SpringMongoDbRule(mongoDbConfiguration);
  }
}
