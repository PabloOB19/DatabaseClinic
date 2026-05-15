	package ifaces;

import java.util.List;
import POJOS.*;

public interface AppointmentManager {
	
	boolean insertAppointment(Appointment appointment);
	Appointment getAppointmentById(int id);
	List<Appointment> listAllAppointments();
    boolean updateAppointment(Appointment appointment)  ;
    boolean deleteAppointment(int id) ;
    List<Appointment> listAppointmentsByDoctor(int doctor_Id);
    List<Appointment> listAppointmentsByPatient(int patient_Id);
    
}
