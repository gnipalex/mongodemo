package org.github.alex.hnyp.mongodemo.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
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
    MongoTemplate mongoTemplate(MongoClient client) {
        return new MongoTemplate(client, "mydatabase");
    }

}
