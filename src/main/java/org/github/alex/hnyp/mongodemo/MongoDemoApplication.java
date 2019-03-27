package org.github.alex.hnyp.mongodemo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.github.alex.hnyp.mongodemo.document.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MongoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }

    @Autowired
    void useMongoTemplate(MongoTemplate template) {
        template.insert(Person.builder().age(10).name("Valera").build());

        Person person = template.findOne(query(where("name").is("Valera")), Person.class);
        System.out.println(person);

//        template.remove(person);
//
//        person = template.findOne(query(where("name").is("Valera")), Person.class);
//        System.out.println(person);
//
//        template.dropCollection(Person.class);
    }

}
