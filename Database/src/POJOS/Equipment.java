package POJOS;

import java.time.LocalDate;
import java.util.*;

import Enums.Category;

public class Equipment {

    private int id;
    private String name;
    private int quantity;
    private double price;
    private Category category;
    private LocalDate expiration_date;
    private List<Surgery> surgeries;

    public Equipment() {
        this.surgeries = new ArrayList<>();
    }

    public Equipment(int id, String name, int quantity, double price, Category category,
                     LocalDate expiration_date, List<Surgery> surgeries) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expiration_date = expiration_date;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }   

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }   

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }   

    public LocalDate getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDate expiration_date) {
        this.expiration_date = expiration_date;
    }   

    public List<Surgery> getSurgeries() {
        return surgeries;
    }

    public void setSurgeries(List<Surgery> surgeries) {
        this.surgeries = surgeries != null ? surgeries : new ArrayList<>();
    }

    public void addSurgery(Surgery surgery) {
        if (surgery != null && !surgeries.contains(surgery)) {
            surgeries.add(surgery);
        }
    }

    public void removeSurgery(Surgery surgery) {
        surgeries.remove(surgery);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Equipment equipment = (Equipment) object;
        return id == equipment.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category=" + category +
                ", expirationDate=" + expiration_date +
                '}';
    }
}