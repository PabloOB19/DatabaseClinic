package JDBC;

import java.sql.*;
import java.util.*;


import Enums.*;
import POJOS.*;
import ifaces.EquipmentManager;


public class JDBCEquipmentManager implements EquipmentManager {
	
	private Connection c;

    public JDBCEquipmentManager(Connection c) {
        this.c = c;
    }

    @Override
    public void insertEquipment(Equipment equipment) {
        String sql = "INSERT INTO Equipment (name, category, quantity, price, expiration_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, equipment.getName());
            p.setString(2, equipment.getCategory().name());
            p.setInt(3, equipment.getQuantity());
            p.setDouble(4, equipment.getPrice());
            p.setDate(5, java.sql.Date.valueOf(equipment.getExpiration_date()));
            
            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating equipment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = p.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	equipment.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating equipment failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during insertEquipment.");
            e.printStackTrace();
        }
    }

    
    
   
    @Override
    public Equipment getEquipmentById(int id) {
        Equipment result = null;

        String sql = "SELECT * FROM Equipment WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    result = new Equipment(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Category.valueOf(rs.getString("category")),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getDate("expiration_date").toLocalDate(),
                            null
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during getEquipmentById.");
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Equipment> listAllEquipments() {
        List<Equipment> list = new ArrayList<>();

        String sql = "SELECT * FROM Equipment";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {

            	Equipment equipment = new Equipment(
            			rs.getInt("id"),
                        rs.getString("name"),
                        Category.valueOf(rs.getString("category")),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDate("expiration_date").toLocalDate(),
                        null
                );

                list.add(equipment);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllEquipment.");
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public void updateEquipment(Equipment equipment) {
        String sql = "UPDATE Equipment SET name = ?, category = ?, quantity = ?, price = ?, expiration_date = ? WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
        	p.setString(1, equipment.getName());
            p.setString(2, equipment.getCategory().name());
            p.setInt(3, equipment.getQuantity());
            p.setDouble(4, equipment.getPrice());
            p.setDate(5, java.sql.Date.valueOf(equipment.getExpiration_date()));
            p.setInt(6, equipment.getId());

            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Database error during updateEquipment.");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEquipment(int id) {
        String sql = "DELETE FROM Equipment WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            p.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database error during deleteEquipment.");
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Equipment> listEquipmentBySurgery(int surgery_Id) {
        List<Equipment> list = new ArrayList<>();

        String sql = "SELECT e.* " +
                     "FROM Equipment e " +
                     "JOIN SURGERY_EQUIPMENT se ON e.id = se.equipment_id " +
                     "WHERE se.surgery_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, surgery_Id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Equipment equipment = new Equipment(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Category.valueOf(rs.getString("category")),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getDate("expiration_date").toLocalDate(),
                            null
                    );

                    list.add(equipment);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listEquipmentBySurgery.");
            e.printStackTrace();
        }

        return list;
    }

    @Override

    public List<Equipment> listEquipmentByCategory(Category category) {
        List<Equipment> list = new ArrayList<>();

        String sql = "SELECT * FROM Equipment WHERE category = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, category.name());

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Equipment equipment = new Equipment(
                            rs.getInt("id"),
                            rs.getString("name"),
                            Category.valueOf(rs.getString("category")),
                            rs.getInt("quantity"),
                            rs.getDouble("price"),
                            rs.getDate("expiration_date").toLocalDate(),
                            null
                    );

                    list.add(equipment);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listEquipmentByCategory.");
            e.printStackTrace();
        }

        return list;
    }



}

    
	
	
	
	

}
