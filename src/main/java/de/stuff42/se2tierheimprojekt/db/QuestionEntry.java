package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.*;

@Entity
public class QuestionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    /* //TODO: Funktionier warum auch immer nicht + Array könnte auch probleme machen
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question_entry")
    public AnswerEntry[] answers;
    */

    protected QuestionEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public QuestionEntry(int sortOrder, String text) {
        this.sortOrder = sortOrder;
        this.text = text;
    }

}
