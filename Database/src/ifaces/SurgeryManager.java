package ifaces;

import java.sql.SQLException;
import java.util.List;

import POJOS.Surgery;

public interface SurgeryManager 
{
	void insertSurgery(Surgery surgery);
	Surgery getSurgeryById(int id);
	List<Surgery> listAllSurgeries();
	void updateSurgery(Surgery surgery);
	void deleteSurgery(int id) throws SQLException;
	List<Surgery> listSurgeriesByDoctor(int doctorId);
	List<Surgery> listSurgeriesByPatient(int patientId);

}
