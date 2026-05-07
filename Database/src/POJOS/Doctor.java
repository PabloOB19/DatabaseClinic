package POJOS;

import java.time.LocalDate;
import java.util.*;


import Enums.*;

public class Doctor 
{
	private int id;
    private String name;
    private String surname;
    private byte[] photo;
    private Sex sex;
    private String email;
    private String speciality;
    private LocalDate dob;
    private List<Appointment> appointments;
    private List<Surgery> surgeries;

    public Doctor() {
        this.appointments = new ArrayList<>();
        this.surgeries = new ArrayList<>();
    }

    public Doctor(int id, String name, String surname, byte[] photo, Sex sex,
            String email, String speciality, LocalDate dob,
            List<Appointment> appointments, List<Surgery> surgeries) {
		  this.id = id;
		  this.name = name;
		  this.surname = surname;
		  this.photo = photo;
		  this.sex = sex;
		  this.email = email;
		  this.speciality = speciality;
		  this.dob = dob;
		  this.appointments = appointments != null ? appointments : new ArrayList<>();
		  this.surgeries = surgeries != null ? surgeries : new ArrayList<>();
		}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    
    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments != null ? appointments : new ArrayList<>();
    }

    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries != null ? surgeries : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", speciality='" + speciality + '\'' +
                ", dateOfBirth=" + dob +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Doctor doctor = (Doctor) object;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
