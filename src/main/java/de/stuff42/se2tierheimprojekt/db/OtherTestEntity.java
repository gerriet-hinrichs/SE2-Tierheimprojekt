package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OtherTestEntity {

    public String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
}
