package Main;

import java.util.List;

import Enums.Category;
import JDBC.JDBCAppointmentManager;
import JDBC.JDBCDoctorManager;
import JDBC.JDBCEquipmentManager;
import JDBC.JDBCPatientManager;
import JDBC.JDBCSurgeryManager;
import POJOS.Doctor;
import POJOS.Equipment;
import POJOS.Patient;
import POJOS.Surgery;

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
                    addEquipmentToSurgeryByScreen();
                    break;
                case 2:
                    getPatientById();
                    break;
                case 3:
                    listSurgeriesByDoctor();
                    break;
                case 4:
                    listAppointmentsByDoctor();
                    break;
                case 5:
                    getDoctorById();
                    break;
                case 6:
                    listDoctorsBySpecialty();
                    break;
                case 7:
                    listDoctorsBySurgery();
                    break;
                case 8:
                    listDoctorsByAppointment();
                    break;
                case 9:
                    listEquipmentBySurgery();
                    break;
                case 10:
                    listEquipmentByCategory();
                    break;
                case 11:
                    doctorRun = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void addEquipmentToSurgeryByScreen() {
        int equipmentId = InputOutput.askPositiveInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(equipmentId);
        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(surgeryId);
        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        if (isEquipmentAlreadyInSurgery(equipmentId, surgeryId)) {
            System.out.println("This equipment is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addEquipmentToSurgery(equipmentId, surgeryId)) {
            System.out.println("Equipment assigned to surgery successfully.");
        } else {
            System.out.println("Equipment could not be assigned to surgery.");
        }
    }

    private void getPatientById() {
        int id = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);
        printObject(patient, "Patient not found.");
    }

    private void listSurgeriesByDoctor() {
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        printList(surgeryManager.listSurgeriesByDoctor(doctorId), "There are no surgeries for that doctor.");
    }

    private void listAppointmentsByDoctor() {
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        printList(appointmentManager.listAppointmentsByDoctor(doctorId), "There are no appointments for that doctor.");
    }

    private void getDoctorById() {
        int id = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);
        printObject(doctor, "Doctor not found.");
    }

    private void listDoctorsBySpecialty() {
        String specialty = InputOutput.askString("Introduce doctor's specialty:");
        printList(doctorManager.listDoctorsBySpecialty(specialty), "There are no doctors with that specialty.");
    }

    private void listDoctorsBySurgery() {
        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        printList(doctorManager.listDoctorsBySurgery(surgeryId), "There are no doctors for that surgery.");
    }

    private void listDoctorsByAppointment() {
        int appointmentId = InputOutput.askPositiveInt("Introduce appointment ID:");
        printList(doctorManager.listDoctorsByAppointment(appointmentId), "There are no doctors for that appointment.");
    }

    private void listEquipmentBySurgery() {
        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        printList(equipmentManager.listEquipmentBySurgery(surgeryId), "There is no equipment for that surgery.");
    }

    private void listEquipmentByCategory() {
        Category category = InputOutput.askCategory();
        printList(equipmentManager.listEquipmentByCategory(category), "There is no equipment in that category.");
    }

    private boolean isEquipmentAlreadyInSurgery(int equipmentId, int surgeryId) {
        for (Equipment equipment : equipmentManager.listEquipmentBySurgery(surgeryId)) {
            if (equipment.getId() == equipmentId) {
                return true;
            }
        }

        return false;
    }

    private void printList(List<?> list, String emptyMessage) {
        if (list == null || list.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }

        for (Object element : list) {
            System.out.println(element);
        }
    }

    private void printObject(Object object, String notFoundMessage) {
        if (object == null) {
            System.out.println(notFoundMessage);
            return;
        }

        System.out.println(object);
    }
}
