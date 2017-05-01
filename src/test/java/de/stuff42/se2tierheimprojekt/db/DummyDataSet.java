package de.stuff42.se2tierheimprojekt.db;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Felix Koch on 01.05.2017.
 */
@Entity
public class DummyDataSet implements Serializable {

    @Id             // Key
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    protected DummyDataSet() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public DummyDataSet(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return String.format(
                "Dummy[id=%d, Name='%s']",
                id, name);
    }

}
