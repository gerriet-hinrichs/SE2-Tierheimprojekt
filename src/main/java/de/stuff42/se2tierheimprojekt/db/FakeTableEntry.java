package de.stuff42.se2tierheimprojekt.db;


import javax.persistence.*;

@Entity
public class FakeTableEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    // @Column(nullable = false)
    public String name;

    @OneToOne
    public OtherFakeTableEntry other;

    protected FakeTableEntry() {
        // no-args constructor required by JPA spec
        // this one is protected since it shouldn't be used directly
    }

    public FakeTableEntry(String name, OtherFakeTableEntry other) {
        this.name = name;
        this.other = other;
    }

    @Override
    public String toString() {
        return String.format(
                "<%s>[id=%d, Name=%s, other=%s]",
                this.getClass().getSimpleName(), id, name, other);
    }
}
