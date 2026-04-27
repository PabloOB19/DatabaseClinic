package Clases;

import java.time.LocalDate;
import java.util.List;

import Enums.Payment_status;
import Enums.Turn;
import Enums.Type_of_surgery;

public class Surgery {

    private int identificator;
    private Type_of_surgery type;
    private LocalDate date;
    private Turn turn;
    private float price;
    private int amount_of_hours;
    private int tariff;
    private Patient patient;
    private List<Doctor> doctors;
    private List<Stock> stocks;
    private Payment_status payment;

    public Surgery(int identificator, Type_of_surgery type, LocalDate date, Turn turn, float price, int amount_of_hours, int tariff, Patient patient, List<Doctor> doctors, List<Stock> stocks, Payment_status payment)
    {
        this.identificator = identificator;
        this.type = type;
        this.date = date;
        this.turn = turn;
        this.price = price;
        this.amount_of_hours = amount_of_hours;
        this.tariff = tariff;
        this.patient = patient;
        this.doctors = doctors;
        this.stocks = stocks;
        this.payment = payment;
    }

    public int getIdentificator() {
        return identificator;
    }

    public void setIdentificator(int identificator) {
        this.identificator = identificator;
    }

    public Type_of_surgery getType() {
        return type;
    }

    public void setType(Type_of_surgery type) {
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

    public int getAmount_of_hours() {
        return amount_of_hours;
    }

    public void setAmount_of_hours(int amount_of_hours) {
        this.amount_of_hours = amount_of_hours;
    }

    public int getTariff() {
        return tariff;
    }

    public void setTariff(int tariff) {
        this.tariff = tariff;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Payment_status getPayment() {
        return payment;
    }

    public void setPayment(Payment_status payment) {
        this.payment = payment;
    }
    //PRUEBA
    
}
