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
                case 5:
                    getMenu();
                    break;
                case 6:
                    assignMenu();
                    break;
                case 7:
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
        Utils.printListMenu();
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

    private void getMenu() {
        Utils.printGetMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                getDoctorById();
                break;
            case 2:
                getPatientById();
                break;
            case 3:
                getEquipmentById();
                break;
            default:
                System.out.println("Invalid get option.");
                break;
        }
    }

    private void assignMenu() {
        Utils.printAssignMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                addDoctorToSurgeryByScreen();
                break;
            case 2:
                addEquipmentToSurgeryByScreen();
                break;
            default:
                System.out.println("Invalid assign option.");
                break;
        }
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
        if (doctorManager.insertDoctor(doctor)) {
            System.out.println("Doctor added successfully with ID: " + doctor.getId());
        } else {
            System.out.println("Doctor could not be added.");
        }
    }

    private void addPatientByScreen() {
        String name = InputOutput.askString("Introduce patient's name:");
        String surname = InputOutput.askString("Introduce patient's surname:");
        String email = InputOutput.askEmail("Introduce patient's email:");
        LocalDate dob = InputOutput.askDate("Introduce patient's date of birth:");
        int height = InputOutput.askPositiveInt("Introduce patient's height:");
        double weightDouble = InputOutput.askPositiveDouble("Introduce patient's weight:");
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
        if (patientManager.insertPatient(patient)) {
            System.out.println("Patient added successfully with ID: " + patient.getId());
        } else {
            System.out.println("Patient could not be added.");
        }
    }

    private void addAppointmentByScreen() {
        Type_of_appointment type = InputOutput.askAppointmentType();
        if (type == null) {
            return;
        }
        LocalDate date = InputOutput.askDate("Introduce appointment date:");
        double price = InputOutput.askPositiveDouble("Introduce appointment price:");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        int patientId = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(patientId);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        Appointment appointment = new Appointment(0, type, date, turn, price, doctor, patient);
        if (appointmentManager.insertAppointment(appointment)) {
            System.out.println("Appointment added successfully with ID: " + appointment.getId());
        } else {
            System.out.println("Appointment could not be added.");
        }
    }

    private void addSurgeryByScreen() {
        Type_of_surgery type = InputOutput.askSurgeryType();
        if (type == null) {
            return;
        }
        LocalDate date = InputOutput.askDate("Introduce surgery date:");
        double price = InputOutput.askPositiveDouble("Introduce surgery price:");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        int patientId = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(patientId);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        Surgery surgery = new Surgery(0, date, type, price, turn, patient, null, null);
        if (surgeryManager.insertSurgery(surgery)) {
            System.out.println("Surgery added successfully with ID: " + surgery.getId());
        } else {
            System.out.println("Surgery could not be added.");
        }
    }

    private void addEquipmentByScreen() {
        String name = InputOutput.askString("Introduce equipment name:");
        Category category = InputOutput.askCategory();
        if (category == null) {
            return;
        }
        int quantity = InputOutput.askPositiveInt("Introduce equipment quantity:");
        double price = InputOutput.askPositiveDouble("Introduce equipment price:");
        LocalDate expirationDate = InputOutput.askDate("Introduce equipment expiration date:");
        Equipment equipment = new Equipment(0, name, category, quantity, price, expirationDate, null);
        if (equipmentManager.insertEquipment(equipment)) {
            System.out.println("Equipment added successfully with ID: " + equipment.getId());
        } else {
            System.out.println("Equipment could not be added.");
        }
    }

    private void updateDoctorByScreen() {
        int id = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }
        doctor.setName(InputOutput.askOptionalString("Introduce new doctor's name:", doctor.getName()));
        doctor.setSurname(InputOutput.askOptionalString("Introduce new doctor's surname:", doctor.getSurname()));
        doctor.setEmail(InputOutput.askOptionalEmail("Introduce new doctor's email:", doctor.getEmail()));
        doctor.setSpecialty(InputOutput.askOptionalString("Introduce new doctor's specialty:", doctor.getSpecialty()));
        doctor.setDob(InputOutput.askOptionalDate("Introduce new doctor's date of birth:", doctor.getDob()));
        if (InputOutput.askYesNo("Do you want to update the sex?")) {
            doctor.setSex(InputOutput.askSex());
        }
        if (InputOutput.askYesNo("Do you want to update the photo?")) {
            byte[] photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
            doctor.setPhoto(photo);
        }
        if (doctorManager.updateDoctor(doctor)) {
            System.out.println("Doctor updated successfully.");
        } else {
            System.out.println("Doctor could not be updated.");
        }
    }

    private void updatePatientByScreen() {
        int id = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        patient.setName(InputOutput.askOptionalString("Introduce new patient's name:", patient.getName()));
        patient.setSurname(InputOutput.askOptionalString("Introduce new patient's surname:", patient.getSurname()));
        patient.setEmail(InputOutput.askOptionalEmail("Introduce new patient's email:", patient.getEmail()));
        patient.setDob(InputOutput.askOptionalDate("Introduce new patient's date of birth:", patient.getDob()));
        patient.setHeight(InputOutput.askOptionalPositiveInt("Introduce new patient's height:", patient.getHeight()));

        double weightDouble = InputOutput.askOptionalPositiveDouble("Introduce new patient's weight:", patient.getWeight());
        patient.setWeight((float) weightDouble);

        patient.setInfo(InputOutput.askOptionalString("Introduce new patient's info:", patient.getInfo()));

        if (InputOutput.askYesNo("Do you want to update the sex?")) {
            patient.setSex(InputOutput.askSex());
        }

        if (InputOutput.askYesNo("Do you want to update the photo?")) {
            byte[] photo = InputOutput.askPhoto();
            if (photo == null) {
                return;
            }
            patient.setPhoto(photo);
        }

        if (patientManager.updatePatient(patient)) {
            System.out.println("Patient updated successfully.");
        } else {
            System.out.println("Patient could not be updated.");
        }
    }

    private void updateAppointmentByScreen() {
        int id = InputOutput.askPositiveInt("Introduce appointment ID:");
        Appointment appointment = appointmentManager.getAppointmentById(id);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (InputOutput.askYesNo("Do you want to update the appointment type?")) {
            appointment.setType(InputOutput.askAppointmentType());
        }

        appointment.setDate(InputOutput.askOptionalDate("Introduce new appointment date:", appointment.getDate()));
        appointment.setPrice(InputOutput.askOptionalPositiveDouble("Introduce new appointment price:", appointment.getPrice()));

        if (InputOutput.askYesNo("Do you want to update the turn?")) {
            appointment.setTurn(InputOutput.askTurn());
        }

        if (InputOutput.askYesNo("Do you want to update the doctor?")) {
            int doctorId = InputOutput.askPositiveInt("Introduce new doctor ID:");
            Doctor doctor = doctorManager.getDoctorById(doctorId);

            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }
            appointment.setDoctor(doctor);
        }

        if (InputOutput.askYesNo("Do you want to update the patient?")) {
            int patientId = InputOutput.askPositiveInt("Introduce new patient ID:");
            Patient patient = patientManager.getPatientById(patientId);

            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }
            appointment.setPatient(patient);
        }

        if (appointmentManager.updateAppointment(appointment)) {
            System.out.println("Appointment updated successfully.");
        } else {
            System.out.println("Appointment could not be updated.");
        }
    }

    private void updateSurgeryByScreen() {
        int id = InputOutput.askPositiveInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(id);

        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        if (InputOutput.askYesNo("Do you want to update the surgery type?")) {
            surgery.setType(InputOutput.askSurgeryType());
        }

        surgery.setDate(InputOutput.askOptionalDate("Introduce new surgery date:", surgery.getDate()));
        surgery.setPrice(InputOutput.askOptionalPositiveDouble("Introduce new surgery price:", surgery.getPrice()));

        if (InputOutput.askYesNo("Do you want to update the turn?")) {
            surgery.setTurn(InputOutput.askTurn());
        }

        if (InputOutput.askYesNo("Do you want to update the patient?")) {
            int patientId = InputOutput.askPositiveInt("Introduce new patient ID:");
            Patient patient = patientManager.getPatientById(patientId);

            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }
            surgery.setPatient(patient);
        }

        if (surgeryManager.updateSurgery(surgery)) {
            System.out.println("Surgery updated successfully.");
        } else {
            System.out.println("Surgery could not be updated.");
        }
    }

    private void updateEquipmentByScreen() {
        int id = InputOutput.askPositiveInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(id);

        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        equipment.setName(InputOutput.askOptionalString("Introduce new equipment name:", equipment.getName()));

        if (InputOutput.askYesNo("Do you want to update the category?")) {
            equipment.setCategory(InputOutput.askCategory());
        }

        equipment.setQuantity(InputOutput.askOptionalPositiveInt("Introduce new equipment quantity:", equipment.getQuantity()));
        equipment.setPrice(InputOutput.askOptionalPositiveDouble("Introduce new equipment price:", equipment.getPrice()));
        equipment.setExpiration_date(InputOutput.askOptionalDate("Introduce new equipment expiration date:", equipment.getExpiration_date()));

        if (equipmentManager.updateEquipment(equipment)) {
            System.out.println("Equipment updated successfully.");
        } else {
            System.out.println("Equipment could not be updated.");
        }
    }

    private void deletePatientByScreen() {
        int id = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);

        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this patient?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (patientManager.deletePatient(id)) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient could not be deleted.");
        }
    }

    private void deleteDoctorByScreen() {
        int id = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);

        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this doctor?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (doctorManager.deleteDoctor(id)) {
            System.out.println("Doctor deleted successfully.");
        } else {
            System.out.println("Doctor could not be deleted.");
        }
    }

    private void deleteAppointmentByScreen() {
        int id = InputOutput.askPositiveInt("Introduce appointment ID:");
        Appointment appointment = appointmentManager.getAppointmentById(id);

        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this appointment?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (appointmentManager.deleteAppointment(id)) {
            System.out.println("Appointment deleted successfully.");
        } else {
            System.out.println("Appointment could not be deleted.");
        }
    }

    private void deleteSurgeryByScreen() {
        int id = InputOutput.askPositiveInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(id);

        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this surgery?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (surgeryManager.deleteSurgery(id)) {
            System.out.println("Surgery deleted successfully.");
        } else {
            System.out.println("Surgery could not be deleted.");
        }
    }

    private void deleteEquipmentByScreen() {
        int id = InputOutput.askPositiveInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(id);

        if (equipment == null) {
            System.out.println("Equipment not found.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this equipment?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (equipmentManager.deleteEquipment(id)) {
            System.out.println("Equipment deleted successfully.");
        } else {
            System.out.println("Equipment could not be deleted.");
        }
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
        if (category == null) {
            return;
        }
        printList(equipmentManager.listEquipmentByCategory(category), "There is no equipment in that category.");
    }

    private void getDoctorById() {
        int id = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(id);
        printObject(doctor, "Doctor not found.");
    }

    private void getPatientById() {
        int id = InputOutput.askPositiveInt("Introduce patient ID:");
        Patient patient = patientManager.getPatientById(id);
        printObject(patient, "Patient not found.");
    }

    private void getEquipmentById() {
        int id = InputOutput.askPositiveInt("Introduce equipment ID:");
        Equipment equipment = equipmentManager.getEquipmentById(id);
        printObject(equipment, "Equipment not found.");
    }

    private void addDoctorToSurgeryByScreen() {
        int doctorId = InputOutput.askPositiveInt("Introduce doctor ID:");
        Doctor doctor = doctorManager.getDoctorById(doctorId);
        if (doctor == null) {
            System.out.println("Doctor not found.");
            return;
        }

        int surgeryId = InputOutput.askPositiveInt("Introduce surgery ID:");
        Surgery surgery = surgeryManager.getSurgeryById(surgeryId);
        if (surgery == null) {
            System.out.println("Surgery not found.");
            return;
        }

        if (isDoctorAlreadyInSurgery(doctorId, surgeryId)) {
            System.out.println("This doctor is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addDoctorToSurgery(doctorId, surgeryId)) {
            System.out.println("Doctor assigned to surgery successfully.");
        } else {
            System.out.println("Doctor could not be assigned to surgery.");
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

    private boolean isDoctorAlreadyInSurgery(int doctorId, int surgeryId) {
        for (Doctor doctor : doctorManager.listDoctorsBySurgery(surgeryId)) {
            if (doctor.getId() == doctorId) {
                return true;
            }
        }

        return false;
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
