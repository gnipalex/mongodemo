package org.github.alex.hnyp.mongodemo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.github.alex.hnyp.mongodemo.document.Person;
import org.github.alex.hnyp.mongodemo.document.PersonDocument;
import org.github.alex.hnyp.mongodemo.repo.PersonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonCollectionTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    MongoTransactionManager txManager;

    @Before
    public void before() {
        personRepository.deleteAll();
    }

    @Test
    public void canCreatePersonDocument() {
        // given document to save
        PersonDocument personToSave = PersonDocument.builder().key("person1").version(1L)
                .document(samplePerson().build()).build();

        // when document is saved to collection
        PersonDocument savedPerson = personRepository.save(personToSave);

        // then id is generated
        assertThat(savedPerson.getId()).isNotNull();

        // and persisted document is equal to initial
        Optional<PersonDocument> retrievedDocument = personRepository.findById(savedPerson.getId());
        assertThat(retrievedDocument).hasValue(personToSave);
    }

    @Test
    public void canCreateSamePersonDocumentWithNewVersion() {
        // given document with key and version exists
        PersonDocument existingPerson = personRepository
                .save(PersonDocument.builder().key("person1").version(1L)
                        .document(samplePerson().build())
                        .build()
                );

        // when document with same key but updated version is saved
        PersonDocument updatedPerson = personRepository.save(
                PersonDocument.builder().key("person1").version(2L)
                        .document(samplePerson().build())
                        .build()
        );

        // then id is generated
        assertThat(updatedPerson.getId()).isNotNull();

        // and ids differ
        assertThat(updatedPerson.getId()).isNotEqualTo(existingPerson.getId());
    }

    @Test
    public void cannotCreateDocumentWithDuplicateKeyVersion() {
        // given document with key and version exists
        personRepository.save(PersonDocument.builder().key("person1").version(1L).build());

        // expect exception when saving document with same key and version
        assertThatCode(() -> {
            personRepository.save(PersonDocument.builder().key("person1").version(1L).build());
        }).hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    public void canRetrieveDocumentByKeyOfLastVersion() {
        // given several documents with same key exist
        PersonDocument existing1 = personRepository.save(
                PersonDocument.builder().key("person1").version(1L)
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing2 = personRepository.save(
                PersonDocument.builder().key("person1").version(3L)
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing3 = personRepository.save(
                PersonDocument.builder().key("person1").version(2L)
                        .document(samplePerson().build())
                        .build()
        );

        // when getting one document by key
        PersonDocument retrievedDocument = personRepository.findFirstByKey("person1");

        // then retrived document is of last version
        assertThat(retrievedDocument).isEqualTo(existing2);
    }

    @Test
    public void retrievedDocumentsByKeyAreSortedByVersionInDescendingOrder() {
        // given several documents with same key exist
        PersonDocument existing1 = personRepository.save(
                PersonDocument.builder().key("person1").version(1L)
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing2 = personRepository.save(
                PersonDocument.builder().key("person1").version(3L)
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing3 = personRepository.save(
                PersonDocument.builder().key("person1").version(2L)
                        .document(samplePerson().build())
                        .build()
        );

        // when retrieving all documents by key
        Collection<PersonDocument> documentsByKey = personRepository.findAllByKey("person1");

        // then documents are sorted by version
        assertThat(documentsByKey).containsExactly(existing2, existing3, existing1);
    }

    @Test
    public void canFindDocumentByKeyAndVersionDescription() {
        // given several documents with same key exist
        PersonDocument existing1 = personRepository.save(
                PersonDocument.builder().key("person1").version(1L).versionDescription("description1")
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing2 = personRepository.save(
                PersonDocument.builder().key("person1").version(3L).versionDescription("description3")
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing3 = personRepository.save(
                PersonDocument.builder().key("person1").version(2L).versionDescription("description2")
                        .document(samplePerson().build())
                        .build()
        );

        // when getting one document by key and version description
        PersonDocument retrievedDocument = personRepository.findFirstByKeyAndVersionDescription("person1",
                "description1");

        // then expected document is returned
        assertThat(retrievedDocument).isEqualTo(existing1);
    }

    @Test
    public void canFindDocumentByKeyAndVersionDescriptionOfLastVersion() {
        // given several documents with same key and same version description
        PersonDocument existing1 = personRepository.save(
                PersonDocument.builder().key("person1").version(1L).versionDescription("description1")
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing2 = personRepository.save(
                PersonDocument.builder().key("person1").version(3L).versionDescription("description1")
                        .document(samplePerson().build())
                        .build()
        );
        PersonDocument existing3 = personRepository.save(
                PersonDocument.builder().key("person1").version(2L).versionDescription("description1")
                        .document(samplePerson().build())
                        .build()
        );

        // when getting one document by key and version description
        PersonDocument retrievedDocument = personRepository.findFirstByKeyAndVersionDescription("person1",
                "description1");

        // then expected document of last version is returned
        assertThat(retrievedDocument).isEqualTo(existing2);
    }

    @Test
    public void documentIsNotSaved_whenUsingTransaction_andExceptionOccursInsideTransaction() {
        // setup
        TransactionTemplate txTemplate = new TransactionTemplate(txManager);

        // when saving document in transaction
        try {
            txTemplate.execute(ctx -> {
                personRepository.save(PersonDocument.builder().key("person1").version(2L).build());

                assertThat(personRepository.findFirstByKey("person1")).isNotNull();

                throw new IllegalArgumentException();
            });
        } catch (RuntimeException e) {
            // suppress exception
        }

        // then document is not saved
        assertThat(personRepository.findAll()).isEmpty();
    }

    static Person.PersonBuilder samplePerson() {
        return Person.builder()
                .name("Valera")
                .age(13)
                .address("Kharkov, Kolomenskaya 25");
    }

}
