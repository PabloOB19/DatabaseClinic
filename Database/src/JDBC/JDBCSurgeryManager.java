package JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Enums.*;
import POJOS.*;
import ifaces.SurgeryManager;

public class JDBCSurgeryManager implements SurgeryManager
{
	private Connection c;

    public JDBCSurgeryManager(Connection c) {
        this.c = c;
    }
	
  
	@Override
	public void insertSurgery(Surgery surgery) {

	    String sql = "INSERT INTO Surgery "
	            + "(type, date, price, turn, patient_id) "
	            + "VALUES (?, ?, ?, ?, ?)";

	    try (PreparedStatement p = c.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

	        p.setString(1, surgery.getType().name());
	        p.setString(2, surgery.getDate().toString());
	        p.setDouble(3, surgery.getPrice());
	        p.setString(4, surgery.getTurn().name());
	        p.setInt(5, surgery.getPatient().getId());

	        int affectedRows = p.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating surgery failed, no rows affected.");
	        }

	        try (ResultSet generatedKeys = p.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                surgery.setId(generatedKeys.getInt(1));
	            } else {
	                throw new SQLException("Creating surgery failed, no ID obtained.");
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during insertSurgery.");
	        e.printStackTrace();
	    }
	}
	
	private Patient getPatientById(int id) throws SQLException {
        Patient patient = null;

        String sql = "SELECT * FROM Patient WHERE id = ?";
        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient(
                            rs.getInt("id"),
                            Sex.valueOf(rs.getString("sex")),
                            rs.getString("name"),
                            rs.getString("surname"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            rs.getInt("height"),
                            rs.getFloat("weight"),
                            rs.getBytes("photo"),
                            rs.getString("info"),
                            rs.getString("email"),
                            null,
                            null
                    );
                }
            }
        }

        return patient;
	}
	
	@Override
	public Surgery getSurgeryById(int id) {
	    Surgery surgery = null;

	    String sql = "SELECT * FROM Surgery WHERE id = ?";

	    try (PreparedStatement p = c.prepareStatement(sql)) {
	        p.setInt(1, id);

	        try (ResultSet rs = p.executeQuery()) {
	            if (rs.next()) {

	                Patient patient = getPatientById(rs.getInt("patient_id"));

	                surgery = new Surgery(
	                        rs.getInt("id"),
	                        JDBCDateUtils.parseLocalDate(rs.getString("date")),
	                        Type_of_surgery.valueOf(rs.getString("type")),
	                        rs.getDouble("price"),
	                        Turn.valueOf(rs.getString("turn")),
	                        patient,
	                        new ArrayList<>(),
	                        new ArrayList<>()
	                );
	            }
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during getSurgeryById.");
	        e.printStackTrace();
	    }

	    return surgery;
	}
	
	@Override
    public List<Surgery> listAllSurgeries() {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT * FROM Surgery";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Patient patient = getPatientById(rs.getInt("patient_id"));

                Surgery surgery = new Surgery(
                        rs.getInt("id"),
                        JDBCDateUtils.parseLocalDate(rs.getString("date")),
                        Type_of_surgery.valueOf(rs.getString("type")),
                        rs.getDouble("price"),
                        Turn.valueOf(rs.getString("turn")),
                        patient,
                        new ArrayList<>(),
                        new ArrayList<>()
                );

                list.add(surgery);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllSurgeries.");
            e.printStackTrace();
        }

        return list;
    }
	
	@Override
	public void updateSurgery(Surgery surgery) {

	    String sql = "UPDATE Surgery "
	            + "SET type = ?, date = ?, price = ?, turn = ?, patient_id = ? "
	            + "WHERE id = ?";

	    try (PreparedStatement p = c.prepareStatement(sql)) {

	        p.setString(1, surgery.getType().name());
	        p.setString(2, surgery.getDate().toString());
	        p.setDouble(3, surgery.getPrice());
	        p.setString(4, surgery.getTurn().name());
	        p.setInt(5, surgery.getPatient().getId());
	        p.setInt(6, surgery.getId());

	        int affectedRows = p.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Updating surgery failed, no rows affected.");
	        }

	    } catch (SQLException e) {
	        System.out.println("Database error during updateSurgery.");
	        e.printStackTrace();
	    }
	}

	@Override
	public void deleteSurgery(int id) {

	    String deleteDoctorsSql = "DELETE FROM DOCTOR_SURGERY WHERE surgery_id = ?";
	    String deleteEquipmentSql = "DELETE FROM SURGERY_EQUIPMENT WHERE surgery_id = ?";
	    String deleteSurgerySql = "DELETE FROM Surgery WHERE id = ?";

	    try {
	        c.setAutoCommit(false);

	        try (PreparedStatement p = c.prepareStatement(deleteDoctorsSql)) {
	            p.setInt(1, id);
	            p.executeUpdate();
	        }

	        try (PreparedStatement p = c.prepareStatement(deleteEquipmentSql)) {
	            p.setInt(1, id);
	            p.executeUpdate();
	        }

	        try (PreparedStatement p = c.prepareStatement(deleteSurgerySql)) {
	            p.setInt(1, id);

	            int affectedRows = p.executeUpdate();

	            if (affectedRows == 0) {
	                throw new SQLException("Deleting surgery failed, no rows affected.");
	            }
	        }

	        c.commit();

	    } catch (SQLException e) {
	        try {
	            c.rollback();
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }

	        System.out.println("Database error during deleteSurgery.");
	        e.printStackTrace();

	    } finally {
	        try {
	            c.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	@Override
    public List<Surgery> listSurgeriesByDoctor(int doctor_Id) {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT s.* " +
                     "FROM Surgery s " +
                     "JOIN DOCTOR_SURGERY ds ON s.id = ds.surgery_id " +
                     "WHERE ds.doctor_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, doctor_Id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Surgery surgery = new Surgery(
                            rs.getInt("id"),
                            JDBCDateUtils.parseLocalDate(rs.getString("date")),
                            Type_of_surgery.valueOf(rs.getString("type")),
                            rs.getDouble("price"),
                            Turn.valueOf(rs.getString("turn")),
                            patient,
                            new ArrayList<>(),
                            new ArrayList<>()
                    );

                    list.add(surgery);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listSurgeriesByDoctor.");
            e.printStackTrace();
        }

        return list;
    }
	
	
	@Override
    public List<Surgery> listSurgeriesByPatient(int patientId) {
        List<Surgery> list = new ArrayList<>();

        String sql = "SELECT * FROM Surgery WHERE patient_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, patientId);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Patient patient = getPatientById(rs.getInt("patient_id"));

                    Surgery surgery = new Surgery(
                            rs.getInt("id"),
                            JDBCDateUtils.parseLocalDate(rs.getString("date")),
                            Type_of_surgery.valueOf(rs.getString("type")),
                            rs.getDouble("price"),
                            Turn.valueOf(rs.getString("turn")),
                            patient,
                            new ArrayList<>(),
                            new ArrayList<>()
                    );

                    list.add(surgery);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listSurgeriesByPatient.");
            e.printStackTrace();
        }

        return list;
    }

	@Override
	public void addDoctorToSurgery(int doctorId, int surgeryId) {
	    String sql = "INSERT INTO DOCTOR_SURGERY (doctor_id, surgery_id) VALUES (?, ?)";

	    try (PreparedStatement p = c.prepareStatement(sql)) {
	        p.setInt(1, doctorId);
	        p.setInt(2, surgeryId);

	        p.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Database error during addDoctorToSurgery.");
	        e.printStackTrace();
	    }
	}
	@Override
	public void addEquipmentToSurgery(int equipmentId, int surgeryId) {
	    String sql = "INSERT INTO SURGERY_EQUIPMENT (surgery_id, equipment_id) VALUES (?, ?)";

	    try (PreparedStatement p = c.prepareStatement(sql)) {
	        p.setInt(1, surgeryId);
	        p.setInt(2, equipmentId);

	        p.executeUpdate();

	    } catch (SQLException e) {
	        System.out.println("Database error during addEquipmentToSurgery.");
	        e.printStackTrace();
	    }
	}

	
}
