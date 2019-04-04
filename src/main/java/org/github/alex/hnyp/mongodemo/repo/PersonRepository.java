package org.github.alex.hnyp.mongodemo.repo;

import org.github.alex.hnyp.mongodemo.document.PersonDocument;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<PersonDocument, String> {

}
