package POJOS;

import java.time.LocalDate;
import java.util.*;


import Enums.*;

public class Patient {

    private int id;
    private Sex sex;
    private String name;
    private String surname;
    private LocalDate dob;
    private int height;
    private float weight;
    private byte[] photo;
    private String info;
    private String email;
    private List<Appointment> appointments;
    private List<Surgery> surgeries;

    public Patient() {
        this.appointments = new ArrayList<>();
        this.surgeries = new ArrayList<>();
    }

    public Patient(int id, Sex sex, String name, String surname, LocalDate dob,
                   int height, float weight, byte[] photo, String info, String email,
                   List<Appointment> appointments, List<Surgery> surgeries) {
        this.id = id;
        this.sex = sex;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.photo = photo;
        this.info = info;
        this.email = email;
        this.appointments = appointments != null ? appointments : new ArrayList<>();
        this.surgeries = surgeries != null ? surgeries : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }   

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }   

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }   

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }   

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }   

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Patient patient = (Patient) object;
        return id == patient.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", sex=" + sex +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", height=" + height +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}