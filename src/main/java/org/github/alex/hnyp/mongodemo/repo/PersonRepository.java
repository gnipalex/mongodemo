package org.github.alex.hnyp.mongodemo.repo;

import org.github.alex.hnyp.mongodemo.document.Person;
import org.springframework.data.repository.Repository;


public interface PersonRepository extends Repository<Person, String> {

}
