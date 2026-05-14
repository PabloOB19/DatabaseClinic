package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import POJOS.Doctor;
import ifaces.DoctorManager;

public class JDBCDoctorManager implements DoctorManager {

    private Connection c;
    
    public JDBCDoctorManager(Connection c) {
    	this.c = c;
    }
    
    @Override
    public boolean insertDoctor(Doctor doctor) {
        String sql = "INSERT INTO Doctor (name, surname, email, sex, dob, photo, specialty) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, doctor.getName());
            p.setString(2, doctor.getSurname());
            p.setString(3, doctor.getEmail());
            p.setString(4, doctor.getSex().name());
            p.setString(5, doctor.getDob().toString());
            p.setBytes(6, doctor.getPhoto());
            p.setString(7, doctor.getSpecialty());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating doctor failed, no rows affected.");
            }

            try (ResultSet generatedKeys = p.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    doctor.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating doctor failed, no ID obtained.");
                }
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Database error during insertDoctor.");
            return false;
        }
    }

    @Override
    public Doctor getDoctorById(int id) {
        Doctor result = null;

        String sql = "SELECT * FROM Doctor WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);

            try (ResultSet rs = p.executeQuery()) {
                if (rs.next()) {
                    result = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBytes("photo"),
                            Enums.Sex.valueOf(rs.getString("sex")),
                            rs.getString("email"),
                            rs.getString("specialty"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            null,
                            null
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during getDoctorById.");
        }

        return result;
    }

    @Override
    public List<Doctor> listAllDoctors() {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT * FROM Doctor";

        try (PreparedStatement p = c.prepareStatement(sql);
             ResultSet rs = p.executeQuery()) {

            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getBytes("photo"),
                        Enums.Sex.valueOf(rs.getString("sex")),
                        rs.getString("email"),
                        rs.getString("specialty"),
                        JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                        null,
                        null
                );

                list.add(doctor);
            }

        } catch (SQLException e) {
            System.out.println("Database error during listAllDoctors.");
        }

        return list;
    }

    @Override
    public boolean updateDoctor(Doctor doctor) {
        String sql = "UPDATE Doctor SET name = ?, surname = ?, email = ?, sex = ?, dob = ?, photo = ?, specialty = ? WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, doctor.getName());
            p.setString(2, doctor.getSurname());
            p.setString(3, doctor.getEmail());
            p.setString(4, doctor.getSex().name());
            p.setString(5, doctor.getDob().toString());
            p.setBytes(6, doctor.getPhoto());
            p.setString(7, doctor.getSpecialty());
            p.setInt(8, doctor.getId());

            int affectedRows = p.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating doctor failed, no rows affected.");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Database error during updateDoctor.");
            return false;
        }
    }

   
    @Override
    public boolean deleteDoctor(int id) {
        String deleteDoctorSurgeriesSql = "DELETE FROM DOCTOR_SURGERY WHERE doctor_id = ?";
        String deleteAppointmentsSql = "DELETE FROM Appointment WHERE doctor_id = ?";
        String deleteDoctorSql = "DELETE FROM Doctor WHERE id = ?";

        try {
            c.setAutoCommit(false);

            try (PreparedStatement p = c.prepareStatement(deleteDoctorSurgeriesSql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deleteAppointmentsSql)) {
                p.setInt(1, id);
                p.executeUpdate();
            }

            try (PreparedStatement p = c.prepareStatement(deleteDoctorSql)) {
                p.setInt(1, id);
                int affectedRows = p.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Deleting doctor failed, no rows affected.");
                }
            }

            c.commit();
            return true;

        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException rollbackException) {
                System.out.println("Database rollback error during deleteDoctor.");
            }

            System.out.println("Database error during deleteDoctor.");
            return false;

        } finally {
            try {
                c.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Database error restoring auto-commit.");
            }
        }
    }


    @Override
    public boolean addDoctorToAppointment(int doctor_id, int appointment_id) {
        String sql = "UPDATE Appointment SET doctor_id = ? WHERE id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, doctor_id);
            p.setInt(2, appointment_id);

            int affectedRows = p.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.out.println("Database error during addDoctorToAppointment.");
            return false;
        }
    }

    @Override
    public List<Doctor> listDoctorsBySpecialty(String specialty) {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT * FROM Doctor WHERE specialty = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, specialty);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBytes("photo"),
                            Enums.Sex.valueOf(rs.getString("sex")),
                            rs.getString("email"),
                            rs.getString("specialty"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            null,
                            null
                    );

                    list.add(doctor);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listDoctorsBySpecialty.");
        }

        return list;
    }

    @Override
    public List<Doctor> listDoctorsBySurgery(int surgery_id) {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT d.* " +
                     "FROM Doctor d " +
                     "JOIN DOCTOR_SURGERY ds ON d.id = ds.doctor_id " +
                     "WHERE ds.surgery_id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, surgery_id);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBytes("photo"),
                            Enums.Sex.valueOf(rs.getString("sex")),
                            rs.getString("email"),
                            rs.getString("specialty"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            null,
                            null
                    );

                    list.add(doctor);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listDoctorsBySurgery.");
        }

        return list;
    }

    @Override
    public List<Doctor> listDoctorsByAppointment(int appointmentId) {
        List<Doctor> list = new ArrayList<>();

        String sql = "SELECT d.* " +
                     "FROM Doctor d " +
                     "JOIN Appointment a ON d.id = a.doctor_id " +
                     "WHERE a.id = ?";

        try (PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, appointmentId);

            try (ResultSet rs = p.executeQuery()) {
                while (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("surname"),
                            rs.getBytes("photo"),
                            Enums.Sex.valueOf(rs.getString("sex")),
                            rs.getString("email"),
                            rs.getString("specialty"),
                            JDBCDateUtils.parseLocalDate(rs.getString("dob")),
                            null,
                            null
                    );

                    list.add(doctor);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during listDoctorsByAppointment.");
        }

        return list;
    }

}
