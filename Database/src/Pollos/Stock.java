package Pollos;
import java.util.Objects;

import Enums.Origin;
import Enums.Type_of_material; 


public class Stock {
	

	private int reference_code;
	private Type_of_material type;
	private int amount;
	private float price;
	private Origin origin;
	private String description;
	
    public Stock(int reference_code, Type_of_material type, int amount, float price, Origin origin, String description) {
        this.reference_code = reference_code;
        this.type = type;
        this.amount = amount;
        this.price = price;
        this.origin = origin;
        this.description = description;
    }

    public int getReference_code() {
        return reference_code;
    }

    public void setReference_code(int reference_code) {
        this.reference_code = reference_code;
    }

    public Type_of_material getType() {
        return type;
    }

    public void setType(Type_of_material type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Origin getOrigin() {
        return origin;
    }

    public void setOrigin(Origin origin) {
        this.origin = origin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Stock stock = (Stock) object;
        return reference_code == stock.reference_code && amount == stock.amount && Float.compare(price, stock.price) == 0 && type == stock.type && origin == stock.origin && Objects.equals(description, stock.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference_code, type, amount, price, origin, description);
    }

}
