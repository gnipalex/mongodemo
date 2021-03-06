package org.github.alex.hnyp.mongodemo.document;

import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class PersonDocument extends VersionedDocument<Person> {

    @Builder
    public PersonDocument(String key, Long version, String versionDescription, Person document) {
        this.key = key;
        this.version = version;
        this.versionDescription = versionDescription;
        this.document = document;
    }

}
