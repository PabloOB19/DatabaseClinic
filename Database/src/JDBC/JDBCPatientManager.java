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
import Enums.Type_of_surgery;
import POJOS.Appointment;
import POJOS.Doctor;
import POJOS.Patient;
import POJOS.Stock;
import POJOS.Surgery;

public class JDBCPatientManager {

    private Connection c;

    public JDBCPatientManager(Connection c) {
        if (c == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Connection cannot be null");
            throw nullPointerException;
        }

        this.c = c;
    }

    public void book_appointment(Appointment appointment)
            throws NullPointerException, IllegalArgumentException {

        if (appointment == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment cannot be null");
            throw nullPointerException;
        }

        if (appointment.getIdentificator() < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        if (appointment.getType() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment type cannot be null");
            throw nullPointerException;
        }

        if (appointment.getDate() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment date cannot be null");
            throw nullPointerException;
        }

        if (appointment.getTurn() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment turn cannot be null");
            throw nullPointerException;
        }

        if (appointment.getPrice() < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment price cannot be negative");
            throw illegalArgumentException;
        }

        if (appointment.getPayment_status() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment payment status cannot be null");
            throw nullPointerException;
        }

        if (appointment.getPatient() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment patient cannot be null");
            throw nullPointerException;
        }

        if (appointment.getPatient().getDni() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Patient DNI cannot be null");
            throw nullPointerException;
        }

        if (appointment.getPatient().getDni().isBlank()) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Patient DNI cannot be blank");
            throw illegalArgumentException;
        }

        if (appointment.getDoctor() == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment doctor cannot be null");
            throw nullPointerException;
        }

        if (appointment.getDoctor().getMedical_license_number() < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor medical license number cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "INSERT INTO Appointment (identificator, type, date, turn, price, payment_status, patient_dni, doctor_license) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, appointment.getIdentificator());
            ps.setString(2, appointment.getType().name());
            ps.setDate(3, Date.valueOf(appointment.getDate()));
            ps.setString(4, appointment.getTurn().name());
            ps.setFloat(5, appointment.getPrice());
            ps.setString(6, appointment.getPayment_status().name());
            ps.setString(7, appointment.getPatient().getDni());
            ps.setInt(8, appointment.getDoctor().getMedical_license_number());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error booking appointment");
            e.printStackTrace();
        }
    }

