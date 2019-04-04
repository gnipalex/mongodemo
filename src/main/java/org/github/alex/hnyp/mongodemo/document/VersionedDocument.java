package org.github.alex.hnyp.mongodemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

@Data
@CompoundIndexes({
        @CompoundIndex(name = "key-version-idx", def = "{'key' : 1, 'version': -1}", unique = true)
})
public abstract class VersionedDocument<D> {

    @Id
    protected String id;

    protected String key;

    protected Long version;

    protected D document;

}
