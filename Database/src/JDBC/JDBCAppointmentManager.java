package JDBC;

import java.sql.*;
import java.time.*;
import java.util.*;


import Enums.*;
import POJOS.*;
import JDBC.JDBCConnectionManager;

public class JDBCAppointmentManager {

    private Connection c;
    
    public JDBCAppointmentManager(Connection c) {
        this.c = c;
    }
    
    public void insertAppointment(Appointment appointment) {
        try {
            String sql = "INSERT INTO Appointment (type, date, price, turn, doctor_id, patient_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            p.setString(1, appointment.getType().name());
	        p.setDate(2, java.sql.Date.valueOf(appointment.getDate()));
            p.setDouble(3, appointment.getPrice());
            p.setString(4, appointment.getTurn().name());
            p.setInt(5, appointment.getDoctor().getId());
            p.setInt(6, appointment.getPatient().getId());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating appointment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = p.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating appointment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during insertAppointment.");
            e.printStackTrace();
        }
    }
    
    private Doctor getDoctorById(int id) throws SQLException {
        Doctor doctor = null;

        String sql = "SELECT * FROM Doctor WHERE id = ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1, id);

        ResultSet rs = p.executeQuery();

        if (rs.next()) {
            doctor = new Doctor(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getBytes("photo"),
                    Sex.valueOf(rs.getString("sex")),
                    rs.getString("email"),
                    rs.getString("speciality"),
                    rs.getDate("dob").toLocalDate(),
                    null,
                    null
            );
        }

        rs.close();
        p.close();

        return doctor;
    }

    private Patient getPatientById(int id) throws SQLException {
        Patient patient = null;

        String sql = "SELECT * FROM Patient WHERE id = ?";
        PreparedStatement p = c.prepareStatement(sql);
        p.setInt(1, id);

        ResultSet rs = p.executeQuery();

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

        rs.close();
        p.close();

        return patient;
    }

    
    public Appointment getAppointmentById(int id) {
        Appointment result = null;

        try {
            String sql = "SELECT * FROM Appointment WHERE id = ?";
            PreparedStatement p = c.prepareStatement(sql);
            p.setInt(1, id);

            ResultSet rs = p.executeQuery();

            if (rs.next()) {
                Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
                Patient patient = getPatientById(rs.getInt("patient_id"));

                result = new Appointment(
                        rs.getInt("id"),
                        Type_of_appointment.valueOf(rs.getString("type")),
                        rs.getDate("date").toLocalDate(),
                        Turn.valueOf(rs.getString("turn")),
                        rs.getDouble("price"),
                        doctor,
                        patient
                );
            }

            rs.close();
            p.close();

        } catch (SQLException e) {
            System.out.println("Database error during getAppointmentById.");
            e.printStackTrace();
        }

        return result;
    }


	    public List<Appointment> listAllAppointments() {
	        List<Appointment> list = new ArrayList<>();
	        try {
	            String sql = "SELECT * FROM Appointment";
	            PreparedStatement p = c.prepareStatement(sql);
	            ResultSet rs = p.executeQuery();
	            while (rs.next()) {
	            	Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
	            	Patient patient = getPatientById(rs.getInt("patient_id"));

	            	Appointment appointment = new Appointment(
	            			rs.getInt("id"),
	                        Type_of_appointment.valueOf(rs.getString("type")),
	                        rs.getDate("date").toLocalDate(),
	                        Turn.valueOf(rs.getString("turn")),
	                        rs.getDouble("price"),
	                        doctor,
	                        patient
	                );
	                list.add(appointment);
	            }
	            rs.close();
	            p.close();
	        } catch (SQLException e) {
	            System.out.println("Database error during listAllAppointments.");
	            e.printStackTrace();
	        }
	        return list;
	    }
	    
	    public void updateAppointment(Appointment appointment) {
	        try {
	            String sql = "UPDATE Appointment SET type = ?, date = ?, price = ?, turn = ?, doctor_id = ?, patient_id = ? WHERE id = ?";
	            PreparedStatement p = c.prepareStatement(sql);

	            p.setString(1, appointment.getType().name());
	            p.setDate(2, java.sql.Date.valueOf(appointment.getDate()));
	            p.setDouble(3, appointment.getPrice());
	            p.setString(4, appointment.getTurn().name());
	            p.setInt(5, appointment.getDoctor().getId());
	            p.setInt(6, appointment.getPatient().getId());
	            p.setInt(7, appointment.getId());

	            p.executeUpdate();
	            p.close();

	        } catch (SQLException e) {
	            System.out.println("Database error during updateAppointment.");
	            e.printStackTrace();
	        }
	    }

    
}

    
