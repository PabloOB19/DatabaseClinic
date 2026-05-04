package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCStockManager {
    
    private Connection c;
    private Integer reference_code;

    public JDBCStockManager(Connection c) {
        this.c = c;
    }

    public JDBCStockManager(Connection c, int reference_code) {
        this.c = c;
        this.reference_code = reference_code;
    }

    public void increase_amount_of_product(int quantity) {
        String sql = "UPDATE Stock SET amount = amount + ? WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, getReferenceCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error increasing stock amount");
            e.printStackTrace();
        }
    }

    public void decrease_amount_of_product(int quantity) {
        String sql = "UPDATE Stock SET amount = amount - ? WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, getReferenceCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error decreasing stock amount");
            e.printStackTrace();
        }
    }

    public void update_price(float newPrice) {
        String sql = "UPDATE Stock SET price = ? WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setFloat(1, newPrice);
            ps.setInt(2, getReferenceCode());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating stock price");
            e.printStackTrace();
        }
    }

    public float calculate_total_price(int amount_required_for_surgery) {
        String sql = "SELECT price FROM Stock WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, getReferenceCode());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getFloat("price") * amount_required_for_surgery;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculating total stock price");
            e.printStackTrace();
        }

        return 0;
    }

    private int getReferenceCode() {
        if (reference_code == null) {
            throw new IllegalStateException("Stock reference code is required");
        }

        return reference_code;
    }
}
