package Clases;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Objects;

import Enums.Sex;

public class Patient {
	
    private String name;
    private String surname;
    private String dni;
    private LocalDate date_of_birth;
    private Sex sex;
    private int height;
    private int weight;
    private byte[] photo;
    private int phone_number;
    private String email;
    private String address;
    private String payment_method;
    private String clinical_history;
    private String personal_information;
    private List<Appointment> appointments;
    private List<Surgery> surgeries;
    
   
    public Patient(String name, String surname, String dni, LocalDate date_of_birth,
            Sex sex, int height, int weight, byte[] photo,
            int phone_number, String email, String address,
            String payment_method, String clinical_history,
            String personal_information) {

 this.name = name;
 this.surname = surname;
 this.dni = dni;
 this.date_of_birth = date_of_birth;
 this.sex = sex;
 this.height = height;
 this.weight = weight;
 this.photo = photo;
 this.phone_number = phone_number;
 this.email = email;
 this.address = address;
 this.payment_method = payment_method;
 this.clinical_history = clinical_history;
 this.personal_information = personal_information;
 this.appointments = new ArrayList<>();
 this.surgeries = new ArrayList<>();


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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Integer phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getClinical_history() {
        return clinical_history;
    }

    public void setClinical_history(String clinical_history) {
        this.clinical_history = clinical_history;
    }

    public String getPersonal_information() {
        return personal_information;
    }

    public void setPersonal_information(String personal_information) {
        this.personal_information = personal_information;
    }
    

    public List<Appointment> getAppointments() {
    	return appointments;
    }
    
    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Patient patient = (Patient) object;
        return height == patient.height && weight == patient.weight && phone_number == patient.phone_number && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(dni, patient.dni) && Objects.equals(date_of_birth, patient.date_of_birth) && sex == patient.sex && Arrays.equals(photo, patient.photo) && Objects.equals(email, patient.email) && Objects.equals(address, patient.address) && Objects.equals(payment_method, patient.payment_method) && Objects.equals(clinical_history, patient.clinical_history) && Objects.equals(personal_information, patient.personal_information);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, surname, dni, date_of_birth, sex, height, weight, phone_number, email, address, payment_method, clinical_history, personal_information);
        result = 31 * result + Arrays.hashCode(photo);
        return result;
    }

}
