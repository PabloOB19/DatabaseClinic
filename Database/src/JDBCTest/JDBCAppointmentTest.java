package JDBCTest;

import JDBC.JDBCAppointmentManager;
import POJOS.Appointment;
import POJOS.Doctor;
import POJOS.Patient;
import POJOS.Surgery;
import Enums.Payment_status;
import Enums.Sex;
import Enums.Turn;
import Enums.Type_of_appointment;
import Exceptions.InvalidIdentificator;
import Exceptions.InvalidPrice;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JDBCAppointmentTest {

    @Test
    void correctConstructor() throws InvalidIdentificator, InvalidPrice, NullPointerException{
    	try
    	{	
    		LocalDate date = LocalDate.of(2004,4,11);
    		Patient patient = new Patient("Carlos","García","12345678A",date,Sex.MALE,175,725,"photo.jpg",600123456,"carlos.garcia@email.com","Calle Mayor 12",
    			    "Credit Card","Sin antecedentes relevantes","Paciente alérgico a la penicilina"); 
    		Doctor doctor = new Doctor(12345,"Laura","Martínez",Sex.FEMALE,date,600123456,"laura.martinez@email.com","Cardiología","photo.jpg",3500.0,12);
    		Appointment appointment = new Appointment(19,Type_of_appointment.EMERGENCY,date,Turn.EARLY_AFTERNOON,120,patient,doctor,Payment_status.PAYED);
    		
            assertEquals(19,appointment.getIdentificator());
            assertEquals(Type_of_appointment.EMERGENCY,appointment.getType());
            assertEquals(Turn.EARLY_AFTERNOON,appointment.getTurn());
            assertEquals(120,appointment.getPrice());
            assertEquals(patient,appointment.getPatient());
            assertEquals(doctor,appointment.getDoctor());
            assertEquals(Payment_status.PAYED,appointment.getPayment_status());
        }
        catch (NullPointerException nullPointerException)
        {
            nullPointerException.getMessage();
            fail("No se debería de lanzar una excepción de tipo CantidadDiasErronea");
        }
        catch (InvalidPrice invalidPrice)
        {
            invalidPrice.getMessage();
            fail("No se debería de lanzar una excepción de tipo IllegalArgumentException");
        }
    	catch (InvalidIdentificator invalidIdentificator)
        {
            invalidIdentificator.getMessage();
            fail("No se debería de lanzar una excepción de tipo IllegalArgumentException");
        }	
   }
    
}