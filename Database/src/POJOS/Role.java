package POJOS;

import java.io.Serializable;
import java.util.*;


import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -8877691943368665971L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> users;

    public Role() {
        this.users = new ArrayList<>();
    }

    public Role(String name) {
        this.name = name;
        this.users = new ArrayList<>();
    }

    public Role(String name, Integer roleId) {
        this.name = name;
        this.roleId = roleId;
        this.users = new ArrayList<>();
    }

    public Role(Integer roleId, String name, List<User> users) {
        this.roleId = roleId;
        this.name = name;
        this.users = users != null ? users : new ArrayList<>();
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        if (!users.contains(user)) {
            users.add(user);
        }
        user.setRole(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Role role = (Role) object;
        return Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId);
    }

    @Override
    public String toString() {
        return "\nRole ID: " + roleId +
                "\nName: " + name +
                "\n----------------------";
    }
}
