package ifaces;

import java.util.List;

import POJOS.Patient;

public interface PatientManager {
	
	boolean insertPatient(Patient patient);
	Patient getPatientById(int id);
	List<Patient> listAllPatients();
	boolean updatePatient(Patient patient);
	boolean deletePatient(int id);

}
