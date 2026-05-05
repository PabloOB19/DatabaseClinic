package Jpa;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import POJOS.Role;
import POJOS.user;

public class JPAUser {

    private EntityManager em;

    public JPAUser() {
        em = Persistence.createEntityManagerFactory("miUnidad").createEntityManager();

        em.getTransaction().begin();
        em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
        em.getTransaction().commit();

        if (getRoles().isEmpty()) {
            createRole(new Role("Admin"));
            createRole(new Role("Doctor"));
            createRole(new Role("Patient"));
        }
    }

    public void close() {
        em.close();
    }

    public void register(user user) {
        try {
            em.getTransaction().begin();
            user.setPassword(hashPassword(user.getPassword()));
            em.persist(user);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("Username or email already exists");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
    }

    public void createRole(Role role) {
        em.getTransaction().begin();
        em.persist(role);
        em.getTransaction().commit();
    }

    public void assignRole(user user, Role role) {
        em.getTransaction().begin();

        user managedUser = em.find(user.class, user.getUserId());
        Role managedRole = em.find(Role.class, role.getRoleId());

        if (managedUser != null && managedRole != null) {
            managedRole.addUser(managedUser);
        }

        em.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRoles() {
        Query q = em.createNativeQuery("SELECT * FROM roles", Role.class);
        return (List<Role>) q.getResultList();
    }

    public Role getRole(String name) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM roles WHERE name = ?", Role.class);
            q.setParameter(1, name);
            return (Role) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public user login(String username, String password) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ?", user.class);
            q.setParameter(1, username);
            user user = (user) q.getSingleResult();

            if (checkPassword(password, user.getPassword())) {
                return user;
            }

            return null;
        } catch (NoResultException e) {
            return null;
        }
    }

    public user getUser(String username, String email) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? AND email = ?", user.class);
            q.setParameter(1, username);
            q.setParameter(2, email);
            return (user) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void updateUser(user user, String newUsername) {
        em.getTransaction().begin();

        user managedUser = em.find(user.class, user.getUserId());
        if (managedUser != null) {
            managedUser.setUsername(newUsername);
        }

        em.getTransaction().commit();
    }

    public void updatePassword(user user, String newPassword) {
        em.getTransaction().begin();

        user managedUser = em.find(user.class, user.getUserId());
        if (managedUser != null) {
            String hashedPassword = hashPassword(newPassword);
            managedUser.setPassword(hashedPassword);
            user.setPassword(hashedPassword);
        } else {
            System.out.println("No user found");
        }

        em.getTransaction().commit();
    }

    public void deleteUser(user user) {
        em.getTransaction().begin();

        user managedUser = em.find(user.class, user.getUserId());
        if (managedUser != null) {
            em.remove(managedUser);
        }

        em.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<user> getAllUsers() {
        Query q = em.createNativeQuery("SELECT * FROM users", user.class);
        return (List<user>) q.getResultList();
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
}
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
