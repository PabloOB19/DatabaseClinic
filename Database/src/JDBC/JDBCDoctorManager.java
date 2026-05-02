package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.Origin;
import Enums.Payment_status;
import Enums.Sex;
import Enums.Turn;
import Enums.Type_of_appointment;
import Enums.Type_of_material;
import POJOS.Appointment;
import POJOS.Doctor;
import POJOS.Patient;
import POJOS.Stock;
import POJOS.Surgery;

public class JDBCDoctorManager {

    private Connection c;

    public JDBCDoctorManager(Connection c) {
        this.c = c;
    }

    public void change_appointment_date(int appointment_identificator, LocalDate newDate, Turn turn) {
        String sql = "UPDATE Appointment SET date = ?, turn = ? WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(newDate));
            ps.setString(2, turn.name());
            ps.setInt(3, appointment_identificator);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing appointment date");
            e.printStackTrace();
        }
    }

    public void cancel_appointment(int appointment_identificator) {
        String sql = "DELETE FROM Appointment WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, appointment_identificator);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error cancelling appointment");
            e.printStackTrace();
        }
    }

    public void order_stock(Stock stock, int amount) {
        if (stockExists(stock.getReference_code())) {
            updateStockAmount(stock.getReference_code(), amount);
        } else {
            insertStock(stock, amount);
        }
    }

    public boolean is_available(LocalDate date, Turn turn) {
        String appointmentSql = "SELECT identificator FROM Appointment WHERE date = ? AND turn = ?";
        String surgerySql = "SELECT identificator FROM Surgery WHERE date = ? AND turn = ?";

        try {
            if (existsEventAtDateAndTurn(appointmentSql, date, turn)) {
                return false;
            }

            if (existsEventAtDateAndTurn(surgerySql, date, turn)) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking availability");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Patient patien_information(int patient_dni) {
        try {
            return getPatientByDni(String.valueOf(patient_dni));
        } catch (SQLException e) {
            System.out.println("Error getting patient information");
            e.printStackTrace();
            return null;
        }
    }

    public List<Patient> see_patients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM Patient";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(readPatient(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error seeing patients");
            e.printStackTrace();
        }

        return patients;
    }

    public List<Appointment> see_appointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(readAppointment(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error seeing appointments");
            e.printStackTrace();
        }

        return appointments;
    }

    public List<Stock> see_stock_for_surgery(Surgery surgery) {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.* FROM Stock s "
                + "JOIN SURGERY_STOCK ss ON s.reference_code = ss.stock_reference_code "
                + "WHERE ss.surgery_identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery.getIdentificator());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    stocks.add(readStock(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error seeing stock for surgery");
            e.printStackTrace();
        }

        return stocks;
    }

    private boolean stockExists(int reference_code) {
        String sql = "SELECT reference_code FROM Stock WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, reference_code);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Error checking stock");
            e.printStackTrace();
            return false;
        }
    }

    private void updateStockAmount(int reference_code, int amount) {
        String sql = "UPDATE Stock SET amount = amount + ? WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setInt(2, reference_code);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating stock amount");
            e.printStackTrace();
        }
    }

    private void insertStock(Stock stock, int amount) {
        String sql = "INSERT INTO Stock (reference_code, type, amount, price, origin, description) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, stock.getReference_code());
            ps.setString(2, stock.getType().name());
            ps.setInt(3, amount);
            ps.setFloat(4, stock.getPrice());
            ps.setString(5, stock.getOrigin().name());
            ps.setString(6, stock.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting stock");
            e.printStackTrace();
        }
    }

    private boolean existsEventAtDateAndTurn(String sql, LocalDate date, Turn turn) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, turn.name());

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Appointment readAppointment(ResultSet rs) throws SQLException {
        Patient patient = getPatientByDni(rs.getString("patient_dni"));
        Doctor doctor = getDoctorByLicense(rs.getInt("doctor_license"));

        return new Appointment(
                rs.getInt("identificator"),
                Type_of_appointment.valueOf(rs.getString("type")),
                rs.getDate("date").toLocalDate(),
                Turn.valueOf(rs.getString("turn")),
                rs.getFloat("price"),
                patient,
                doctor,
                Payment_status.valueOf(rs.getString("payment_status")));
    }

    private Patient getPatientByDni(String dni) throws SQLException {
        String sql = "SELECT * FROM Patient WHERE dni = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readPatient(rs);
                }
            }
        }

        return null;
    }

    private Patient readPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("name"),
                rs.getString("surname"),
                rs.getString("dni"),
                rs.getDate("date_of_birth").toLocalDate(),
                Sex.valueOf(rs.getString("sex")),
                rs.getInt("height"),
                rs.getInt("weight"),
                rs.getBytes("photo"),
                rs.getInt("phone_number"),
                rs.getString("email"),
                rs.getString("address"),
                rs.getString("payment_method"),
                rs.getString("clinical_history"),
                rs.getString("personal_information"));
    }

    private Doctor getDoctorByLicense(int medical_license_number) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE medical_license_number = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, medical_license_number);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(
                            rs.getInt("medical_license_number"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            Sex.valueOf(rs.getString("sex")),
                            rs.getDate("date_of_birth").toLocalDate(),
                            rs.getInt("phone_number"),
                            rs.getString("email"),
                            rs.getString("speciality"),
                            rs.getBytes("photo"),
                            rs.getDouble("salary"),
                            rs.getInt("amount_of_surgeries"));
                }
            }
        }

        return null;
    }

    private Stock readStock(ResultSet rs) throws SQLException {
        return new Stock(
                rs.getInt("reference_code"),
                Type_of_material.valueOf(rs.getString("type")),
                rs.getInt("amount"),
                rs.getFloat("price"),
                Origin.valueOf(rs.getString("origin")),
                rs.getString("description"));
    }
}
