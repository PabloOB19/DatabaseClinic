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

    // No inserto la lista de appointments y surgeries porque al final puede tener muchas, es una relación many to many
    // No tiene sentido, ya que, no es una cosa caracter´sitica, guardar estos datos ya lo hacen el resto de clases
    @Override
    public void insertPatient(Patient patient) {

        String sql = "INSERT INTO Patient "
                + "(name, surname, email, sex, dob, height, weight, photo, info) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            p.setString(1, patient.getName());
            p.setString(2, patient.getSurname());
            p.setString(3, patient.getEmail());
            p.setString(4, patient.getSex().name());
            p.setDate(5, java.sql.Date.valueOf(patient.getDob()));
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

        } catch (SQLException e) {
            System.out.println("Database error during insertPatient.");
            e.printStackTrace();
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
                            rs.getDate("dob").toLocalDate(),
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
            e.printStackTrace();
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
                        rs.getDate("dob").toLocalDate(),
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
            e.printStackTrace();
        }

        return list;
    }
    
    
    // No dejo hacer un update del id, ya que, es lo que se utiliza para localizar al paciente que queremos modificar
    // No tiene sentido que el paciente vaya cambiando de id, como hacemos nosotros con el DNI , que tienes uno para siempre
    @Override
    public void updatePatient(Patient patient) {

        String sql = "UPDATE Patient "
                + "SET name = ?, surname = ?, email = ?, sex = ?, dob = ?, "
                + "height = ?, weight = ?, photo = ?, info = ? "
                + "WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, patient.getName());
            p.setString(2, patient.getSurname());
            p.setString(3, patient.getEmail());
            p.setString(4, patient.getSex().name());
            p.setDate(5, java.sql.Date.valueOf(patient.getDob()));
            p.setInt(6, patient.getHeight());
            p.setFloat(7, patient.getWeight());
            p.setBytes(8, patient.getPhoto());
            p.setString(9, patient.getInfo());
            p.setInt(10, patient.getId());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating patient failed, no rows affected.");
            }

        } catch (SQLException e) {
            System.out.println("Database error during updatePatient.");
            e.printStackTrace();
        }
    }

    
    @Override
    public void deletePatient(int id) {
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
                p.executeUpdate();
            }

            c.commit();

        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.out.println("Database error during deletePatient.");
            e.printStackTrace();

        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
}
