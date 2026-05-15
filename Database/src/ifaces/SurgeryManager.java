package ifaces;

import java.util.List;

import POJOS.Surgery;

public interface SurgeryManager 
{
	boolean insertSurgery(Surgery surgery);
	Surgery getSurgeryById(int id);
	List<Surgery> listAllSurgeries();
	boolean updateSurgery(Surgery surgery);
	boolean deleteSurgery(int id);
	List<Surgery> listSurgeriesByDoctor(int doctorId);
	List<Surgery> listSurgeriesByPatient(int patientId);
	boolean addDoctorToSurgery(int doctorId, int surgeryId);
	boolean addEquipmentToSurgery(int equipmentId, int surgeryId);

}
