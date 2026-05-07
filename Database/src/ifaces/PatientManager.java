package ifaces;

import java.util.List;

import POJOS.Patient;

public interface PatientManager {
	
	void insertPatient(Patient patient);
	List<Patient> listAllPatients();
	void updatePatient(Patient patient);
	void deletePatient(int id);

}
