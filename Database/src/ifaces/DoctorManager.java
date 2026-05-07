package ifaces;

import java.util.List;

import POJOS.Doctor;

public interface DoctorManager {
    void insertDoctor(Doctor doctor);
    Doctor getDoctorById(int id);
    List<Doctor> listAllDoctors();
    void updateDoctor(Doctor doctor);
    void deleteDoctor(int id);
    void addDoctorToAppointment(int doctor_id, int appointment_id);
    List<Doctor> listDoctorsBySpecialty(String specialty);
    List<Doctor> listDoctorsBySurgery(int surgery_id);
}
