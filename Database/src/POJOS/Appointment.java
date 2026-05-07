package POJOS;

import java.time.LocalDate;
import java.util.Objects;


import Enums.*;


public class Appointment {
	
	private int id;
    private Type_of_appointment type;
    private LocalDate date;
    private Turn turn;
    private double price;
    private Doctor doctor;
    
    public Appointment () {

    }
    
    public Appointment(int id, Type_of_appointment type, LocalDate date,
            Turn turn, double price, Doctor doctor) 
{
    	this.id = id;
        this.type = type;
        this.date = date;
        this.turn = turn;
        this.price = price;
        this.doctor=doctor;
        
        
}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type_of_appointment getType() {
        return type;
    }

    public void setType(Type_of_appointment type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Appointment that = (Appointment) object;
        return id == that.id && Double.compare(price, that.price) == 0 && type == that.type 
        	&& Objects.equals(date, that.date) && turn == that.turn 
        	&& Objects.equals(doctor, that.doctor) ;
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(id, type, date, turn, price, doctor);
    }
    
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", type=" + type +
                ", date=" + date +
                ", turn=" + turn +
                ", price=" + price +
                 ", doctor=" + doctor +
                '}';
    }


}
