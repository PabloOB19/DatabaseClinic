package Main;

import JDBC.JDBCAppointmentManager;
import JDBC.JDBCDoctorManager;
import JDBC.JDBCEquipmentManager;
import JDBC.JDBCPatientManager;
import JDBC.JDBCSurgeryManager;
import POJOS.Doctor;
import POJOS.Equipment;
import POJOS.Surgery;
import POJOS.User;

public class DoctorMenu {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private JDBCEquipmentManager equipmentManager;
    private Doctor currentDoctor;

    public DoctorMenu(User loggedUser, JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager,
            JDBCEquipmentManager equipmentManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.equipmentManager = equipmentManager;
        this.currentDoctor = findDoctorByUserEmail(loggedUser);
    }

    public void run() {
        if (currentDoctor == null) {
            System.out.println("Contact with administration.");
            return;
        }

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
                addEquipmentToMySurgeryByScreen();
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
                listSurgeriesByCurrentDoctor();
                break;
            case 2:
                listAppointmentsByCurrentDoctor();
                break;
            case 3:
                Common.listDoctorsBySpecialty(doctorManager);
                break;
            case 4:
                Common.listDoctorsBySurgery(doctorManager);
                break;
            case 5:
                Common.listEquipmentBySurgery(equipmentManager);
                break;
            case 6:
                Common.listEquipmentByCategory(equipmentManager);
                break;
            default:
                System.out.println("Invalid list option.");
                break;
        }
    }

    private void getMenu() {
        Utils.printDoctorGetMenu();
        int op = InputOutput.askInt("\n Choose an option: ");

        switch (op) {
            case 1:
                Common.getPatientById(patientManager);
                break;
            case 2:
                getCurrentDoctorById();
                break;
            case 3: 
            	Common.getEquipmentById(equipmentManager);
            	break;
            default:
                System.out.println("Invalid get option.");
                break;
        }
    }

    private Doctor findDoctorByUserEmail(User loggedUser) {
        if (loggedUser == null || loggedUser.getEmail() == null) {
            return null;
        }

        for (Doctor doctor : doctorManager.listAllDoctors()) {
            if (doctor.getEmail() != null && doctor.getEmail().equalsIgnoreCase(loggedUser.getEmail())) {
                return doctor;
            }
        }

        return null;
    }

    private void addEquipmentToMySurgeryByScreen() {
        Equipment equipment = Common.askExistingId("Enter equipment ID:", equipmentManager::getEquipmentById,
                "Equipment not found.");
        if (equipment == null) {
            return;
        }

        Surgery surgery = Common.askExistingId("Enter surgery ID:", surgeryManager::getSurgeryById,
                "Surgery not found.");
        if (surgery == null) {
            return;
        }

        if (!Common.isDoctorAlreadyInSurgery(doctorManager, currentDoctor.getId(), surgery.getId())) {
            System.out.println("You cannot add equipment to this surgery.");
            return;
        }

        if (Common.isEquipmentAlreadyInSurgery(equipmentManager, equipment.getId(), surgery.getId())) {
            System.out.println("This equipment is already assigned to that surgery.");
            return;
        }

        if (surgeryManager.addEquipmentToSurgery(equipment.getId(), surgery.getId())) {
            System.out.println("Equipment assigned to surgery successfully.");
        } else {
            System.out.println("Equipment could not be assigned to surgery.");
        }
    }

    private void listSurgeriesByCurrentDoctor() {
        Utils.printList(surgeryManager.listSurgeriesByDoctor(currentDoctor.getId()), "There are no surgeries for your doctor profile.");
    }

    private void listAppointmentsByCurrentDoctor() {
        Utils.printList(appointmentManager.listAppointmentsByDoctor(currentDoctor.getId()), "There are no appointments for your doctor profile.");
    }

    private void getCurrentDoctorById() {
        Doctor doctor = doctorManager.getDoctorById(currentDoctor.getId());
        Utils.printObject(doctor, "Doctor not found.");
    }

}
