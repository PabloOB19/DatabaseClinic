package POJOS;

import java.time.LocalDate;
import java.util.Objects;

import Enums.Turn;
import Enums.Type_of_surgery;

public class Surgery {
	
	private int id;
    private LocalDate date;
    private Type_of_surgery type;
    private float price;
    private Turn turn;

    public Surgery ()
    {
    }
    
    public Surgery(int id, LocalDate date, Type_of_surgery type, float price, Turn turn) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.price = price;
        this.turn = turn;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    @Override
    public String toString() {
        return "Surgery{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", price=" + price +
                ", turn=" + turn +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Surgery surgery = (Surgery) object;
        return id == surgery.id && Float.compare(price, surgery.price) == 0 && Objects.equals(date, surgery.date) && Objects.equals(type, surgery.type) && turn == surgery.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, type, price, turn);
    }

}
