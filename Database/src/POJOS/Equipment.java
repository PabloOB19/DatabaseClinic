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

@XmlRootElement(name = "equipment")

@XmlAccessorType(XmlAccessType.FIELD)

@XmlType(propOrder = {
		"id",
		"name",
		"category",
		"quantity",
		"price",
		"expiration_date",
		"surgeries"
})


public class Equipment implements Serializable{

	@XmlAttribute
    private int id;
	
	@XmlElement
    private String name;
	
	@XmlElement
    private Category category;
	
	@XmlElement
    private int quantity;
	
	@XmlElement
    private double price;
	
	@XmlElement
    private LocalDate expiration_date;
    
    @XmlElementWrapper(name = "surgeries")
    @XmlElement(name = "surgery")
    private List<Surgery> surgeries;

    
    public Equipment() 
    {
        this.surgeries = new ArrayList<>();
    }

    public Equipment(int id, String name,Category category, int quantity, double price, 
                     LocalDate expiration_date, List<Surgery> surgeries) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
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
                ", category=" + category +
                ", quantity=" + quantity +
                ", price=" + price +
                ", expirationDate=" + expiration_date +
                '}';
    }
}