package org.github.alex.hnyp.mongodemo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Person {

    @Id
    private String id;

    private String name;

    private int age;

}
