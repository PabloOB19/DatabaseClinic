package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Enums.*;

import POJOS.*;
import ifaces.PatientManager;

public class JDBCPatientManager implements PatientManager{

	private Connection c;

    public JDBCPatientManager(Connection c) {
        this.c = c;
    }

    
    @Override
    public boolean insertPatient(Patient patient) {

        String sql = "INSERT INTO Patient "
                + "(name, surname, email, sex, dob, height, weight, photo, info) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, patient.getName());
            p.setString(2, patient.getSurname());
            p.setString(3, patient.getEmail());
            p.setString(4, patient.getSex().name());
            p.setString(5, patient.getDob().toString());
            p.setInt(6, patient.getHeight());
            p.setFloat(7, patient.getWeight());
            p.setBytes(8, patient.getPhoto());
            p.setString(9, patient.getInfo());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating patient failed, no rows affected.");
            }

            try (ResultSet generatedKeys = p.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    patient.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating patient failed, no ID obtained.");
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Database error during insertPatient.");
            return false;
        }
    }
    
    @Override
    public Patient getPatientById(int id) {
        Patient patient = null;

        String sql = "SELECT * FROM Patient WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient(
                            rs.getInt("id"),
                            Sex.valueOf(rs.getString("sex")),
                            rs.getString("name"),
                            rs.getString("surname"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            rs.getInt("height"),
                            rs.getFloat("weight"),
                            rs.getBytes("photo"),
                            rs.getString("info"),
                            rs.getString("email"),
                            null,
                            null
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during getPatientById.");
        }

        return patient;
    }

    @Override
    public List<Patient> listAllPatients() {
        List<Patient> list = new ArrayList<>();

        String sql = "SELECT * FROM Patient";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {

                Patient patient = new Patient(
                        rs.getInt("id"),
                        Sex.valueOf(rs.getString("sex")),
                        rs.getString("name"),
                        rs.getString("surname"),
                        JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                        rs.getInt("height"),
                        rs.getFloat("weight"),
                        rs.getBytes("photo"),
                        rs.getString("info"),
                        rs.getString("email"),
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                list.add(patient);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllPatients.");
        }

        return list;
    }
    
    
    
    @Override
    public boolean updatePatient(Patient patient) {

        String sql = "UPDATE Patient "
                + "SET name = ?, surname = ?, email = ?, sex = ?, dob = ?, "
                + "height = ?, weight = ?, photo = ?, info = ? "
                + "WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, patient.getName());
            p.setString(2, patient.getSurname());
            p.setString(3, patient.getEmail());
            p.setString(4, patient.getSex().name());
            p.setString(5, patient.getDob().toString());
            p.setInt(6, patient.getHeight());
            p.setFloat(7, patient.getWeight());
            p.setBytes(8, patient.getPhoto());
            p.setString(9, patient.getInfo());
            p.setInt(10, patient.getId());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating patient failed, no rows affected.");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Database error during updatePatient.");
            return false;
        }
    }

    
    @Override
    public boolean deletePatient(int id) {
        String deleteAppointmentsSql = "DELETE FROM Appointment WHERE patient_id = ?";

        String deleteDoctorSurgerySql =
                "DELETE FROM DOCTOR_SURGERY " +
                "WHERE surgery_id IN (SELECT id FROM Surgery WHERE patient_id = ?)";

        String deleteSurgeryEquipmentSql =
                "DELETE FROM SURGERY_EQUIPMENT " +
                "WHERE surgery_id IN (SELECT id FROM Surgery WHERE patient_id = ?)";

        String deleteSurgeriesSql = "DELETE FROM Surgery WHERE patient_id = ?";
        String deletePatientSql = "DELETE FROM Patient WHERE id = ?";

        try {
            c.setAutoCommit(false);

            try (PreparedStatement p = c.prepareStatement(deleteAppointmentsSql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deleteDoctorSurgerySql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deleteSurgeryEquipmentSql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deleteSurgeriesSql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deletePatientSql)) {
                p.setInt(1, id);
                int affectedRows = p.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting patient failed, no rows affected.");
                }
            }

            c.commit();
            return true;

        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Database rollback error during deletePatient.");
            }

            System.out.println("Database error during deletePatient.");
            return false;

        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Database error restoring auto-commit.");
            }
        }
    }

    
}
