
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
                    "clinical_history TEXT, " +
                    "personal_information TEXT)";

            String doctorTable = "CREATE TABLE IF NOT EXISTS Doctor (" +
                    "medical_license_number INT PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "photo BLOB, " +
                    "sex TEXT" +
                    "date_of_birth DATE" +
                    "phone_number INT, " +
                    "email TEXT, " +
                    "speciality TEXT, " +
                    "salary DOUBLE, " +
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


            String stockTable = "CREATE TABLE IF NOT EXISTS Stock (" +
                    "reference_code INT PRIMARY KEY, " +
                    "type TEXT, " +
                    "amount INT, " +
                    "price FLOAT, " +
                    "origin TEXT, " +
                    "description TEXT)";

            String doctorSurgeryTable = "CREATE TABLE IF NOT EXISTS DOCTOR_SURGERY (" +
                    "doctor_medical_license_number TEXT NOT NULL, " +
                    "surgery_identificator INTEGER NOT NULL, " +
                    "PRIMARY KEY (doctor_medical_license_number, surgery_identificator), " +
                    "FOREIGN KEY (doctor_medical_license_number) REFERENCES DOCTOR(medical_license_number), " +
                    "FOREIGN KEY (surgery_identificator) REFERENCES SURGERY(identificator))";

            String doctorStockTable = "CREATE TABLE IF NOT EXISTS DOCTOR_STOCK (" +
                    "doctor_medical_license_number TEXT NOT NULL, " +
                    "stock_reference_code TEXT NOT NULL, " +
                    "PRIMARY KEY (doctor_medical_license_number, stock_reference_code), " +
                    "FOREIGN KEY (doctor_medical_license_number) REFERENCES DOCTOR(medical_license_number), " +
                    "FOREIGN KEY (stock_reference_code) REFERENCES STOCK(reference_code))";

            String surgeryStockTable = "CREATE TABLE IF NOT EXISTS SURGERY_STOCK (" +
                    "surgery_identificator INTEGER NOT NULL, " +
                    "stock_reference_code TEXT NOT NULL, " +
                    "PRIMARY KEY (surgery_identificator, stock_reference_code), " +
                    "FOREIGN KEY (stock_reference_code) REFERENCES STOCK(reference_code), " +
                    "FOREIGN KEY (surgery_identificator) REFERENCES SURGERY(identificator))";
          
            s.executeUpdate(patientTable);
            s.executeUpdate(doctorTable);
            s.executeUpdate(appointmentTable);
            s.executeUpdate(surgeryTable);
            s.executeUpdate(stockTable);
            s.executeUpdate(doctorSurgeryTable);
            s.executeUpdate(doctorStockTable);
            s.executeUpdate(surgeryStockTable);

            s.close();

        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }
}
