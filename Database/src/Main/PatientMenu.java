package Main;

import JDBC.JDBCAppointmentManager;
import JDBC.JDBCDoctorManager;
import JDBC.JDBCPatientManager;
import JDBC.JDBCSurgeryManager;
import POJOS.Appointment;
import POJOS.Patient;
import POJOS.Surgery;
import POJOS.User;

public class PatientMenu {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private Patient currentPatient;

    public PatientMenu(User loggedUser, JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.currentPatient = findPatientByUserEmail(loggedUser);
    }

    public void run() {
        if (currentPatient == null) {
            System.out.println("No patient profile found for this user email.");
            return;
        }

        boolean patientRun = true;

        while (patientRun) {
            Utils.printPatientMenu();
            int op = InputOutput.askInt("\n Choose an option: ");

            switch (op) {
                case 1:
                    listMenu();
                    break;
                case 2:
                    getMenu();
                    break;
                case 3:
                    patientRun = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void listMenu() {
        Utils.printPatientListMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                listSurgeriesByCurrentPatient();
                break;
            case 2:
                listAppointmentsByCurrentPatient();
                break;
            case 3:
                Common.listDoctorsBySpecialty(doctorManager);
                break;
            default:
                System.out.println("Invalid list option.");
                break;
        }
    }

    private void getMenu() {
        Utils.printPatientGetMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                getSurgeryById();
                break;
            case 2:
                getAppointmentById();
                break;
            case 3:
                getCurrentPatientById();
                break;
            default:
                System.out.println("Invalid get option.");
                break;
        }
    }

    private Patient findPatientByUserEmail(User loggedUser) {
        if (loggedUser == null || loggedUser.getEmail() == null) {
            return null;
        }

        for (Patient patient : patientManager.listAllPatients()) {
            if (patient.getEmail() != null && patient.getEmail().equalsIgnoreCase(loggedUser.getEmail())) {
                return patient;
            }
        }

        return null;
    }

    private void getSurgeryById() {
        Surgery surgery = null;
        int attempts = 0;
        while (surgery == null) {
            int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
            surgery = surgeryManager.getSurgeryById(surgeryId);

            if (surgery == null) {
                attempts++;
                if (attempts == 3) {
                    System.out.println("Límite de IDs superado, pase por administracion");
                    return;
                }
                System.out.println("Surgery not found.");
            }
        }

        if (surgery.getPatient() == null || surgery.getPatient().getId() != currentPatient.getId()) {
            System.out.println("You cannot see this surgery.");
            return;
        }

        System.out.println(surgery);
    }

    private void getAppointmentById() {
        Appointment appointment = null;
        int attempts = 0;
        while (appointment == null) {
            int appointmentId = InputOutput.askPositiveInt("Introduce appointment ID:");
            appointment = appointmentManager.getAppointmentById(appointmentId);

            if (appointment == null) {
                attempts++;
                if (attempts == 3) {
                    System.out.println("Límite de IDs superado, pase por administracion");
                    return;
                }
                System.out.println("Appointment not found.");
            }
        }

        if (appointment.getPatient() == null || appointment.getPatient().getId() != currentPatient.getId()) {
            System.out.println("You cannot see this appointment.");
            return;
        }

        System.out.println(appointment);
    }

    private void listSurgeriesByCurrentPatient() {
        Utils.printList(surgeryManager.listSurgeriesByPatient(currentPatient.getId()), "You have no surgeries.");
    }

    private void listAppointmentsByCurrentPatient() {
        Utils.printList(appointmentManager.listAppointmentsByPatient(currentPatient.getId()), "You have no appointments.");
    }

    private void getCurrentPatientById() {
        Patient patient = patientManager.getPatientById(currentPatient.getId());
        Utils.printObject(patient, "Patient not found.");
    }

}
