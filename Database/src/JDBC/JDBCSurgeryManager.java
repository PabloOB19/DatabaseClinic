package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.Sex;
import Enums.Turn;
import Enums.Type_of_appointment;
import Enums.Type_of_surgery;
import POJOS.Appointment;
import POJOS.Doctor;
import POJOS.Patient;
import POJOS.Surgery;
import ifaces.SurgeryManager;

public class JDBCSurgeryManager implements SurgeryManager
{
	private Connection c;

    public JDBCSurgeryManager(Connection c) {
        this.c = c;
    }
	
    // Nos pasa lo mismo que en la clase Patient, al ser doctor y equipment dos listas, no hace falta guardarlas
    // No hay que insertarlas dentro de mi tabla
	@Override
	public void insertSurgery(Surgery surgery) {

	    String sql = "INSERT INTO Surgery "
	            + "(id, type, date, price, turn, patient_id) "
	            + "VALUES (?, ?, ?, ?, ?, ?)";

	    try (PreparedStatement p = c.prepareStatement(sql)) {

	        p.setInt(1, surgery.getId());
	        p.setString(2, surgery.getType().name());
	        p.setDate(3, java.sql.Date.valueOf(surgery.getDate()));
	        p.setDouble(4, surgery.getPrice());
	        p.setString(5, surgery.getTurn().name());
	        p.setInt(6, surgery.getPatient().getId());

	        int affectedRows = p.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating surgery failed, no rows affected.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during insertSurgery.");
	        e.printStackTrace();
	    }
	}
	
	private Patient getPatientById(int id) throws SQLException {
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
        }

        return patient;
    }
	
	@Override
	public Surgery getSurgeryById(int id) throws SQLException {
	    Surgery surgery = null;

	    String sql = "SELECT * FROM Surgery WHERE id = ?";

	    try (PreparedStatement p = c.prepareStatement(sql)) {
	        p.setInt(1, id);

	        try (ResultSet rs = p.executeQuery()) {
	            if (rs.next()) {

	                Patient patient = getPatientById(rs.getInt("patient_id"));

	                surgery = new Surgery(
	                        rs.getInt("id"),
	                        rs.getDate("date").toLocalDate(),
	                        Type_of_surgery.valueOf(rs.getString("type")),
	                        rs.getDouble("price"),
	                        Turn.valueOf(rs.getString("turn")),
	                        patient,
	                        new ArrayList<>(),
	                        new ArrayList<>()
	                );
	            }
	        }
	    }

	    return surgery;
	}
	
	@Override
    public List<Surgery> listAllSurgeries() {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT * FROM Surgeries";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Patient patient = getPatientById(rs.getInt("patient_id"));

                Surgery surgery = new Surgery(
                        rs.getInt("id"),
                        rs.getDate("date").toLocalDate(),
                        Type_of_surgery.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        Turn.valueOf(rs.getString("turn")),
                        patient,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                list.add(surgery);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllSurgeries.");
            e.printStackTrace();
        }

        return list;
    }
	
	@Override
	public void updateSurgery(Surgery surgery) {

	    String sql = "UPDATE Surgery "
	            + "SET type = ?, date = ?, price = ?, turn = ?, patient_id = ? "
	            + "WHERE id = ?";

	    try (PreparedStatement p = c.prepareStatement(sql)) {

	        p.setString(1, surgery.getType().name());
	        p.setDate(2, java.sql.Date.valueOf(surgery.getDate()));
	        p.setDouble(3, surgery.getPrice());
	        p.setString(4, surgery.getTurn().name());
	        p.setInt(5, surgery.getPatient().getId());
	        p.setInt(6, surgery.getId());

	        int affectedRows = p.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Updating surgery failed, no rows affected.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during updateSurgery.");
	        e.printStackTrace();
	    }
	}

	@Override
	public void deleteSurgery(int id) throws SQLException{

	    String sql = "DELETE FROM Surgery WHERE id = ?";

	    try (PreparedStatement p = c.prepareStatement(sql)) {

	        p.setInt(1, id);

	        int affectedRows = p.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Deleting surgery failed, no rows affected.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during deleteSurgery.");
	        e.printStackTrace();
	    }
	}
	
	@Override
    public List<Surgery> listSurgeriesByDoctor(int doctor_Id) {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT * FROM Surgery WHERE doctor_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, doctor_Id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Surgery surgery = new Surgery(
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate(),
                            Type_of_surgery.valueOf(rs.getString("type")),
                            rs.getDouble("price"),
                            Turn.valueOf(rs.getString("turn")),
                            patient,
                            new ArrayList<>(),
                            new ArrayList<>()
                    );

                    list.add(surgery);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAppointmentsByDoctor.");
            e.printStackTrace();
        }

        return list;
    }
	
	
	@Override
    public List<Surgery> listSurgeriesByPatient(int patientId) {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT * FROM Surgery WHERE patient_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, patientId);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Surgery surgery = new Surgery(
                            rs.getInt("id"),
                            rs.getDate("date").toLocalDate(),
                            Type_of_surgery.valueOf(rs.getString("type")),
                            rs.getDouble("price"),
                            Turn.valueOf(rs.getString("turn")),
                            patient,
                            new ArrayList<>(),
                            new ArrayList<>()
                    );

                    list.add(surgery);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAppointmentsByPatient.");
            e.printStackTrace();
        }

        return list;
    }


    /*
     Faltan hacer los métodos : 
     - addDoctorToSurgery(doctor_id, surgery_id)
     - addEquipmentToSurgery(equipment_id, surgery_id)
     */

}
