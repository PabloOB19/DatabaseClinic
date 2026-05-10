package POJOS;

import java.time.LocalDate;
import Enums.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "doctor")

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(
    propOrder = {
        "name",
        "surname",
        "email",
        "sex",
        "dob",
        "photo",
        "specialty",
        "appointments",
        "surgeries"
    }
)

public class Doctor implements Serializable{

    // ATTRIBUTE
    @XmlAttribute
    private int id;

    // ELEMENTS
    @XmlElement
    private String name;

    @XmlElement
    private String surname;

    @XmlElement
    private String email;

    @XmlElement
    private Sex sex;

    @XmlElement
    private LocalDate dob;

    @XmlElement
    private byte[] photo;

    @XmlElement
    private String specialty;

    @XmlElementWrapper(name = "appointments")
    @XmlElement(name = "appointment")
    private List<Appointment> appointments;

    @XmlElementWrapper(name = "surgeries")
    @XmlElement(name = "surgery")
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
		  this.specialty = speciality;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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
                ", specialty='" + specialty + '\'' +
                ", date of birth=" + dob +
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
