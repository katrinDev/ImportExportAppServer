package org.project.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", workEmail='" + workEmail + '\'' +
                '}';
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "personID", nullable = false)
    private int personId;
    @Basic
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Basic
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;
    @Basic
    @Column(name = "patronymic", nullable = true, length = 45)
    private String patronymic;
    @Basic
    @Column(name = "workEmail", nullable = true, length = 45)
    private String workEmail;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private transient List<User> users;

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return personId == person.personId && Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(patronymic, person.patronymic) && Objects.equals(workEmail, person.workEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, name, surname, patronymic, workEmail);
    }
}
