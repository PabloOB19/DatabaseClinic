package Main;

import java.time.LocalDate;
import java.util.List;

import Enums.*;
import JDBC.*;
import POJOS.*;

public class AdminMenu {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private JDBCEquipmentManager equipmentManager;

    public AdminMenu(JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager,
            JDBCEquipmentManager equipmentManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.equipmentManager = equipmentManager;
    }

    public void run() {
        boolean adminRun = true;

        while (adminRun) {
            Utils.ImpresionAdmin();
            int op = InputOutput.askInt("\n Choose an option: ");

            switch (op) {
                case 1:
                    addMenu();
                    break;
                case 2:
                    updateMenu();
                    break;
                case 3:
                    deleteMenu();
                    break;
                case 4:
                    listMenu();
                    break;
                case 6:
                    adminRun = false;
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
    }

    private void addMenu() {
        Utils.ImpresionEntity();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                addPatientByScreen();
                break;
            case 2:
                addDoctorByScreen();
                break;
            case 3:
                addAppointmentByScreen();
                break;
            case 4:
                addSurgeryByScreen();
                break;
            case 5:
                addEquipmentByScreen();
                break;
            default:
                System.out.println("Invalid entity option.");
                break;
        }
    }

    private void updateMenu() {
        Utils.ImpresionEntity();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                updatePatientByScreen();
                break;
            case 2:
                updateDoctorByScreen();
                break;
            case 3:
                updateAppointmentByScreen();
                break;
            case 4:
                updateSurgeryByScreen();
                break;
            case 5:
                updateEquipmentByScreen();
                break;
            default:
                System.out.println("Invalid entity option.");
                break;
        }
    }

