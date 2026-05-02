package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCStockManager {
	
	private Connection c;

    public JDBCStockManager(Connection c) {
        this.c = c;
    }
	
		   



		   public void increase_amount(int quantity){
		  
			   String sql = "UPDATE Stock SET quantity = ?, turn = ? WHERE identificator = ?";

		        try (PreparedStatement ps = c.prepareStatement(sql)) {
		            
		            ps.setInt(1,quantity );

		            ps.executeUpdate();
		        } catch (SQLException e) {
		            System.out.println("Error changing appointment date");
		            e.printStackTrace();
		        }
		    }
		   

			  

		public void decrease_amount_of_product(int quantity){
			String sql = "UPDATE Stock SET amount = ?, turn = ? WHERE identificator = ?";
		
		 try{

		     PreparedStatement stmt = c.prepareStatement(sql);
		     stmt.setInt(1, quantity);
		     stmt.executeUpdate();
		    
		     }
		     catch (Exception e) {
		         e.printStackTrace();
		     }
		}
		     public void update_price(float newPrice){
		     
		          String sql = "UPDATE Stock SET amount = ?, turn = ? WHERE identificator = ?";
		          try {
		          PreparedStatement stmt = c.prepareStatement(sql);
		          stmt.setFloat(1, newPrice);
		          stmt.executeUpdate();
		         
		          }
		          catch (Exception e) {
		              e.printStackTrace();
		          }
		          }

		   // float calculate_total_price(int amount_required_for_surgery){
		    	  
		      }




// }
