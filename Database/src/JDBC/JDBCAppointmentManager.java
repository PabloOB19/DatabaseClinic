package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import Enums.Turn;
import POJOS.Doctor;

public class JDBCAppointmentManager {

    private Connection c;

    public JDBCAppointmentManager(Connection c) {
        if (c == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Connection cannot be null");
            throw nullPointerException;
        }

        this.c = c;
    }

    public void assign_doctor(int identificator, Doctor doctor)
            throws IllegalArgumentException, NullPointerException {

        if (identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        if (doctor == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Doctor cannot be null");
            throw nullPointerException;
        }

        if (doctor.getMedical_license_number() < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor medical license number cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "UPDATE Appointment SET doctor_license = ? WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, doctor.getMedical_license_number());
            ps.setInt(2, identificator);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }

    public void change_appointment_date(int identificator, LocalDate newDate, Turn turn)
            throws IllegalArgumentException, NullPointerException {

        if (identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        if (newDate == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("New appointment date cannot be null");
            throw nullPointerException;
        }

        if (turn == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Turn cannot be null");
            throw nullPointerException;
        }

        String sql = "UPDATE Appointment SET date = ?, turn = ? WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(newDate));
            ps.setString(2, turn.name());
            ps.setInt(3, identificator);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }

    public void cancel_appointment(int identificator)
            throws IllegalArgumentException {

        if (identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "DELETE FROM Appointment WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, identificator);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }

    public float calculate_total_price(int identificator)
            throws IllegalArgumentException {

        if (identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "SELECT price FROM Appointment WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, identificator);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("price");
                }
            }
        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }

        return 0.0f;
    }
}
