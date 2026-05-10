package Wrappers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import POJOS.*;

import javax.xml.bind.annotation.XmlElement;


@XmlRootElement(name="DataBase")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
	    "patients",
	    "doctors",
	    "appointments",
	    "surgeries",
	    "equipments",
	    "roles",
	    "users"
	})

public class DatabaseWrapper implements Serializable{

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "patient")
    private List<Patient> patients;

    @XmlElement(name = "doctor")
    private List<Doctor> doctors;

    @XmlElement(name = "appointment")
    private List<Appointment> appointments;

    @XmlElement(name = "surgery")
    private List<Surgery> surgeries;

    @XmlElement(name = "equipment")
    private List<Equipment> equipments;

    @XmlElement(name = "role")
    private List<Role> roles;

    @XmlElement(name = "user")
    private List<User> users;


    public DatabaseWrapper() {
        this.patients = new ArrayList<>();
        this.doctors = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.surgeries = new ArrayList<>();
        this.equipments = new ArrayList<>();
        this.roles = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
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

    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }


}
