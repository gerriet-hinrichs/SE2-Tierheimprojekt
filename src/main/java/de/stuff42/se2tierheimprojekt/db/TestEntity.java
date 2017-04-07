package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.*;

@Entity
public class TestEntity {

    public String name;

    @OneToOne
    public OtherTestEntity other;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
}
