package JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;


import Enums.*;
import POJOS.*;
import ifaces.AppointmentManager;

public class JDBCAppointmentManager implements AppointmentManager {

    private Connection c;

    public JDBCAppointmentManager(Connection c) {
        this.c = c;
    }

    @Override
    public void insertAppointment(Appointment appointment) {
        String sql = "INSERT INTO Appointment (type, date, price, turn, doctor_id, patient_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, appointment.getType().name());
            p.setString(2, appointment.getDate().toString());
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
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBytes("photo"),
                            Sex.valueOf(rs.getString("sex")),
                            rs.getString("email"),
                            rs.getString("specialty"),
                            LocalDate.parse(rs.getString("dob")),
                            null,
                            null
                    );
                }
            }
        }

        return doctor;
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
                            LocalDate.parse(rs.getString("dob")),
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
    public Appointment getAppointmentById(int id) {
        Appointment result = null;

        String sql = "SELECT * FROM Appointment WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    result = new Appointment(
                            rs.getInt("id"),
                            Type_of_appointment.valueOf(rs.getString("type")),
                            LocalDate.parse(rs.getString("date")),
                            Turn.valueOf(rs.getString("turn")),
                            rs.getDouble("price"),
                            doctor,
                            patient
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during getAppointmentById.");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Appointment> listAllAppointments() {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
                Patient patient = getPatientById(rs.getInt("patient_id"));

                Appointment appointment = new Appointment(
                        rs.getInt("id"),
                        Type_of_appointment.valueOf(rs.getString("type")),
                        LocalDate.parse(rs.getString("date")),
                        Turn.valueOf(rs.getString("turn")),
                        rs.getDouble("price"),
                        doctor,
                        patient
                );

                list.add(appointment);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllAppointments.");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE Appointment SET type = ?, date = ?, price = ?, turn = ?, doctor_id = ?, patient_id = ? WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, appointment.getType().name());
            p.setString(2, appointment.getDate().toString());
            p.setDouble(3, appointment.getPrice());
            p.setString(4, appointment.getTurn().name());
            p.setInt(5, appointment.getDoctor().getId());
            p.setInt(6, appointment.getPatient().getId());
            p.setInt(7, appointment.getId());

            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database error during updateAppointment.");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAppointment(int id) {
        String sql = "DELETE FROM Appointment WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error during deleteAppointment.");
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Appointment> listAppointmentsByDoctor(int doctor_Id) {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment WHERE doctor_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, doctor_Id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Appointment appointment = new Appointment(
                            rs.getInt("id"),
                            Type_of_appointment.valueOf(rs.getString("type")),
                            LocalDate.parse(rs.getString("date")),
                            Turn.valueOf(rs.getString("turn")),
                            rs.getDouble("price"),
                            doctor,
                            patient
                    );

                    list.add(appointment);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAppointmentsByDoctor.");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Appointment> listAppointmentsByPatient(int patient_Id) {
        List<Appointment> list = new ArrayList<>();

        String sql = "SELECT * FROM Appointment WHERE patient_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, patient_Id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = getDoctorById(rs.getInt("doctor_id"));
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Appointment appointment = new Appointment(
                            rs.getInt("id"),
                            Type_of_appointment.valueOf(rs.getString("type")),
                            LocalDate.parse(rs.getString("date")),
                            Turn.valueOf(rs.getString("turn")),
                            rs.getDouble("price"),
                            doctor,
                            patient
                    );

                    list.add(appointment);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAppointmentsByPatient.");
            e.printStackTrace();
        }

        return list;
    }


}

    
