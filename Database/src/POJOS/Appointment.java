package POJOS;

import java.time.LocalDate;
import java.util.Objects;

import Enums.Type_of_appointment;
import Enums.Turn;
import Enums.Payment_status;

public class Appointment {

    private int identificator;
    private Type_of_appointment type;
    private LocalDate date;
    private Turn turn;
    private float price;
    private Patient patient;
    private Doctor doctor;
    private Payment_status payment_status;

    // Constructor
    public Appointment(int identificator, Type_of_appointment type, LocalDate date,
                       Turn turn, float price, Patient patient, Doctor doctor,
                       Payment_status payment_status) {

        this.identificator = identificator;
        this.type = type;
        this.date = date;
        this.turn = turn;
        this.price = price;
        this.patient = patient;
        this.doctor = doctor;
        this.payment_status = payment_status;
    }

    // Getters y Setters

    public int getIdentificator() {
        return identificator;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Payment_status getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(Payment_status payment_status) {
        this.payment_status = payment_status;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Appointment that = (Appointment) object;
        return identificator == that.identificator && Float.compare(price, that.price) == 0 && type == that.type && Objects.equals(date, that.date) && turn == that.turn && Objects.equals(patient, that.patient) && Objects.equals(doctor, that.doctor) && payment_status == that.payment_status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificator, type, date, turn, price, patient, doctor, payment_status);
    }
}