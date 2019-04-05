package org.github.alex.hnyp.mongodemo.repo;

import org.github.alex.hnyp.mongodemo.document.PersonDocument;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonRepository extends CrudRepository<PersonDocument, String> {

    // since there's an index by {key:1, version:-1} it will return the newest version
    PersonDocument findFirstByKey(String key);

    PersonDocument findFirstByKeyAndVersionDescription(String key, String versionDescription);

    PersonDocument findByKeyAndVersion(String key, Long version);

    // since there's an index by {key:1, version:-1} it will return documents sorted by version in desc order
    Collection<PersonDocument> findAllByKey(String key);

}
