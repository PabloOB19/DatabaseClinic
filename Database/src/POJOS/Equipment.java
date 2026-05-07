package POJOS;


import java.util.Objects;
import java.time.LocalDate;

import Enums.*; 



public class Equipment {

	private int id;
	private String name;
	private int quantity;
	private float price;
	private Category category;
	private LocalDate expiration_date;
	
	public Equipment() {
	
	}
	
    public Equipment(int id, String name, int quantity, float price, Category category, LocalDate expiration_date) {
        this.id = id;
        this.name= name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expiration_date = expiration_date;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Equipment equipment = (Equipment) object;
        return id == equipment.id
                && quantity == equipment.quantity
                && Float.compare(price, equipment.price) == 0
                && Objects.equals(name, equipment.name)
                && category == equipment.category
                && Objects.equals(expiration_date, equipment.expiration_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, quantity, price, category, expiration_date);
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", category=" + category +
                ", expiration_date=" + expiration_date +
                '}';
    }
    
    
    
}

