package JPA;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.*;

import POJOS.Role;
import POJOS.User;
import ifaces.UserManager;

public class JPAUser implements UserManager {

    private EntityManagerFactory emf;
    private EntityManager em;

    public JPAUser() {
        emf = Persistence.createEntityManagerFactory("miUnidad");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
        createTables();
        em.getTransaction().commit();

        if (getRoles().isEmpty()) {
            createRole(new Role("Admin"));
            createRole(new Role("Doctor"));
            createRole(new Role("Patient"));
        }
    }

    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }

        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    private void createTables() {
        String sequenceTable = "CREATE TABLE IF NOT EXISTS SEQUENCE (" +
                "SEQ_NAME TEXT PRIMARY KEY, " +
                "SEQ_COUNT INTEGER)";

        String roleTable = "CREATE TABLE IF NOT EXISTS roles (" +
                "role_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL UNIQUE)";

        String userTable = "CREATE TABLE IF NOT EXISTS users (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL UNIQUE, " +
                "email TEXT NOT NULL UNIQUE, " +
                "password TEXT NOT NULL, " +
                "role_id INTEGER, " +
                "FOREIGN KEY (role_id) REFERENCES roles(role_id))";

        em.createNativeQuery(sequenceTable).executeUpdate();
        em.createNativeQuery(roleTable).executeUpdate();
        em.createNativeQuery(userTable).executeUpdate();
        em.createNativeQuery("INSERT OR IGNORE INTO SEQUENCE (SEQ_NAME, SEQ_COUNT) VALUES ('SEQ_GEN_IDENTITY', 0)").executeUpdate();
        em.createNativeQuery("UPDATE SEQUENCE SET SEQ_COUNT = (SELECT MAX(id) FROM (" +
                "SELECT COALESCE(MAX(role_id), 0) AS id FROM roles " +
                "UNION ALL " +
                "SELECT COALESCE(MAX(user_id), 0) AS id FROM users)) " +
                "WHERE SEQ_NAME = 'SEQ_GEN_IDENTITY' AND SEQ_COUNT < (SELECT MAX(id) FROM (" +
                "SELECT COALESCE(MAX(role_id), 0) AS id FROM roles " +
                "UNION ALL " +
                "SELECT COALESCE(MAX(user_id), 0) AS id FROM users))").executeUpdate();
    }

    @Override
    public boolean register(User user) {
        try {
            em.getTransaction().begin();
            String hashedPassword = hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            em.persist(user);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            rollback();
            return false;
        }
    }

    @Override
    public void createRole(Role role) {
        try {
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        } catch (Exception e) {
            rollback();
            System.out.println("Role already exists");
        }
    }

    @Override
    public void assignRole(User user, Role role) {
        try {
            em.getTransaction().begin();

            User managedUser = em.find(User.class, user.getUserId());
            Role managedRole = em.find(Role.class, role.getRoleId());

            if (managedUser != null && managedRole != null) {
                managedUser.setRole(managedRole);
                managedRole.addUser(managedUser);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            rollback();
            System.out.println("Role could not be assigned.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
        return (List<Role>) q.getResultList();
    }

    @Override
    public Role getRole(String name) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM roles WHERE name = ?", Role.class);
            q.setParameter(1, name);
            return (Role) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User login(String username, String password) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ?", User.class);
            q.setParameter(1, username);
            User user = (User) q.getSingleResult();

            if (checkPassword(password, user.getPassword())) {
                return user;
            }

            return null;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User getUser(String username, String email) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND email = ?", User.class);
            q.setParameter(1, username);
            q.setParameter(2, email);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void updateUser(User user, String newUsername) {
        try {
            em.getTransaction().begin();

            User managedUser = em.find(User.class, user.getUserId());
            if (managedUser != null) {
                managedUser.setUsername(newUsername);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            rollback();
            System.out.println("User could not be updated.");
        }
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        try {
            em.getTransaction().begin();

            User managedUser = em.find(User.class, user.getUserId());
            if (managedUser != null) {
                String hashedPassword = hashPassword(newPassword);
                managedUser.setPassword(hashedPassword);
                user.setPassword(hashedPassword);
            } else {
                System.out.println("No user found");
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            rollback();
            System.out.println("Password could not be updated.");
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            em.getTransaction().begin();

            User managedUser = em.find(User.class, user.getUserId());
            if (managedUser != null) {
                em.remove(managedUser);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            rollback();
            System.out.println("User could not be deleted.");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        Query q = em.createNativeQuery("SELECT * FROM users", User.class);
        return (List<User>) q.getResultList();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 is not available", e);
        }
    }

    private boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }

    private void rollback() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
