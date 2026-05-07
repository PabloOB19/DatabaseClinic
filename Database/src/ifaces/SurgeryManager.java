package ifaces;

import java.util.List;

import POJOS.Surgery;

public interface SurgeryManager 
{
	void insertSurgery(Surgery surgery);
	Surgery getSurgeryById(int id);
	List<Surgery> listAllSurgeries();
	void updateSurgery(Surgery surgery);
	void deleteSurgery(int id);
	List<Surgery> listSurgeriesByDoctor(int doctorId);
	List<Surgery> listSurgeriesByPatient(int patientId);
	void addDoctorToSurgery(int doctorId, int surgeryId);
	void addEquipmentToSurgery(int equipmentId, int surgeryId);

}
