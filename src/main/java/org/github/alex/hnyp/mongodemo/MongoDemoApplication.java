package org.github.alex.hnyp.mongodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDemoApplication.class, args);
    }

//    @Autowired
//    void useMongoTemplate(MongoTemplate template) {
//        template.insert(Person.builder().age(10).name("Valera").build());
//
//        Person person = template.findOne(query(where("name").is("Valera")), Person.class);
//        System.out.println(person);
//
////        template.remove(person);
////
////        person = template.findOne(query(where("name").is("Valera")), Person.class);
////        System.out.println(person);
////
////        template.dropCollection(Person.class);
//    }

}
