package POJOS;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import Enums.*;

@XmlRootElement(name = "surgery")

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(
    propOrder = {
        "id",
        "type",
        "date",
        "price",
        "turn",
        "patient",
        "doctors",
        "equipment",
    }
)

public class Surgery implements Serializable{
	
	@XmlAttribute
	private int id;
	
	@XmlElement
    private Type_of_surgery type;
    
	@XmlElement
    private LocalDate date;
    
	@XmlElement
    private double price;
    
	@XmlElement
    private Turn turn;
    
	@XmlElement
    private Patient patient;
    
	@XmlElementWrapper(name = "doctors")
    @XmlElement(name = "doctor")
    private List<Doctor> doctors;
    
	@XmlElementWrapper(name = "equipments")
    @XmlElement(name = "equipment")
    private List<Equipment> equipment;

    
    
    public Surgery() {
        this.setDoctors(new ArrayList<>());
        this.setEquipment(new ArrayList<>());
    }

    public Surgery(int id, LocalDate date, Type_of_surgery type, double price, Turn turn, Patient patient, 
    		List<Doctor> doctors, List<Equipment> equipment) {
	 this.id = id;
	 this.date = date;
	 this.type = type;
	 this.price = price;
	 this.turn = turn;
	 this.patient = patient;
	 this.doctors = doctors != null ? doctors : new ArrayList<>();
	 this.equipment = equipment != null ? equipment : new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Type_of_surgery getType() {
        return type;
    }

    public void setType(Type_of_surgery type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

	    public Turn getTurn() {
	        return turn;
	    }

	    public void setTurn(Turn turn) {
	        this.turn = turn;
	    }

	    public Patient getPatient() {
	        return patient;
	    }

	    public void setPatient(Patient patient) {
	        this.patient = patient;
	    }

	    public List<Equipment> getEquipment() {
			return equipment;
		}

		public void setEquipment(List<Equipment> equipment) {
			this.equipment = equipment != null ? equipment : new ArrayList<>();
		}
		public List<Doctor> getDoctors() {
			return doctors;
		}

		public void setDoctors(List<Doctor> doctors) {
			this.doctors = doctors != null ? doctors : new ArrayList<>();
		}

    @Override
    public String toString() {
        return "Surgery{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", price=" + price +
                ", turn=" + turn +
                ", patientId=" + (patient != null ? patient.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Surgery surgery = (Surgery) object;
        return id == surgery.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

	
	
}
