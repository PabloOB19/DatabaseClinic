package ifaces;

import java.sql.SQLException;
import java.util.List;

import POJOS.Appointment;
import POJOS.Patient;

public interface PatientManager {
	
	void insertPatient(Patient patient);
	Patient getPatientById(int id) throws SQLException;
	List<Patient> listAllPatients();
	void updatePatient(Patient patient);
	void deletePatient(int id);

}