    public void cancel_appointment(int appointment_identificator)
            throws IllegalArgumentException {

        if (appointment_identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "DELETE FROM Appointment WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, appointment_identificator);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error cancelling appointment");
            e.printStackTrace();
        }
    }

    public void change_appointment_date(int appointment_identificator, LocalDate newDate, Turn turn)
            throws IllegalArgumentException, NullPointerException {

        if (appointment_identificator < 0) {
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
            ps.setDate(1, Date.valueOf(newDate));
            ps.setString(2, turn.name());
            ps.setInt(3, appointment_identificator);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing appointment date");
            e.printStackTrace();
        }
    }

    public void check_appointment_date(int appointment_identificator)
            throws IllegalArgumentException {

        if (appointment_identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "SELECT date, turn FROM Appointment WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, appointment_identificator);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    if (rs.getDate("date") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Appointment date cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getString("turn") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Appointment turn cannot be null");
                        throw nullPointerException;
                    }

                    System.out.println("Appointment date: " + rs.getDate("date").toLocalDate());
                    System.out.println("Appointment turn: " + rs.getString("turn"));
                } else {
                    System.out.println("Appointment not found");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking appointment date");
            e.printStackTrace();
        }
    }

    public List<Appointment> view_appointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                appointments.add(readAppointment(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing appointments");
            e.printStackTrace();
        }

        return appointments;
    }

    public List<Surgery> view_surgeries() {
        List<Surgery> surgeries = new ArrayList<>();
        String sql = "SELECT * FROM Surgery";

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                surgeries.add(readSurgery(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error viewing surgeries");
            e.printStackTrace();
        }

        return surgeries;
    }

    private Appointment readAppointment(ResultSet rs)
            throws SQLException, NullPointerException, IllegalArgumentException {

        if (rs == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("ResultSet cannot be null");
            throw nullPointerException;
        }

        if (rs.getInt("identificator") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment identificator cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getString("type") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment type cannot be null");
            throw nullPointerException;
        }

        if (rs.getDate("date") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment date cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("turn") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment turn cannot be null");
            throw nullPointerException;
        }

        if (rs.getFloat("price") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Appointment price cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getString("payment_status") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Appointment payment status cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("patient_dni") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Patient DNI cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("patient_dni").isBlank()) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Patient DNI cannot be blank");
            throw illegalArgumentException;
        }

        if (rs.getInt("doctor_license") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor medical license number cannot be negative");
            throw illegalArgumentException;
        }

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

    private Surgery readSurgery(ResultSet rs)
            throws SQLException, NullPointerException, IllegalArgumentException {

        if (rs == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("ResultSet cannot be null");
            throw nullPointerException;
        }

        if (rs.getInt("identificator") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery identificator cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getString("type") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Surgery type cannot be null");
            throw nullPointerException;
        }

        if (rs.getDate("date") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Surgery date cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("turn") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Surgery turn cannot be null");
            throw nullPointerException;
        }

        if (rs.getFloat("price") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery price cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getInt("amount_of_hours") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery amount of hours cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getInt("tariff") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery tariff cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getString("patient_dni") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Patient DNI cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("patient_dni").isBlank()) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Patient DNI cannot be blank");
            throw illegalArgumentException;
        }

        if (rs.getString("payment_status") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Surgery payment status cannot be null");
            throw nullPointerException;
        }

        int identificator = rs.getInt("identificator");
        Patient patient = getPatientByDni(rs.getString("patient_dni"));

        return new Surgery(
                identificator,
                Type_of_surgery.valueOf(rs.getString("type")),
                rs.getDate("date").toLocalDate(),
                Turn.valueOf(rs.getString("turn")),
                rs.getFloat("price"),
                rs.getInt("amount_of_hours"),
                rs.getInt("tariff"),
                patient,
                getDoctorsBySurgery(identificator),
                getStocksBySurgery(identificator),
                Payment_status.valueOf(rs.getString("payment_status")));
    }

    private Patient getPatientByDni(String dni)
            throws SQLException, NullPointerException, IllegalArgumentException {

        if (dni == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Patient DNI cannot be null");
            throw nullPointerException;
        }

        if (dni.isBlank()) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Patient DNI cannot be blank");
            throw illegalArgumentException;
        }

        String sql = "SELECT * FROM Patient WHERE dni = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    if (rs.getString("name") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Patient name cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getString("surname") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Patient surname cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getString("dni") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Patient DNI cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getDate("date_of_birth") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Patient date of birth cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getString("sex") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Patient sex cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getInt("height") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Patient height cannot be negative");
                        throw illegalArgumentException;
                    }

                    if (rs.getInt("weight") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Patient weight cannot be negative");
                        throw illegalArgumentException;
                    }

                    if (rs.getInt("phone_number") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Patient phone number cannot be negative");
                        throw illegalArgumentException;
                    }

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
            }
        }

        return null;
    }

    private Doctor getDoctorByLicense(int medical_license_number)
            throws SQLException, IllegalArgumentException {

        if (medical_license_number < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor medical license number cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "SELECT * FROM Doctor WHERE medical_license_number = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, medical_license_number);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return readDoctor(rs);
                }
            }
        }

        return null;
    }

    private List<Doctor> getDoctorsBySurgery(int surgery_identificator)
            throws SQLException, IllegalArgumentException {

        if (surgery_identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery identificator cannot be negative");
            throw illegalArgumentException;
        }

        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT d.* FROM Doctor d "
                + "JOIN DOCTOR_SURGERY ds ON d.medical_license_number = ds.doctor_medical_license_number "
                + "WHERE ds.surgery_identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery_identificator);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    doctors.add(readDoctor(rs));
                }
            }
        }

        return doctors;
    }

    private Doctor readDoctor(ResultSet rs)
            throws SQLException, NullPointerException, IllegalArgumentException {

        if (rs == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("ResultSet cannot be null");
            throw nullPointerException;
        }

        if (rs.getInt("medical_license_number") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor medical license number cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getString("name") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Doctor name cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("surname") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Doctor surname cannot be null");
            throw nullPointerException;
        }

        if (rs.getString("sex") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Doctor sex cannot be null");
            throw nullPointerException;
        }

        if (rs.getDate("date_of_birth") == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Doctor date of birth cannot be null");
            throw nullPointerException;
        }

        if (rs.getInt("phone_number") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor phone number cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getDouble("salary") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor salary cannot be negative");
            throw illegalArgumentException;
        }

        if (rs.getInt("amount_of_surgeries") < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Doctor amount of surgeries cannot be negative");
            throw illegalArgumentException;
        }

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

    private List<Stock> getStocksBySurgery(int surgery_identificator)
            throws SQLException, IllegalArgumentException {

        if (surgery_identificator < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Surgery identificator cannot be negative");
            throw illegalArgumentException;
        }

        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT s.* FROM Stock s "
                + "JOIN SURGERY_STOCK ss ON s.reference_code = ss.stock_reference_code "
                + "WHERE ss.surgery_identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery_identificator);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    if (rs.getInt("reference_code") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Stock reference code cannot be negative");
                        throw illegalArgumentException;
                    }

                    if (rs.getString("type") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Stock type cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getInt("amount") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Stock amount cannot be negative");
                        throw illegalArgumentException;
                    }

                    if (rs.getFloat("price") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Stock price cannot be negative");
                        throw illegalArgumentException;
                    }

                    if (rs.getString("origin") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Stock origin cannot be null");
                        throw nullPointerException;
                    }

                    if (rs.getString("description") == null) {
                        NullPointerException nullPointerException =
                                new NullPointerException("Stock description cannot be null");
                        throw nullPointerException;
                    }

                    stocks.add(new Stock(
                            rs.getInt("reference_code"),
                            Type_of_material.valueOf(rs.getString("type")),
                            rs.getInt("amount"),
                            rs.getFloat("price"),
                            Origin.valueOf(rs.getString("origin")),
                            rs.getString("description")));
                }
            }
        }

        return stocks;
    }
}