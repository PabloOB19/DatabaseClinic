package ifaces;

import java.util.List;
import POJOS.*;

public interface AppointmentManager {
	
	void insertAppointment(Appointment appointment);
	Appointment getAppointmentById(int id);
	List<Appointment> listAllAppointments();
    void updateAppointment(Appointment appointment)  ;
    void deleteAppointment(int id) ;
    List<Appointment> listAppointmentsByDoctor(int doctor_Id);
    List<Appointment> listAppointmentsByPatient(int patient_Id);
    
}
