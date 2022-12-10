package org.project.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User  {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "userID", nullable = false)
    private int userId;
    @Basic
    @Column(name = "login", nullable = false, length = 45)
    private String login;
    @Basic
    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="roleID")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="personID")
    private Person person;

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", person=" + person +
                '}';
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role) && Objects.equals(person, user.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, password, role, person);
    }
}
