package de.stuff42.se2tierheimprojekt.entity;

import java.util.List;
import javax.persistence.*;

@Entity
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public int sortOrder;

    public String text;

    @OneToMany(mappedBy = "question")
    public List<AnswerEntity> answers;

    protected QuestionEntity() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public QuestionEntity(int sortOrder, String text) {
        this.sortOrder = sortOrder;
        this.text = text;
    }

}
