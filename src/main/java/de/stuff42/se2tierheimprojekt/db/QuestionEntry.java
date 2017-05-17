package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    protected QuestionEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public QuestionEntry(int sortOrder, String text) {
        this.sortOrder = sortOrder;
        this.text = text;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[id=%d, SortOrder=%s, Name=%s]",
                this.getClass().getSimpleName(), id, sortOrder, text);
    }
}