    private void deleteMenu() {
        Utils.ImpresionEntity();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                deletePatientByScreen();
                break;
            case 2:
                deleteDoctorByScreen();
                break;
            case 3:
                deleteAppointmentByScreen();
                break;
            case 4:
                deleteSurgeryByScreen();
                break;
            case 5:
                deleteEquipmentByScreen();
                break;
            default:
                System.out.println("Invalid entity option.");
                break;
        }
    }

    private void listMenu() {
        printListMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                listAllPatients();
                break;
            case 2:
                listAllDoctors();
                break;
            case 3:
                listAllAppointments();
                break;
            case 4:
                listAllSurgeries();
                break;
            case 5:
                listAllEquipment();
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
            default:
                System.out.println("Invalid list option.");
                break;
        }
    }

    private void printListMenu() {
        System.out.println("1- List all patients");
        System.out.println("2- List all doctors");
        System.out.println("3- List all appointments");
        System.out.println("4- List all surgeries");
        System.out.println("5- List all equipment");
        System.out.println("6- List doctors by specialty");
        System.out.println("7- List doctors by surgery");
        System.out.println("8- List doctors by appointment");
        System.out.println("9- List equipment by surgery");
        System.out.println("10- List equipment by category");
    }

    private void addDoctorByScreen() {
        String name = InputOutput.askString("Introduce doctor's name:");
        String surname = InputOutput.askString("Introduce doctor's surname:");
        String email = InputOutput.askEmail("Introduce doctor's email:");
        String specialty = InputOutput.askString("Introduce doctor's specialty:");
        LocalDate dob = InputOutput.askDate("Introduce doctor's date of birth:");
        Sex sex = InputOutput.askSex();
        if (sex == null) {
            return;
        }
        byte[] photo = null;
        if (InputOutput.askYesNo("Do you want to upload a photo?")) {
            photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
        }
        Doctor doctor = new Doctor(0, name, surname, photo, sex, email, specialty, dob, null, null);
        doctorManager.insertDoctor(doctor);
        System.out.println("Doctor added successfully with ID: " + doctor.getId());
    }

    private void addPatientByScreen() {
        String name = InputOutput.askString("Introduce patient's name:");
        String surname = InputOutput.askString("Introduce patient's surname:");
        String email = InputOutput.askEmail("Introduce patient's email:");
        LocalDate dob = InputOutput.askDate("Introduce patient's date of birth:");
        int height = InputOutput.askInt("Introduce patient's height:");
        double weightDouble = InputOutput.askDouble("Introduce patient's weight:");
        float weight = (float) weightDouble;
        String info = InputOutput.askString("Introduce patient's info:");
        Sex sex = InputOutput.askSex();
        if (sex == null) {
            return;
        }
        byte[] photo = null;
        if (InputOutput.askYesNo("Do you want to upload a photo?")) {
            photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
        }
        Patient patient = new Patient(0, sex, name, surname, dob, height, weight, photo, info, email, null, null);
        patientManager.insertPatient(patient);
        System.out.println("Patient added successfully with ID: " + patient.getId());
    }

    private void addAppointmentByScreen() {
        Type_of_appointment type = InputOutput.askAppointmentType();
        if (type == null) {
            return;
        }
        LocalDate date = InputOutput.askDate("Introduce appointment date:");
        double price = InputOutput.askDouble("Introduce appointment price:");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        int doctorId = InputOutput.askInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        int patientId = InputOutput.askInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        Appointment appointment = new Appointment(0, type, date, turn, price, doctor, patient);
        appointmentManager.insertAppointment(appointment);
        System.out.println("Appointment added successfully with ID: " + appointment.getId());
    }

    private void addSurgeryByScreen() {
        Type_of_surgery type = InputOutput.askSurgeryType();
        if (type == null) {
            return;
        }
        LocalDate date = InputOutput.askDate("Introduce surgery date:");
        double price = InputOutput.askDouble("Introduce surgery price:");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        int patientId = InputOutput.askInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        Surgery surgery = new Surgery(0, date, type, price, turn, patient, null, null);
        surgeryManager.insertSurgery(surgery);
        System.out.println("Surgery added successfully with ID: " + surgery.getId());
    }

    private void addEquipmentByScreen() {
        String name = InputOutput.askString("Introduce equipment name:");
        Category category = InputOutput.askCategory();
        if (category == null) {
            return;
        }
        int quantity = InputOutput.askInt("Introduce equipment quantity:");
        double price = InputOutput.askDouble("Introduce equipment price:");
        LocalDate expirationDate = InputOutput.askDate("Introduce equipment expiration date:");
        Equipment equipment = new Equipment(0, name, category, quantity, price, expirationDate, null);
        equipmentManager.insertEquipment(equipment);
        System.out.println("Equipment added successfully with ID: " + equipment.getId());
    }

    private void updateDoctorByScreen() {
        int id = InputOutput.askInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        doctor.setName(InputOutput.askString("Introduce new doctor's name:"));
        doctor.setSurname(InputOutput.askString("Introduce new doctor's surname:"));
        doctor.setEmail(InputOutput.askEmail("Introduce new doctor's email:"));
        doctor.setSpecialty(InputOutput.askString("Introduce new doctor's specialty:"));
        doctor.setDob(InputOutput.askDate("Introduce new doctor's date of birth:"));
        Sex sex = InputOutput.askSex();
        if (sex == null) {
            return;
        }
        doctor.setSex(sex);
        if (InputOutput.askYesNo("Do you want to update the photo?")) {
            byte[] photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
            doctor.setPhoto(photo);
        }
        doctorManager.updateDoctor(doctor);
        System.out.println("Doctor updated successfully.");
    }

    private void updatePatientByScreen() {
        int id = InputOutput.askInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        patient.setName(InputOutput.askString("Introduce new patient's name:"));
        patient.setSurname(InputOutput.askString("Introduce new patient's surname:"));
        patient.setEmail(InputOutput.askEmail("Introduce new patient's email:"));
        patient.setDob(InputOutput.askDate("Introduce new patient's date of birth:"));
        patient.setHeight(InputOutput.askInt("Introduce new patient's height:"));

        double weightDouble = InputOutput.askDouble("Introduce new patient's weight:");
        patient.setWeight((float) weightDouble);

        patient.setInfo(InputOutput.askString("Introduce new patient's info:"));

        Sex sex = InputOutput.askSex();
        if (sex == null) {
            return;
        }
        patient.setSex(sex);

        if (InputOutput.askYesNo("Do you want to update the photo?")) {
            byte[] photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
            patient.setPhoto(photo);
        }

        patientManager.updatePatient(patient);
        System.out.println("Patient updated successfully.");
    }

    private void updateAppointmentByScreen() {
        int id = InputOutput.askInt("Introduce appointment ID:");
        Appointment appointment = appointmentManager.getAppointmentById(id);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        Type_of_appointment type = InputOutput.askAppointmentType();
        if (type == null) {
            return;
        }

        appointment.setType(type);
        appointment.setDate(InputOutput.askDate("Introduce new appointment date:"));
        appointment.setPrice(InputOutput.askDouble("Introduce new appointment price:"));

        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        appointment.setTurn(turn);

        int doctorId = InputOutput.askInt("Introduce new doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        int patientId = InputOutput.askInt("Introduce new patient ID:");
        Patient patient = patientManager.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentManager.updateAppointment(appointment);
        System.out.println("Appointment updated successfully.");
    }

    private void updateSurgeryByScreen() {
        int id = InputOutput.askInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(id);

        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        Type_of_surgery type = InputOutput.askSurgeryType();
        if (type == null) {
            return;
        }

        surgery.setType(type);
        surgery.setDate(InputOutput.askDate("Introduce new surgery date:"));
        surgery.setPrice(InputOutput.askDouble("Introduce new surgery price:"));

        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        surgery.setTurn(turn);

        int patientId = InputOutput.askInt("Introduce new patient ID:");
        Patient patient = patientManager.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        surgery.setPatient(patient);

        surgeryManager.updateSurgery(surgery);
        System.out.println("Surgery updated successfully.");
    }

    private void updateEquipmentByScreen() {
        int id = InputOutput.askInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(id);

        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        equipment.setName(InputOutput.askString("Introduce new equipment name:"));

        Category category = InputOutput.askCategory();
        if (category == null) {
            return;
        }
        equipment.setCategory(category);

        equipment.setQuantity(InputOutput.askInt("Introduce new equipment quantity:"));
        equipment.setPrice(InputOutput.askDouble("Introduce new equipment price:"));
        equipment.setExpiration_date(InputOutput.askDate("Introduce new equipment expiration date:"));

        equipmentManager.updateEquipment(equipment);
        System.out.println("Equipment updated successfully.");
    }

    private void deletePatientByScreen() {
        int id = InputOutput.askInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        patientManager.deletePatient(id);
        System.out.println("Patient deleted successfully.");
    }

    private void deleteDoctorByScreen() {
        int id = InputOutput.askInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        doctorManager.deleteDoctor(id);
        System.out.println("Doctor deleted successfully.");
    }

    private void deleteAppointmentByScreen() {
        int id = InputOutput.askInt("Introduce appointment ID:");
        Appointment appointment = appointmentManager.getAppointmentById(id);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        appointmentManager.deleteAppointment(id);
        System.out.println("Appointment deleted successfully.");
    }

    private void deleteSurgeryByScreen() {
        int id = InputOutput.askInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(id);

        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        surgeryManager.deleteSurgery(id);
        System.out.println("Surgery deleted successfully.");
    }

    private void deleteEquipmentByScreen() {
        int id = InputOutput.askInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(id);

        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        equipmentManager.deleteEquipment(id);
        System.out.println("Equipment deleted successfully.");
    }

    private void listAllPatients() {
        printList(patientManager.listAllPatients(), "There are no patients.");
    }

    private void listAllDoctors() {
        printList(doctorManager.listAllDoctors(), "There are no doctors.");
    }

    private void listAllAppointments() {
        printList(appointmentManager.listAllAppointments(), "There are no appointments.");
    }

    private void listAllSurgeries() {
        printList(surgeryManager.listAllSurgeries(), "There are no surgeries.");
    }

    private void listAllEquipment() {
        printList(equipmentManager.listAllEquipments(), "There is no equipment.");
    }

    private void listDoctorsBySpecialty() {
        String specialty = InputOutput.askString("Introduce doctor's specialty:");
        printList(doctorManager.listDoctorsBySpecialty(specialty), "There are no doctors with that specialty.");
    }

    private void listDoctorsBySurgery() {
        int surgeryId = InputOutput.askInt("Introduce surgery ID:");
        printList(doctorManager.listDoctorsBySurgery(surgeryId), "There are no doctors for that surgery.");
    }

    private void listDoctorsByAppointment() {
        int appointmentId = InputOutput.askInt("Introduce appointment ID:");
        printList(doctorManager.listDoctorsByAppointment(appointmentId), "There are no doctors for that appointment.");
    }

    private void listEquipmentBySurgery() {
        int surgeryId = InputOutput.askInt("Introduce surgery ID:");
        printList(equipmentManager.listEquipmentBySurgery(surgeryId), "There is no equipment for that surgery.");
    }

    private void listEquipmentByCategory() {
        Category category = InputOutput.askCategory();
        if (category == null) {
            return;
        }
        printList(equipmentManager.listEquipmentByCategory(category), "There is no equipment in that category.");
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
}
