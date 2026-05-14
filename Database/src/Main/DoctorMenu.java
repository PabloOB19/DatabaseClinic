package Main;

import JDBC.JDBCAppointmentManager;
import JDBC.JDBCDoctorManager;
import JDBC.JDBCEquipmentManager;
import JDBC.JDBCPatientManager;
import JDBC.JDBCSurgeryManager;
import POJOS.Doctor;

public class DoctorMenu {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private JDBCEquipmentManager equipmentManager;

    public DoctorMenu(JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager,
            JDBCEquipmentManager equipmentManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.equipmentManager = equipmentManager;
    }

    public void run() {
        boolean doctorRun = true;

        while (doctorRun) {
            Utils.printDoctorMenu();
            int op = InputOutput.askInt("\n Choose an option: ");

            switch (op) {
                case 1:
                    addMenu();
                    break;
                case 2:
                    listMenu();
                    break;
                case 3:
                    getMenu();
                    break;
                case 4:
                    doctorRun = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void addMenu() {
        Utils.printDoctorAddMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                Common.addEquipmentToSurgeryByScreen(equipmentManager, surgeryManager);
                break;
            default:
                System.out.println("Invalid add option.");
                break;
        }
    }

    private void listMenu() {
        Utils.printDoctorListMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                listSurgeriesByDoctor();
                break;
            case 2:
                listAppointmentsByDoctor();
                break;
            case 3:
                Common.listDoctorsBySpecialty(doctorManager);
                break;
            case 4:
                Common.listDoctorsBySurgery(doctorManager);
                break;
            case 5:
                Common.listDoctorsByAppointment(doctorManager);
                break;
            case 6:
                Common.listEquipmentBySurgery(equipmentManager);
                break;
            case 7:
                Common.listEquipmentByCategory(equipmentManager);
                break;
            default:
                System.out.println("Invalid list option.");
                break;
        }
    }

    private void getMenu() {
        Utils.printGetMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                Common.getPatientById(patientManager);
                break;
            case 2:
                Common.getDoctorById(doctorManager);
                break;
            case 3: 
            	Common.getEquipmentById(equipmentManager);
            	break;
            default:
                System.out.println("Invalid get option.");
                break;
        }
    }

    private void listSurgeriesByDoctor() {
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        Utils.printList(surgeryManager.listSurgeriesByDoctor(doctorId), "There are no surgeries for that doctor.");
    }

    private void listAppointmentsByDoctor() {
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        Utils.printList(appointmentManager.listAppointmentsByDoctor(doctorId), "There are no appointments for that doctor.");
    }

}
