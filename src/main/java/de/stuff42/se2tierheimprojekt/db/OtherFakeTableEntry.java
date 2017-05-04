package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OtherFakeTableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    protected OtherFakeTableEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public OtherFakeTableEntry(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[id=%d, Name=%s]",
                this.getClass().getSimpleName(), id, name);
    }
}
