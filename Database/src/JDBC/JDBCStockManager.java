package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCStockManager {

    private Connection c;
    private Integer reference_code;

    public JDBCStockManager(Connection c) {
        if (c == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Connection cannot be null");
            throw nullPointerException;
        }

        this.c = c;
    }

    public JDBCStockManager(Connection c, int reference_code)
            throws NullPointerException, IllegalArgumentException {

        if (c == null) {
            NullPointerException nullPointerException =
                    new NullPointerException("Connection cannot be null");
            throw nullPointerException;
        }

        if (reference_code < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Stock reference code cannot be negative");
            throw illegalArgumentException;
        }

        this.c = c;
        this.reference_code = reference_code;
    }

    public void increase_amount_of_product(int quantity)
            throws IllegalArgumentException, IllegalStateException {

        if (quantity < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Quantity cannot be negative");
            throw illegalArgumentException;
        }

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

    public void decrease_amount_of_product(int quantity)
            throws IllegalArgumentException, IllegalStateException {

        if (quantity < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Quantity cannot be negative");
            throw illegalArgumentException;
        }

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

    public void update_price(float newPrice)
            throws IllegalArgumentException, IllegalStateException {

        if (newPrice < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("New price cannot be negative");
            throw illegalArgumentException;
        }

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

    public float calculate_total_price(int amount_required_for_surgery)
            throws IllegalArgumentException, IllegalStateException {

        if (amount_required_for_surgery < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Amount required for surgery cannot be negative");
            throw illegalArgumentException;
        }

        String sql = "SELECT price FROM Stock WHERE reference_code = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, getReferenceCode());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    if (rs.getFloat("price") < 0) {
                        IllegalArgumentException illegalArgumentException =
                                new IllegalArgumentException("Stock price cannot be negative");
                        throw illegalArgumentException;
                    }

                    return rs.getFloat("price") * amount_required_for_surgery;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error calculating total stock price");
            e.printStackTrace();
        }

        return 0;
    }

    private int getReferenceCode()
            throws IllegalStateException {

        if (reference_code == null) {
            IllegalStateException illegalStateException =
                    new IllegalStateException("Stock reference code is required");
            throw illegalStateException;
        }

        if (reference_code < 0) {
            IllegalArgumentException illegalArgumentException =
                    new IllegalArgumentException("Stock reference code cannot be negative");
            throw illegalArgumentException;
        }

        return reference_code;
    }
}
