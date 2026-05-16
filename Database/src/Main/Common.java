package Main;

import java.util.ArrayList;
import java.util.List;

import Enums.Category;
import JDBC.*;
import POJOS.*;

public class Common {
    private static final int MAX_ID_ATTEMPTS = 3;
    private static final String ID_LIMIT_MESSAGE = "ID limit exceeded, please go to administration";

    public interface EntityFinder<T> {
        T findById(int id);
    }

    public static <T> T askExistingId(String prompt, EntityFinder<T> finder, String notFoundMessage) {
        int attempts = 0;

        while (attempts < MAX_ID_ATTEMPTS) {
            int id = InputOutput.askPositiveInt(prompt);
            T entity = finder.findById(id);

            if (entity != null) {
                return entity;
            }

            attempts++;
            if (attempts == MAX_ID_ATTEMPTS) {
                System.out.println(ID_LIMIT_MESSAGE);
                return null;
            }

            System.out.println(notFoundMessage);
        }

        return null;
    }

    public static <T> T askOptionalExistingId(String prompt, int currentId, EntityFinder<T> finder,
            String notFoundMessage) {
        int attempts = 0;

        while (attempts < MAX_ID_ATTEMPTS) {
            int id = InputOutput.askOptionalPositiveInt(prompt, currentId);
            T entity = finder.findById(id);

            if (entity != null) {
                return entity;
            }

            attempts++;
            if (attempts == MAX_ID_ATTEMPTS) {
                System.out.println(ID_LIMIT_MESSAGE);
                return null;
            }

            System.out.println(notFoundMessage);
        }

        return null;
    }

    public static void getDoctorById(JDBCDoctorManager doctorManager) {
        Doctor doctor = askExistingId("Enter doctor ID:", doctorManager::getDoctorById, "Doctor not found.");

        if (doctor != null) {
            System.out.println(doctor);
        }
    }

    public static void getPatientById(JDBCPatientManager patientManager) {
        Patient patient = askExistingId("Enter patient ID:", patientManager::getPatientById, "Patient not found.");

        if (patient != null) {
            System.out.println(patient);
        }
    }

    public static void getEquipmentById(JDBCEquipmentManager equipmentManager) {
        Equipment equipment = askExistingId("Enter equipment ID:", equipmentManager::getEquipmentById,
                "Equipment not found.");

        if (equipment != null) {
            System.out.println(equipment);
        }
    }

    public static void getAppointmentById(JDBCAppointmentManager appointmentManager) {
        Appointment appointment = askExistingId("Enter appointment ID:", appointmentManager::getAppointmentById,
                "Appointment not found.");

        if (appointment != null) {
            System.out.println(appointment);
        }
    }

    public static void getSurgeryById(JDBCSurgeryManager surgeryManager) {
        Surgery surgery = askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById, "Surgery not found.");

        if (surgery != null) {
            System.out.println(surgery);
        }
    }

    public static void listDoctorsBySpecialty(JDBCDoctorManager doctorManager) {
        List<String> specialties = new ArrayList<>();

        for (Doctor doctor : doctorManager.listAllDoctors()) {
            String specialty = doctor.getSpecialty();

            if (specialty != null && !specialty.trim().isEmpty() && !containsSpecialty(specialties, specialty)) {
                specialties.add(specialty);
            }
        }

        if (specialties.isEmpty()) {
            System.out.println("There are no specialties available.");
            return;
        }

        System.out.println("Choose specialty:");
        for (int i = 0; i < specialties.size(); i++) {
            System.out.println((i + 1) + "- " + specialties.get(i));
        }

        int option;
        while (true) {
            option = InputOutput.askInt("Choose specialty:");

            if (option >= 1 && option <= specialties.size()) {
                break;
            }

            System.out.println("Invalid specialty option.");
        }

        String specialty = specialties.get(option - 1);
        Utils.printList(doctorManager.listDoctorsBySpecialty(specialty), "There are no doctors with that specialty.");
    }

    private static boolean containsSpecialty(List<String> specialties, String specialty) {
        for (String existingSpecialty : specialties) {
            if (existingSpecialty.equalsIgnoreCase(specialty)) {
                return true;
            }
        }

        return false;
    }

    public static void listDoctorsBySurgery(JDBCDoctorManager doctorManager) {
        int surgeryId = InputOutput.askPositiveInt("Enter surgery ID:");
        Utils.printList(doctorManager.listDoctorsBySurgery(surgeryId), "There are no doctors for that surgery.");
    }

    public static void listSurgeriesByDoctor(JDBCSurgeryManager surgeryManager) {
        int doctorId = InputOutput.askPositiveInt("Enter doctor ID:");
        Utils.printList(surgeryManager.listSurgeriesByDoctor(doctorId), "There are no surgeries for that doctor.");
    }

    public static void listDoctorsByAppointment(JDBCDoctorManager doctorManager) {
        int appointmentId = InputOutput.askPositiveInt("Enter appointment ID:");
        Utils.printList(doctorManager.listDoctorsByAppointment(appointmentId), "There are no doctors for that appointment.");
    }

    public static void listAppointmentsByDoctor(JDBCAppointmentManager appointmentManager) {
        int doctorId = InputOutput.askPositiveInt("Enter doctor ID:");
        Utils.printList(appointmentManager.listAppointmentsByDoctor(doctorId), "There are no appointments for that doctor.");
    }

    public static void listEquipmentBySurgery(JDBCEquipmentManager equipmentManager) {
        int surgeryId = InputOutput.askPositiveInt("Enter surgery ID:");
        Utils.printList(equipmentManager.listEquipmentBySurgery(surgeryId), "There is no equipment for that surgery.");
    }

    public static void listEquipmentByCategory(JDBCEquipmentManager equipmentManager) {
        Category category = InputOutput.askCategory();
        Utils.printList(equipmentManager.listEquipmentByCategory(category), "There is no equipment in that category.");
    }

    public static void addEquipmentToSurgeryByScreen(JDBCEquipmentManager equipmentManager,
            JDBCSurgeryManager surgeryManager) {
        Equipment equipment = askExistingId("Enter equipment ID:", equipmentManager::getEquipmentById,
                "Equipment not found.");
        if (equipment == null) {
            return;
        }

        Surgery surgery = askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById, "Surgery not found.");
        if (surgery == null) {
            return;
        }

        if (isEquipmentAlreadyInSurgery(equipmentManager, equipment.getId(), surgery.getId())) {
            System.out.println("This equipment is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addEquipmentToSurgery(equipment.getId(), surgery.getId())) {
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
