package org.project.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleID", nullable = false)
    private int roleId;

    @Basic
    @Column(name = "roleName", nullable = false, length = 45)
    private String roleName;

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Role() {
    }

    @OneToMany (mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private transient List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }



    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleId == role.roleId && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, roleName);
    }
}
