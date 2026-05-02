package JDBC;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.Origin;
import Enums.Turn;
import Enums.Type_of_material;
import POJOS.Doctor;
import POJOS.Stock;

public class JDBCSurgeryManager {

    private Connection c;
    private int surgery_identificator;

    public JDBCSurgeryManager(Connection c, int surgery_identificator) 
    {
        this.c = c;
        this.surgery_identificator = surgery_identificator;
    }

    public void add_doctor(Doctor doctor)
    {
        String sql = "INSERT INTO DOCTOR_SURGERY " + "(doctor_medical_license_number, surgery_identificator) " + "VALUES (?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, doctor.getMedical_license_number());
            ps.setInt(2, surgery_identificator);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding doctor to surgery");
            e.printStackTrace();
        }
    }

    public void remove_doctor(int doctor_medical_license) {
        String sql = "DELETE FROM DOCTOR_SURGERY "
                + "WHERE doctor_medical_license_number = ? "
                + "AND surgery_identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, String.valueOf(doctor_medical_license));
            ps.setInt(2, surgery_identificator);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error removing doctor from surgery");
            e.printStackTrace();
        }
    }

    public void material_used(Stock stock, int amount) {
        String sql = "INSERT INTO SURGERY_STOCK "
                + "(surgery_identificator, stock_reference_code, amount) "
                + "VALUES (?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery_identificator);
            ps.setInt(2, stock.getReference_code());
            ps.setInt(3, amount);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error adding material used in surgery");
            e.printStackTrace();
        }
    }

    public void change_surgery_date(LocalDate newDate, Turn turn) {
        String sql = "UPDATE SURGERY "
                + "SET date = ?, turn = ? "
                + "WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(newDate));
            ps.setString(2, turn.name());
            ps.setInt(3, surgery_identificator);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error changing surgery date");
            e.printStackTrace();
        }
    }

    public void cancel_surgery() {
        String deleteDoctorsSql = "DELETE FROM DOCTOR_SURGERY WHERE surgery_identificator = ?";
        String deleteMaterialsSql = "DELETE FROM SURGERY_STOCK WHERE surgery_identificator = ?";
        String deleteSurgerySql = "DELETE FROM SURGERY WHERE identificator = ?";

        try {
            c.setAutoCommit(false);

            try (PreparedStatement ps = c.prepareStatement(deleteDoctorsSql)) {
                ps.setInt(1, surgery_identificator);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(deleteMaterialsSql)) {
                ps.setInt(1, surgery_identificator);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = c.prepareStatement(deleteSurgerySql)) {
                ps.setInt(1, surgery_identificator);
                ps.executeUpdate();
            }

            c.commit();

        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            System.out.println("Error cancelling surgery");
            e.printStackTrace();

        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public float calculate_total_price() {
        String sql = "SELECT amount_of_hours, tariff "
                + "FROM SURGERY "
                + "WHERE identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery_identificator);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    float amountOfHours = rs.getFloat("amount_of_hours");
                    float tariff = rs.getFloat("tariff");

                    return amountOfHours * tariff;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error calculating total surgery price");
            e.printStackTrace();
        }

        return 0;
    }

    public List<Stock> view_material_used() {
        List<Stock> materials = new ArrayList<>();

        String sql = "SELECT s.reference_code, s.type, s.amount, s.price, s.origin, s.description "
                + "FROM STOCK s "
                + "JOIN SURGERY_STOCK ss "
                + "ON s.reference_code = ss.stock_reference_code "
                + "WHERE ss.surgery_identificator = ?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, surgery_identificator);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Stock stock = new Stock(
                            rs.getInt("reference_code"),
                            Type_of_material.valueOf(rs.getString("type")),
                            rs.getInt("amount"),
                            rs.getFloat("price"),
                            Origin.valueOf(rs.getString("origin")),
                            rs.getString("description")
                    );

                    materials.add(stock);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error viewing material used in surgery");
            e.printStackTrace();
        }

        return materials;
    }
    //
}