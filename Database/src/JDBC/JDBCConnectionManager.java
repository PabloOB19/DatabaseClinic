 
package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCConnectionManager {

    private Connection c;

    public Connection manager() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:db/database.db");
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
                    "id TEXT PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "email TEXT, " +
                    "sex TEXT, " +
                    "dob DATE, " +
                    "height INTEGER, " +
                    "weight FLOAT, " +
                    "photo BLOB, " +
                    "info TEXT)";

            String doctorTable = "CREATE TABLE IF NOT EXISTS Doctor (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "surname TEXT, " +
                    "email TEXT, " +
                    "sex TEXT, " +
                    "dob DATE, " +
                    "photo BLOB, " +
                    "speciality TEXT)";
            
            String appointmentTable = "CREATE TABLE IF NOT EXISTS Appointment (" +
                    "id INTEGER PRIMARY KEY, " +
                    "type TEXT, " +
                    "date DATE, " +
                    "price DOUBLE, " +
                    "turn TEXT, " +
                    "doctor_id INTEGER, " +
                    "patient_id TEXT, " +
                    "FOREIGN KEY (doctor_id) REFERENCES Doctor(id), " +
                    "FOREIGN KEY (patient_id) REFERENCES Patient(id))";

            String surgeryTable = "CREATE TABLE IF NOT EXISTS Surgery (" +
                    "id INTEGER PRIMARY KEY, " +
                    "type TEXT, " +
                    "date DATE, " +
                    "price DOUBLE, " +
                    "turn TEXT, " +
                    "patient_id TEXT, " +
                    "FOREIGN KEY (patient_id) REFERENCES Patient(id))";


            String equipmentTable = "CREATE TABLE IF NOT EXISTS Equipment (" +
                    "id INTEGER PRIMARY KEY, " +
                    "name TEXT, " +
                    "category TEXT, " +
                    "quantity INTEGER, " +
                    "price DOUBLE, " +
                    "expiration_date DATE)";

            String doctorSurgeryTable = "CREATE TABLE IF NOT EXISTS DOCTOR_SURGERY (" +
                    "doctor_id INTEGER NOT NULL, " +
                    "surgery_id INTEGER NOT NULL, " +
                    "PRIMARY KEY (doctor_id, surgery_id), " +
                    "FOREIGN KEY (doctor_id) REFERENCES Doctor(id), " +
                    "FOREIGN KEY (surgery_id) REFERENCES Surgery(id))";


            String surgeryEquipmentTable = "CREATE TABLE IF NOT EXISTS SURGERY_EQUIPMENT (" +
                    "surgery_id INTEGER NOT NULL, " +
                    "equipment_id INTEGER NOT NULL, " +
                    "PRIMARY KEY (surgery_id, equipment_id), " +
                    "FOREIGN KEY (equipment_id) REFERENCES Equipment(id), " +
                    "FOREIGN KEY (surgery_id) REFERENCES Surgery(id))";
            
            s.executeUpdate(patientTable);
            s.executeUpdate(doctorTable);
            s.executeUpdate(appointmentTable);
            s.executeUpdate(surgeryTable);
            s.executeUpdate(equipmentTable);
            s.executeUpdate(doctorSurgeryTable);
            s.executeUpdate(surgeryEquipmentTable);

            s.close();

        } catch (Exception e) {
            System.out.println("Database access error");
            e.printStackTrace();
        }
    }
}
