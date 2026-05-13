package Main;

import java.sql.Connection;
import java.nio.file.Files;
import java.nio.file.Paths;
import Enums.Sex;
import java.util.List;
import java.util.ArrayList;
import JDBC.JDBCConnectionManager;
import JDBC.JDBCDoctorManager;
import POJOS.Appointment;
import POJOS.Doctor;
import POJOS.Surgery;
import Utils.Utils;

public class InputOutput {
	 
   
    public static void main(String[] args) {

        JDBCConnectionManager connectionManager = new JDBCConnectionManager();
        Connection c = connectionManager.manager();

        if (c == null) {
            System.out.println("Could not connect to database.");
            return;
        }

        System.out.println("Database connected and tables created.");

        List<Appointment> appointments = new ArrayList<>();
        List<Surgery> surgeries = new ArrayList<>();

        try {
            byte[] foto = Utils.loadImage("doctor.jpg");

            Doctor doctor = new Doctor(
                1,
                "Ana",
                "García",
                foto,
                Sex.FEMALE,
                "ana@email.com",
                "Cardiología",
                java.time.LocalDate.of(1985, 5, 10),
                appointments,
                surgeries
            );

           
            JDBCDoctorManager doc = new JDBCDoctorManager(c);
            doc.insertDoctor(doctor);

            System.out.println("Doctor guardado con ID: " + doctor.getId());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connectionManager.closeConnection();
        }
    } 
}