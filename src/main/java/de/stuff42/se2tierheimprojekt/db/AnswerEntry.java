package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.*;

@Entity
public class AnswerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    /* //TODO: Funktionier warum auch immer nicht
    @ManyToOne
    public QuestionEntry question;
    */

    protected AnswerEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public AnswerEntry(int sortOrder, String text, QuestionEntry question) {
        this.sortOrder = sortOrder;
        this.text = text;
        //this.question = question;
    }

}
