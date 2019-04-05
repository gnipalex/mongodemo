package org.github.alex.hnyp.mongodemo.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "org.github.alex.hnyp.mongodemo.repo")
@Configuration
public class AppConfig {

    @Bean
    MongoClientFactoryBean mongoClient() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        return mongo;
    }

    @Bean
    MongoDbFactory mongoDbFactory(MongoClient client) {
        return new SimpleMongoDbFactory(client, "mydatabase");
    }

    @Bean
    MongoTemplate mongoTemplate(MongoDbFactory dbFactory) {
        return new MongoTemplate(dbFactory);
    }

    // supported since mongo:4.0
    @Bean
    MongoTransactionManager mongoTxManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
