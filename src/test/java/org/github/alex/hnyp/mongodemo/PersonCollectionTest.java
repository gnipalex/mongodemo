package org.github.alex.hnyp.mongodemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.github.alex.hnyp.mongodemo.document.Person;
import org.github.alex.hnyp.mongodemo.document.PersonDocument;
import org.github.alex.hnyp.mongodemo.document.VersionedDocument;
import org.github.alex.hnyp.mongodemo.repo.PersonRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonCollectionTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void before() {
        personRepository.deleteAll();
    }

    @After
    public void after() {
        //mongoTemplate.dropCollection("person");
    }

    @Test
    public void createPersonDocument() {
        PersonDocument personToSave = PersonDocument.builder().key("person1").version(1L)
                .document(Person.builder().name("Valera").age(13).address("Kharkov, Kolomenskaya 25").build())
                .build();

        VersionedDocument<Person> savedPerson = personRepository.save(personToSave);

        assertThat(savedPerson.getId()).isNotNull();
    }

    @Test
    public void cannotCreateDocumentWithDuplicateKeyVersion() {
        personRepository.save(PersonDocument.builder().key("person1").version(1L).build());

        assertThatCode(() -> {
            personRepository.save(PersonDocument.builder().key("person1").version(1L).build());
        }).hasCauseInstanceOf(RuntimeException.class);
    }

}
