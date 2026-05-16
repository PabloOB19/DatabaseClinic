package Main;

import java.time.LocalDate;

import Enums.*;
import JDBC.*;
import POJOS.*;
import ifaces.UserManager;
import ifaces.XmlManager;

public class AdminMenu {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private JDBCEquipmentManager equipmentManager;
    private UserManager userManager;
    private User currentAdmin;
    private XmlAdmin xmlAdmin;

    public AdminMenu(JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager,
            JDBCEquipmentManager equipmentManager, UserManager userManager, XmlManager xmlManager, User currentAdmin) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.equipmentManager = equipmentManager;
        this.userManager = userManager;
        this.currentAdmin = currentAdmin;
        this.xmlAdmin = new XmlAdmin(doctorManager, patientManager, appointmentManager, surgeryManager,
                equipmentManager, userManager, xmlManager);
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
                    xmlAdmin.run();
                    break;
                case 8:
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
        System.out.println("6- User");
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
            case 6:
                deleteUserByScreen();
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
                Common.listDoctorsBySpecialty(doctorManager);
                break;
            case 7:
                Common.listSurgeriesByDoctor(surgeryManager);
                break;
            case 8:
                Common.listAppointmentsByDoctor(appointmentManager);
                break;
            case 9:
                Common.listEquipmentBySurgery(equipmentManager);
                break;
            case 10:
                Common.listEquipmentByCategory(equipmentManager);
                break;
            case 11:
                listAllUsers();
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
            case 4:
                Common.getAppointmentById(appointmentManager);
                break;
            case 5:
                Common.getSurgeryById(surgeryManager);
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
                Common.addEquipmentToSurgeryByScreen(equipmentManager, surgeryManager);
                break;
            default:
                System.out.println("Invalid assign option.");
                break;
        }
    }


    private void addDoctorByScreen() {
        String name = InputOutput.askString("Enter doctor's name:");
        String surname = InputOutput.askString("Enter doctor's surname:");
        String email = InputOutput.askEmail("Enter doctor's email:");
        String specialty = InputOutput.askString("Enter doctor's specialty:");
        LocalDate dob = InputOutput.askPastDate("Enter doctor's date of birth:");
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
        String name = InputOutput.askString("Enter patient's name:");
        String surname = InputOutput.askString("Enter patient's surname:");
        String email = InputOutput.askEmail("Enter patient's email:");
        LocalDate dob = InputOutput.askPastDate("Enter patient's date of birth:");
        int height = InputOutput.askPositiveInt("Enter patient's height (cm):");
        double weightDouble = InputOutput.askPositiveDouble("Enter patient's weight (Kg):");
        float weight = (float) weightDouble;
        String info = InputOutput.askText("Enter any relevant info about the patient:");
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
        LocalDate date = InputOutput.askFutureDate("Enter appointment date:");
        double price = InputOutput.askPositiveDouble("Enter appointment price (€):");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        Doctor doctor = Common.askExistingId("Enter doctor ID:", doctorManager::getDoctorById,
                "Doctor not found.");
        if (doctor == null) {
            return;
        }

        Patient patient = Common.askExistingId("Enter patient ID:", patientManager::getPatientById,
                "Patient not found.");
        if (patient == null) {
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
        LocalDate date = InputOutput.askFutureDate("Enter surgery date:");
        double price = InputOutput.askPositiveDouble("Enter surgery price (€):");
        Turn turn = InputOutput.askTurn();
        if (turn == null) {
            return;
        }
        Patient patient = Common.askExistingId("Enter patient ID:", patientManager::getPatientById,
                "Patient not found.");
        if (patient == null) {
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
        String name = InputOutput.askText("Enter equipment name:");
        Category category = InputOutput.askCategory();
        if (category == null) {
            return;
        }
        int quantity = InputOutput.askPositiveInt("Enter equipment quantity:");
        double price = InputOutput.askPositiveDouble("Enter equipment price (€):");
        LocalDate expirationDate = InputOutput.askFutureDate("Enter equipment expiration date:");
        Equipment equipment = new Equipment(0, name, category, quantity, price, expirationDate, null);
        if (equipmentManager.insertEquipment(equipment)) {
            System.out.println("Equipment added successfully with ID: " + equipment.getId());
        } else {
            System.out.println("Equipment could not be added.");
        }
    }

    private void updateDoctorByScreen() {
        Doctor doctor = Common.askExistingId("Enter doctor ID:", doctorManager::getDoctorById,
                "Doctor not found.");
        if (doctor == null) {
            return;
        }
        doctor.setName(InputOutput.askOptionalString("Enter new doctor's name:", doctor.getName()));
        doctor.setSurname(InputOutput.askOptionalString("Enter new doctor's surname:", doctor.getSurname()));
        doctor.setEmail(InputOutput.askOptionalEmail("Enter new doctor's email:", doctor.getEmail()));
        doctor.setSpecialty(InputOutput.askOptionalString("Enter new doctor's specialty:", doctor.getSpecialty()));
        doctor.setDob(InputOutput.askOptionalPastDate("Enter new doctor's date of birth:", doctor.getDob()));
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
        Patient patient = Common.askExistingId("Enter patient ID:", patientManager::getPatientById,
                "Patient not found.");

        if (patient == null) {
            return;
        }

        patient.setName(InputOutput.askOptionalString("Enter new patient's name:", patient.getName()));
        patient.setSurname(InputOutput.askOptionalString("Enter new patient's surname:", patient.getSurname()));
        patient.setEmail(InputOutput.askOptionalEmail("Enter new patient's email:", patient.getEmail()));
        patient.setDob(InputOutput.askOptionalPastDate("Enter new patient's date of birth:", patient.getDob()));
        patient.setHeight(InputOutput.askOptionalPositiveInt("Enter new patient's height:", patient.getHeight()));

        double weightDouble = InputOutput.askOptionalPositiveDouble("Enter new patient's weight:", patient.getWeight());
        patient.setWeight((float) weightDouble);

        patient.setInfo(InputOutput.askOptionalText("Enter new patient's info:", patient.getInfo()));

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
        Appointment appointment = Common.askExistingId("Enter appointment ID:",
                appointmentManager::getAppointmentById, "Appointment not found.");

        if (appointment == null) {
            return;
        }

        if (InputOutput.askYesNo("Do you want to update the appointment type?")) {
            appointment.setType(InputOutput.askAppointmentType());
        }

        appointment.setDate(InputOutput.askOptionalFutureDate("Enter new appointment date:", appointment.getDate()));
        appointment.setPrice(InputOutput.askOptionalPositiveDouble("Enter new appointment price:", appointment.getPrice()));

        if (InputOutput.askYesNo("Do you want to update the turn?")) {
            appointment.setTurn(InputOutput.askTurn());
        }

        if (InputOutput.askYesNo("Do you want to update the doctor?")) {
            Doctor doctor = Common.askOptionalExistingId("Enter new doctor ID:", appointment.getDoctor().getId(),
                    doctorManager::getDoctorById, "Doctor not found.");
            if (doctor == null) {
                return;
            }
            appointment.setDoctor(doctor);
        }

        if (InputOutput.askYesNo("Do you want to update the patient?")) {
            Patient patient = Common.askOptionalExistingId("Enter new patient ID:",
                    appointment.getPatient().getId(), patientManager::getPatientById, "Patient not found.");
            if (patient == null) {
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
        Surgery surgery = Common.askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById,
                "Surgery not found.");

        if (surgery == null) {
            return;
        }

        if (InputOutput.askYesNo("Do you want to update the surgery type?")) {
            surgery.setType(InputOutput.askSurgeryType());
        }

        surgery.setDate(InputOutput.askOptionalFutureDate("Enter new surgery date:", surgery.getDate()));
        surgery.setPrice(InputOutput.askOptionalPositiveDouble("Enter new surgery price:", surgery.getPrice()));

        if (InputOutput.askYesNo("Do you want to update the turn?")) {
            surgery.setTurn(InputOutput.askTurn());
        }

        if (InputOutput.askYesNo("Do you want to update the patient?")) {
            Patient patient = Common.askOptionalExistingId("Enter new patient ID:", surgery.getPatient().getId(),
                    patientManager::getPatientById, "Patient not found.");
            if (patient == null) {
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
        Equipment equipment = Common.askExistingId("Enter equipment ID:", equipmentManager::getEquipmentById,
                "Equipment not found.");

        if (equipment == null) {
            return;
        }

        equipment.setName(InputOutput.askOptionalText("Enter new equipment name:", equipment.getName()));

        if (InputOutput.askYesNo("Do you want to update the category?")) {
            equipment.setCategory(InputOutput.askCategory());
        }

        equipment.setQuantity(InputOutput.askOptionalPositiveInt("Enter new equipment quantity:", equipment.getQuantity()));
        equipment.setPrice(InputOutput.askOptionalPositiveDouble("Enter new equipment price:", equipment.getPrice()));
        equipment.setExpiration_date(InputOutput.askOptionalFutureDate("Enter new equipment expiration date:", equipment.getExpiration_date()));

        if (equipmentManager.updateEquipment(equipment)) {
            System.out.println("Equipment updated successfully.");
        } else {
            System.out.println("Equipment could not be updated.");
        }
    }

    private void deletePatientByScreen() {
        Patient patient = Common.askExistingId("Enter patient ID:", patientManager::getPatientById,
                "Patient not found.");

        if (patient == null) {
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this patient?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (patientManager.deletePatient(patient.getId())) {
            System.out.println("Patient deleted successfully.");
        } else {
            System.out.println("Patient could not be deleted.");
        }
    }

    private void deleteDoctorByScreen() {
        Doctor doctor = Common.askExistingId("Enter doctor ID:", doctorManager::getDoctorById,
                "Doctor not found.");

        if (doctor == null) {
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this doctor?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (doctorManager.deleteDoctor(doctor.getId())) {
            System.out.println("Doctor deleted successfully.");
        } else {
            System.out.println("Doctor could not be deleted.");
        }
    }

    private void deleteAppointmentByScreen() {
        Appointment appointment = Common.askExistingId("Enter appointment ID:",
                appointmentManager::getAppointmentById, "Appointment not found.");

        if (appointment == null) {
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this appointment?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (appointmentManager.deleteAppointment(appointment.getId())) {
            System.out.println("Appointment deleted successfully.");
        } else {
            System.out.println("Appointment could not be deleted.");
        }
    }

    private void deleteSurgeryByScreen() {
        Surgery surgery = Common.askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById,
                "Surgery not found.");

        if (surgery == null) {
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this surgery?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (surgeryManager.deleteSurgery(surgery.getId())) {
            System.out.println("Surgery deleted successfully.");
        } else {
            System.out.println("Surgery could not be deleted.");
        }
    }

    private void deleteEquipmentByScreen() {
        Equipment equipment = Common.askExistingId("Enter equipment ID:", equipmentManager::getEquipmentById,
                "Equipment not found.");

        if (equipment == null) {
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this equipment?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (equipmentManager.deleteEquipment(equipment.getId())) {
            System.out.println("Equipment deleted successfully.");
        } else {
            System.out.println("Equipment could not be deleted.");
        }
    }

    private void deleteUserByScreen() {
        String username = InputOutput.askText("Enter username:");
        String email = InputOutput.askEmail("Enter email:");
        User user = userManager.getUser(username, email);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        if (currentAdmin != null && currentAdmin.getUserId() != null && currentAdmin.getUserId().equals(user.getUserId())) {
            System.out.println("You cannot delete your own admin user.");
            return;
        }

        if (!InputOutput.askYesNo("Are you sure you want to delete this user?")) {
            System.out.println("Delete cancelled.");
            return;
        }

        userManager.deleteUser(user);

        if (userManager.getUser(username, email) == null) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User could not be deleted.");
        }
    }

    private void listAllPatients() {
        Utils.printList(patientManager.listAllPatients(), "There are no patients.");
    }

    private void listAllDoctors() {
        Utils.printList(doctorManager.listAllDoctors(), "There are no doctors.");
    }

    private void listAllAppointments() {
        Utils.printList(appointmentManager.listAllAppointments(), "There are no appointments.");
    }

    private void listAllSurgeries() {
        Utils.printList(surgeryManager.listAllSurgeries(), "There are no surgeries.");
    }

    private void listAllEquipment() {
        Utils.printList(equipmentManager.listAllEquipments(), "There is no equipment.");
    }

    private void listAllUsers() {
        Utils.printList(userManager.getAllUsers(), "There are no users.");
    }

    private void addDoctorToSurgeryByScreen() {
        Doctor doctor = Common.askExistingId("Enter doctor ID:", doctorManager::getDoctorById,
                "Doctor not found.");
        if (doctor == null) {
            return;
        }

        Surgery surgery = Common.askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById,
                "Surgery not found.");
        if (surgery == null) {
            return;
        }

        if (Common.isDoctorAlreadyInSurgery(doctorManager, doctor.getId(), surgery.getId())) {
            System.out.println("This doctor is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addDoctorToSurgery(doctor.getId(), surgery.getId())) {
            System.out.println("Doctor assigned to surgery successfully.");
        } else {
            System.out.println("Doctor could not be assigned to surgery.");
        }
    }

}
