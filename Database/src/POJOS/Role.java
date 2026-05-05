package POJOS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    private static final long serialVersionUID = -8877691943368665971L;

    @Id
    @GeneratedValue(generator = "roles")
    @TableGenerator(name = "roles", table = "sqlite_sequence",
        pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "roles")
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private ArrayList<user> users;

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

    public Role(Integer roleId, String name, ArrayList<user> users) {
        this.roleId = roleId;
        this.name = name;
        this.users = users;
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

    public List<user> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<user> users) {
        this.users = users;
    }

    public void addUser(user user) {
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
