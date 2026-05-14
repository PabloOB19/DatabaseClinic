package Main;

import JDBC.JDBCAppointmentManager;
import JDBC.JDBCDoctorManager;
import JDBC.JDBCEquipmentManager;
import JDBC.JDBCPatientManager;
import JDBC.JDBCSurgeryManager;
import Wrappers.DatabaseWrapper;
import ifaces.UserManager;
import ifaces.XmlManager;

public class XmlAdmin {

    private JDBCDoctorManager doctorManager;
    private JDBCPatientManager patientManager;
    private JDBCAppointmentManager appointmentManager;
    private JDBCSurgeryManager surgeryManager;
    private JDBCEquipmentManager equipmentManager;
    private UserManager userManager;
    private XmlManager xmlManager;

    public XmlAdmin(JDBCDoctorManager doctorManager, JDBCPatientManager patientManager,
            JDBCAppointmentManager appointmentManager, JDBCSurgeryManager surgeryManager,
            JDBCEquipmentManager equipmentManager, UserManager userManager, XmlManager xmlManager) {
        this.doctorManager = doctorManager;
        this.patientManager = patientManager;
        this.appointmentManager = appointmentManager;
        this.surgeryManager = surgeryManager;
        this.equipmentManager = equipmentManager;
        this.userManager = userManager;
        this.xmlManager = xmlManager;
    }

    public void run() {
        boolean xmlRun = true;

        while (xmlRun) {
            Utils.printXmlMenu();
            int op = InputOutput.askInt("\n Choose an option: ");

            switch (op) {
                case 1:
                    exportDatabaseToXML();
                    break;
                case 2:
                    importXMLToJava();
                    break;
                case 3:
                    convertXMLToHTML();
                    break;
                case 4:
                    xmlRun = false;
                    break;
                default:
                    System.out.println("Invalid XML option.");
                    break;
            }
        }
    }

    private DatabaseWrapper buildDatabaseWrapper() {
        DatabaseWrapper wrapper = new DatabaseWrapper();

        wrapper.setPatients(patientManager.listAllPatients());
        wrapper.setDoctors(doctorManager.listAllDoctors());
        wrapper.setAppointments(appointmentManager.listAllAppointments());
        wrapper.setSurgeries(surgeryManager.listAllSurgeries());
        wrapper.setEquipments(equipmentManager.listAllEquipments());
        wrapper.setRoles(userManager.getRoles());
        wrapper.setUsers(userManager.getAllUsers());

        return wrapper;
    }

    private void exportDatabaseToXML() {
        try {
            DatabaseWrapper wrapper = buildDatabaseWrapper();
            xmlManager.export_to_XML(wrapper);
            System.out.println("Database exported to XML successfully.");
        } catch (Exception e) {
            System.out.println("Database could not be exported to XML: " + e.getMessage());
        }
    }

    private void importXMLToJava() {
        try {
            DatabaseWrapper wrapper = xmlManager.import_to_Java();
            System.out.println("XML imported to Java successfully.");

            Utils.printList(wrapper.getPatients(), "No patients in XML.");
            Utils.printList(wrapper.getDoctors(), "No doctors in XML.");
            Utils.printList(wrapper.getAppointments(), "No appointments in XML.");
            Utils.printList(wrapper.getSurgeries(), "No surgeries in XML.");
            Utils.printList(wrapper.getEquipments(), "No equipment in XML.");
            Utils.printList(wrapper.getRoles(), "No roles in XML.");
            Utils.printList(wrapper.getUsers(), "No users in XML.");
        } catch (Exception e) {
            System.out.println("XML could not be imported to Java: " + e.getMessage());
        }
    }

    private void convertXMLToHTML() {
        try {
            xmlManager.convert_to_HTML();
            System.out.println("HTML generated successfully.");
        } catch (Exception e) {
            System.out.println("HTML could not be generated: " + e.getMessage());
        }
    }
}
