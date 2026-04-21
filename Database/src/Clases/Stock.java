package Clases;

public class Stock {
	private  int reference_code;
    private int stock_code;
    float price;
     type;
    Origin origin;
    String description;
}


        public Stock(int reference_code, Type type, int amount, float price, Enum origin, String description) {

            this.reference_code = reference_code;
            this.amount = amount;
            this.description = description;
            this.type = type;
            this.origin = origin;
        }



}
