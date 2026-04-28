
	package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCManagerConnection {

    private Connection c;

    public Connection manager() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            createTables();
            return c;
        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
            return null;
        }
    }

    private void createTables() {
        try {
            Statement s = c.createStatement();

            String patientTable = "CREATE TABLE IF NOT EXISTS Patient (" +
                    "dni TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "date_of_birth DATE, " +
                    "sex TEXT, " +
                    "height INT, " +
                    "weight INT, " +
                    "photo BLOB, " +
                    "phone_number INT, " +
                    "email TEXT, " +
                    "address TEXT, " +
                    "payment_method TEXT, " +
                    "clinical_story TEXT, " +
                    "personal_information TEXT)";

            String doctorTable = "CREATE TABLE IF NOT EXISTS Doctor (" +
                    "medical_license_number INT PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "photo BLOB, " +
                    "phone_number INT, " +
                    "email TEXT, " +
                    "speciality TEXT, " +
                    "salary FLOAT, " +
                    "amount_of_surgeries INT)";

            String appointmentTable = "CREATE TABLE IF NOT EXISTS Appointment (" +
                    "identificator INT PRIMARY KEY, " +
                    "type TEXT, " +
                    "date DATE, " +
                    "turn TEXT, " +
                    "price FLOAT, " +
                    "payment_status TEXT, " +
                    "patient_dni TEXT, " +
                    "doctor_license INT, " +
                    "FOREIGN KEY (patient_dni) REFERENCES Patient(dni), " +
                    "FOREIGN KEY (doctor_license) REFERENCES Doctor(medical_license_number))";

            String surgeryTable = "CREATE TABLE IF NOT EXISTS Surgery (" +
                    "identificator INT PRIMARY KEY, " +
                    "type TEXT, " +
                    "date DATE, " +
                    "turn TEXT, " +
                    "price FLOAT, " +
                    "amount_of_hours INT, " +
                    "tariff INT, " +
                    "payment_status TEXT, " +
                    "patient_dni TEXT, " +
                    "FOREIGN KEY (patient_dni) REFERENCES Patient(dni))";

            String surgeryDoctorTable = "CREATE TABLE IF NOT EXISTS Surgery_Doctor (" +
                    "surgery_id INT, " +
                    "doctor_license INT, " +
                    "PRIMARY KEY (surgery_id, doctor_license), " +
                    "FOREIGN KEY (surgery_id) REFERENCES Surgery(identificator), " +
                    "FOREIGN KEY (doctor_license) REFERENCES Doctor(medical_license_number))";

            String stockTable = "CREATE TABLE IF NOT EXISTS Stock (" +
                    "reference_code INT PRIMARY KEY, " +
                    "type TEXT, " +
                    "amount INT, " +
                    "price FLOAT, " +
                    "origin TEXT, " +
                    "description TEXT)";

            String surgeryStockTable = "CREATE TABLE IF NOT EXISTS Surgery_Stock (" +
                    "surgery_id INT, " +
                    "stock_code INT, " +
                    "amount_used INT, " +
                    "PRIMARY KEY (surgery_id, stock_code), " +
                    "FOREIGN KEY (surgery_id) REFERENCES Surgery(identificator), " +
                    "FOREIGN KEY (stock_code) REFERENCES Stock(reference_code))";

            s.executeUpdate(patientTable);
            s.executeUpdate(doctorTable);
            s.executeUpdate(appointmentTable);
            s.executeUpdate(surgeryTable);
            s.executeUpdate(surgeryDoctorTable);
            s.executeUpdate(stockTable);
            s.executeUpdate(surgeryStockTable);

            s.close();

        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }
}
