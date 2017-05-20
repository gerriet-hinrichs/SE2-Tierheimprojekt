package de.stuff42.se2tierheimprojekt.entity;

import javax.persistence.*;

@Entity
public class AnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    @ManyToOne
    public QuestionEntity question;

    protected AnswerEntity() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public AnswerEntity(int sortOrder, String text, QuestionEntity question) {
        this.sortOrder = sortOrder;
        this.text = text;
        this.question = question;
    }
}
