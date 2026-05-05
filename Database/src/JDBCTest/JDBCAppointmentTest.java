package JDBCTest;

import JDBC.JDBCAppointmentManager;
import POJOS.Doctor;
import Enums.Turn;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JDBCAppointmentTest {

    private Connection connection;
    private JDBCAppointmentManager jdbcAppointmentManager;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        createAppointmentTable();
        insertAppointmentForTesting();
        jdbcAppointmentManager = new JDBCAppointmentManager(connection);
    }

    @AfterEach
    public void closeConnection() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void constructorThrowsNullPointerExceptionWhenConnectionIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new JDBCAppointmentManager(null)
        );

        assertEquals("Connection cannot be null", exception.getMessage());
    }

    @Test
    public void assignDoctorUpdatesDoctorMedicalLicenseInAppointment() throws Exception {
        Doctor doctor = new Doctor();
        doctor.setMedical_license_number(12345);

        jdbcAppointmentManager.assign_doctor(1, doctor);

        int doctorMedicalLicenseNumberStoredInDatabase = getDoctorMedicalLicenseNumberFromAppointment(1);

        assertEquals(12345, doctorMedicalLicenseNumberStoredInDatabase);
    }

    @Test
    public void assignDoctorThrowsIllegalArgumentExceptionWhenAppointmentIdentificatorIsNegative() {
        Doctor doctor = new Doctor();
        doctor.setMedical_license_number(12345);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jdbcAppointmentManager.assign_doctor(-1, doctor)
        );

        assertEquals("Appointment identificator cannot be negative", exception.getMessage());
    }

    @Test
    public void assignDoctorThrowsNullPointerExceptionWhenDoctorIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> jdbcAppointmentManager.assign_doctor(1, null)
        );

        assertEquals("Doctor cannot be null", exception.getMessage());
    }

    @Test
    public void assignDoctorThrowsIllegalArgumentExceptionWhenDoctorMedicalLicenseNumberIsNegative() {
        Doctor doctor = new Doctor();
        doctor.setMedical_license_number(-12345);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jdbcAppointmentManager.assign_doctor(1, doctor)
        );

        assertEquals("Doctor medical license number cannot be negative", exception.getMessage());
    }

    @Test
    public void changeAppointmentDateUpdatesDateAndTurnInAppointment() throws Exception {
        LocalDate newAppointmentDate = LocalDate.of(2026, 5, 20);
        Turn newAppointmentTurn = Turn.EARLY_AFTERNOON;

        jdbcAppointmentManager.change_appointment_date(1, newAppointmentDate, newAppointmentTurn);

        String sql = "SELECT date, turn FROM Appointment WHERE identificator = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                assertTrue(resultSet.next());

                LocalDate appointmentDateStoredInDatabase = resultSet.getDate("date").toLocalDate();
                String appointmentTurnStoredInDatabase = resultSet.getString("turn");

                assertEquals(newAppointmentDate, appointmentDateStoredInDatabase);
                assertEquals(newAppointmentTurn.name(), appointmentTurnStoredInDatabase);
            }
        }
    }

    @Test
    public void changeAppointmentDateThrowsIllegalArgumentExceptionWhenAppointmentIdentificatorIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jdbcAppointmentManager.change_appointment_date(
                        -1,
                        LocalDate.of(2026, 5, 20),
                        Turn.EARLY_AFTERNOON
                )
        );

        assertEquals("Appointment identificator cannot be negative", exception.getMessage());
    }

    @Test
    public void changeAppointmentDateThrowsNullPointerExceptionWhenNewDateIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> jdbcAppointmentManager.change_appointment_date(
                        1,
                        null,
                        Turn.EARLY_AFTERNOON
                )
        );

        assertEquals("New appointment date cannot be null", exception.getMessage());
    }

    @Test
    public void changeAppointmentDateThrowsNullPointerExceptionWhenTurnIsNull() {
        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> jdbcAppointmentManager.change_appointment_date(
                        1,
                        LocalDate.of(2026, 5, 20),
                        null
                )
        );

        assertEquals("Turn cannot be null", exception.getMessage());
    }

    @Test
    public void cancelAppointmentDeletesAppointmentFromDatabase() throws Exception {
        jdbcAppointmentManager.cancel_appointment(1);

        int numberOfAppointmentsWithThatIdentificator = countAppointmentsByIdentificator(1);

        assertEquals(0, numberOfAppointmentsWithThatIdentificator);
    }

    @Test
    public void cancelAppointmentThrowsIllegalArgumentExceptionWhenAppointmentIdentificatorIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jdbcAppointmentManager.cancel_appointment(-1)
        );

        assertEquals("Appointment identificator cannot be negative", exception.getMessage());
    }

    @Test
    public void calculateTotalPriceReturnsAppointmentPrice() {
        float totalPrice = jdbcAppointmentManager.calculate_total_price(1);

        assertEquals(150.75f, totalPrice);
    }

    @Test
    public void calculateTotalPriceReturnsZeroWhenAppointmentDoesNotExist() {
        float totalPrice = jdbcAppointmentManager.calculate_total_price(999);

        assertEquals(0.0f, totalPrice);
    }

    @Test
    public void calculateTotalPriceThrowsIllegalArgumentExceptionWhenAppointmentIdentificatorIsNegative() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> jdbcAppointmentManager.calculate_total_price(-1)
        );

        assertEquals("Appointment identificator cannot be negative", exception.getMessage());
    }

    private void createAppointmentTable() throws Exception {
        String sql = """
                CREATE TABLE Appointment (
                    identificator INTEGER PRIMARY KEY,
                    type TEXT,
                    date DATE NOT NULL,
                    turn TEXT NOT NULL,
                    price REAL NOT NULL,
                    patient_id INTEGER,
                    doctor_license INTEGER,
                    payment_status TEXT
                )
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private void insertAppointmentForTesting() throws Exception {
        String sql = """
                INSERT INTO Appointment
                (identificator, type, date, turn, price, patient_id, doctor_license, payment_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "RHINOPLASTY");
            preparedStatement.setDate(3, java.sql.Date.valueOf(LocalDate.of(2026, 4, 15)));
            preparedStatement.setString(4, Turn.EARLY_MORNING.name());
            preparedStatement.setFloat(5, 150.75f);
            preparedStatement.setInt(6, 1);
            preparedStatement.setNull(7, java.sql.Types.INTEGER);
            preparedStatement.setString(8, "PENDING");

            preparedStatement.executeUpdate();
        }
    }

    private int getDoctorMedicalLicenseNumberFromAppointment(int appointmentIdentificator) throws Exception {
        String sql = "SELECT doctor_license FROM Appointment WHERE identificator = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointmentIdentificator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                assertTrue(resultSet.next());
                return resultSet.getInt("doctor_license");
            }
        }
    }

    private int countAppointmentsByIdentificator(int appointmentIdentificator) throws Exception {
        String sql = "SELECT COUNT(*) FROM Appointment WHERE identificator = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, appointmentIdentificator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                assertTrue(resultSet.next());
                return resultSet.getInt(1);
            }
        }
    }
}