package org.github.alex.hnyp.mongodemo;

import org.github.alex.hnyp.mongodemo.document.Person1;
import org.github.alex.hnyp.mongodemo.repo.Person1Repository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Person1CollectionTest {

    @Autowired
    Person1Repository person1Repository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    void before() {
        mongoTemplate.dropCollection(Person1.class);
        mongoTemplate.createCollection(Person1.class);
    }

	@Test
	public void returnLatestVersion_whenQueryForDocument() {

//        person1Repository.save(Person1.builder().)


	}

}
