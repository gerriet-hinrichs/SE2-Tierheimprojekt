package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class QuestionEntry {

    public String text;

    @Id
    public long id;

    // TODO: welcher datentyp?
    //public ? sortOrder;

    protected QuestionEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public QuestionEntry(long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[id=%d, Name=%s]",
                this.getClass().getSimpleName(), id, text);
    }
}
