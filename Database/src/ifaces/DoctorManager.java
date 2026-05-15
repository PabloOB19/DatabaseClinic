package ifaces;

import java.util.List;

import POJOS.Doctor;

public interface DoctorManager {
    boolean insertDoctor(Doctor doctor);
    Doctor getDoctorById(int id);
    List<Doctor> listAllDoctors();
    boolean updateDoctor(Doctor doctor);
    boolean deleteDoctor(int id);
    boolean addDoctorToAppointment(int doctor_id, int appointment_id);
    List<Doctor> listDoctorsBySpecialty(String specialty);
    List<Doctor> listDoctorsBySurgery(int surgery_id);
    List<Doctor> listDoctorsByAppointment(int appointmentId);
}
