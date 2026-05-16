package Main;

import Enums.Category;
import JDBC.*;
import POJOS.*;

public class Common {
    private static final int MAX_ID_ATTEMPTS = 3;
    private static final String ID_LIMIT_MESSAGE = "ID limit exceeded, please go to administration";

    public static void getDoctorById(JDBCDoctorManager doctorManager) {
        Doctor doctor = null;
        int attempts = 0;
        while (doctor == null) {
            int id = InputOutput.askPositiveInt("Introduce doctor ID:");
            doctor = doctorManager.getDoctorById(id);

            if (doctor == null) {
                attempts++;
                if (attempts == MAX_ID_ATTEMPTS) {
                    System.out.println(ID_LIMIT_MESSAGE);
                    return;
                }
                System.out.println("Doctor not found.");
            }
        }

        System.out.println(doctor);
    }

    public static void getPatientById(JDBCPatientManager patientManager) {
        Patient patient = null;
        int attempts = 0;
        while (patient == null) {
            int id = InputOutput.askPositiveInt("Introduce patient ID:");
            patient = patientManager.getPatientById(id);

            if (patient == null) {
                attempts++;
                if (attempts == MAX_ID_ATTEMPTS) {
                    System.out.println(ID_LIMIT_MESSAGE);
                    return;
                }
                System.out.println("Patient not found.");
            }
        }

        System.out.println(patient);
    }

    public static void getEquipmentById(JDBCEquipmentManager equipmentManager) {
        Equipment equipment = null;
        int attempts = 0;
        while (equipment == null) {
            int id = InputOutput.askPositiveInt("Introduce equipment ID:");
            equipment = equipmentManager.getEquipmentById(id);

            if (equipment == null) {
                attempts++;
                if (attempts == MAX_ID_ATTEMPTS) {
                    System.out.println(ID_LIMIT_MESSAGE);
                    return;
                }
                System.out.println("Equipment not found.");
            }
        }

        System.out.println(equipment);
    }

    public static void listDoctorsBySpecialty(JDBCDoctorManager doctorManager) {
        String specialty = InputOutput.askString("Introduce doctor's specialty:");
        Utils.printList(doctorManager.listDoctorsBySpecialty(specialty), "There are no doctors with that specialty.");
    }

    public static void listDoctorsBySurgery(JDBCDoctorManager doctorManager) {
        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        Utils.printList(doctorManager.listDoctorsBySurgery(surgeryId), "There are no doctors for that surgery.");
    }

    public static void listDoctorsByAppointment(JDBCDoctorManager doctorManager) {
        int appointmentId = InputOutput.askPositiveInt("Introduce appointment ID:");
        Utils.printList(doctorManager.listDoctorsByAppointment(appointmentId), "There are no doctors for that appointment.");
    }

    public static void listEquipmentBySurgery(JDBCEquipmentManager equipmentManager) {
        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        Utils.printList(equipmentManager.listEquipmentBySurgery(surgeryId), "There is no equipment for that surgery.");
    }

    public static void listEquipmentByCategory(JDBCEquipmentManager equipmentManager) {
        Category category = InputOutput.askCategory();
        Utils.printList(equipmentManager.listEquipmentByCategory(category), "There is no equipment in that category.");
    }

    public static void addEquipmentToSurgeryByScreen(JDBCEquipmentManager equipmentManager,
            JDBCSurgeryManager surgeryManager) {
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

        if (isEquipmentAlreadyInSurgery(equipmentManager, equipmentId, surgeryId)) {
            System.out.println("This equipment is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addEquipmentToSurgery(equipmentId, surgeryId)) {
            System.out.println("Equipment assigned to surgery successfully.");
        } else {
            System.out.println("Equipment could not be assigned to surgery.");
        }
    }

    public static boolean isDoctorAlreadyInSurgery(JDBCDoctorManager doctorManager, int doctorId, int surgeryId) {
        for (Doctor doctor : doctorManager.listDoctorsBySurgery(surgeryId)) {
            if (doctor.getId() == doctorId) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEquipmentAlreadyInSurgery(JDBCEquipmentManager equipmentManager, int equipmentId,
            int surgeryId) {
        for (Equipment equipment : equipmentManager.listEquipmentBySurgery(surgeryId)) {
            if (equipment.getId() == equipmentId) {
                return true;
            }
        }

        return false;
    }
}
