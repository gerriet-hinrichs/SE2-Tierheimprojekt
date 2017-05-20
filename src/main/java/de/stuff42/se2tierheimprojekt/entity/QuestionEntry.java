package de.stuff42.se2tierheimprojekt.entity;

import java.util.List;
import javax.persistence.*;

@Entity
public class QuestionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    @OneToMany(mappedBy = "question")
    public List<AnswerEntry> answers;

    protected QuestionEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public QuestionEntry(int sortOrder, String text) {
        this.sortOrder = sortOrder;
        this.text = text;
    }

}
