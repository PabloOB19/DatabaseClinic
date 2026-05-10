package POJOS;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import xml.SQLDateAdapter;


import Enums.*;

@XmlRootElement(name = "patient")

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(propOrder = {
		"name",
		"surname",
		"email",
		"sex",
		"dob",
		"height",
		"weight",
		"photo",
		"info"
})


public class Patient implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@XmlAttribute
    private int id;
	
	@XmlElement
    private String name;
    
	@XmlElement
    private String surname;
    
	@XmlElement
    private String email;
    
	@XmlElement
    private Sex sex;
    
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private LocalDate dob;
    
	@XmlElement
    private int height;
    
	@XmlElement
    private float weight;
    
	@XmlElement
    private byte[] photo;
    
	@XmlElement
    private String info;
    
	@XmlTransient
    private List<Appointment> appointments;
    
	@XmlTransient
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
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email +
                ", sex=" + sex +
                ", dob=" + dob +
                ", height=" + height +
                ", weight=" + weight +
                ", info='" + info +
                 '\'' +
                '}';
    }
}
