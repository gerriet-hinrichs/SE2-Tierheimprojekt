package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AnswerEntry {

    public String text;

    @Id
    public long id;

    // TODO: welcher datentyp?
    //public ? sortOrder;

    @ManyToOne // TODO: evtl @OneToMany
    public QuestionEntry question;

    protected AnswerEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public AnswerEntry(long id, String text, QuestionEntry question) {
        this.id = id;
        this.text = text;
        this.question = question;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[id=%d, Name=%s, QuestionId=%s]",
                this.getClass().getSimpleName(), id, text, question.id);
    }
}
