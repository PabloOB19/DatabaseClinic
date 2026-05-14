package Main;

import java.sql.Connection;
import java.time.LocalDate;

import Enums.*;
import JDBC.*;
import JPA.JPAUser;
import ifaces.*;
import xml.XmlManagerImplement;
import POJOS.*;

public class Main {
	private static JDBCDoctorManager doctorManager;
	private static JDBCPatientManager patientManager;
	private static JDBCAppointmentManager appointmentManager;
	private static JDBCSurgeryManager surgeryManager;
	private static JDBCEquipmentManager equipmentManager;
	private static JPAUser userManager;
	private static XmlManager xmlManager;
	
		public static void main(String[] args) {
	    	
			JDBCConnectionManager connectionManager = new JDBCConnectionManager();
			Connection c = connectionManager.manager();

			try {
				doctorManager = new JDBCDoctorManager(c);
				patientManager = new JDBCPatientManager(c);
				appointmentManager = new JDBCAppointmentManager(c);
				surgeryManager = new JDBCSurgeryManager(c);
				equipmentManager = new JDBCEquipmentManager(c);
				xmlManager = new XmlManagerImplement();
				userManager = new JPAUser();
				String username, password, email;

				boolean run = true;
				System.out.println("\n\n╔══════════════════════════════╗");
				System.out.println("║            WELCOME           ║");
				System.out.println("╚══════════════════════════════╝");
				while (run) {
					 System.out.println("\nOPTIONS:");
					    System.out.println(" 1-LOG IN ");
					    System.out.println(" 2-REGISTER");
					    System.out.println(" 3-FORGOT PASSWORD?");
					    System.out.println(" 0-Exit");
					    int op = InputOutput.askInt("\n Choose an option: ");
					    
					    switch(op) {
					    	 case 1:
					    		    username = InputOutput.askUsername("Introduce your username:");
					    		    password = InputOutput.askPassword("Introduce your password:");
					    		    User loggedUser = userManager.login(username, password);
					    		    if (loggedUser == null) {
					    		        System.out.println("Incorrect username or password.");
					    		    } else if (loggedUser.getRole() == null) {
					    		        System.out.println("This user does not have a role assigned.");
					    		    } else {
					    		        String roleName = loggedUser.getRole().getName();
	
					    		        switch (roleName) {
					    		            case "Admin":
					    		                adminMenu();
					    		                break;
	
					    		            case "Doctor":
					    		                doctorMenu();
					    		                break;
	
					    		            case "Patient":
					    		                patientMenu();
					    		                break;
	
					    		            default:
					    		                System.out.println("Unknown role: " + roleName);
					    		                break;
					    		        }
					    		    }
					    		    break;
					    	 case 2:
					    		   Utils.ImpresionRoles();	
					    		   int roleOption = InputOutput.askInt("Choose role:");					  
					    		   String roleName = null;
					    		    switch (roleOption) {
					    		        case 1:
					    		            String adminCode = InputOutput.askPassword("Enter admin creation code:");
	
					    		            if (!adminCode.equals("1234abc")) {
					    		                System.out.println("Incorrect admin code. Admin user was not created.");
					    		                break;
					    		            }
	
					    		            roleName = "Admin";
					    		            break;
	
					    		        case 2:
					    		            roleName = "Doctor";
					    		            break;
	
					    		        case 3:
					    		            roleName = "Patient";
					    		            break;
	
					    		        default:
					    		            System.out.println("Invalid role option.");
					    		            break;
					    		    }
	
					    		    if (roleName == null) {
					    		        break;
					    		    }
	
					    		     username = InputOutput.askUsername("Introduce username:");
					    		     email = InputOutput.askEmail("Introduce email:");
					    		     password = InputOutput.askPassword("Introduce password:");
	
					    		    User newUser = new User(username, password, email);
					    		    userManager.register(newUser);
	
					    		    User savedUser = userManager.getUser(username, email);
	
					    		    if (savedUser == null) {
					    		        System.out.println("User could not be registered. Username or email may already exist.");
					    		        break;
					    		    }
	
					    		    Role role = userManager.getRole(roleName);
	
					    		    if (role == null) {
					    		        System.out.println("Role not found.");
					    		        break;
					    		    }
	
					    		    userManager.assignRole(savedUser, role);
	
					    		    System.out.println("User registered successfully as " + roleName + ".");
					    		    break;

					    	 case 3:
					    		    username = InputOutput.askUsername("Introduce your username:");
					    		    email = InputOutput.askEmail("Introduce your email:");

					    		    User user = userManager.getUser(username, email);

					    		    if (user == null) {
					    		        System.out.println("No user found with that username and email.");
					    		        break;
					    		    }

					    		    password = InputOutput.askPassword("Introduce your new password:");
					    		    userManager.updatePassword(user, password);
					    		    System.out.println("Password updated successfully.");
					    		    break;

					    	 case 0:
					    		    run = false;
					    		    System.out.println("Exit");
					    		    break;
					     }}} finally {
				if (userManager != null) {
					userManager.close();
				}
				connectionManager.closeConnection();
				}}
		
	private static void adminMenu() {
	    boolean adminRun = true;

	    while (adminRun) {
	    	Utils.ImpresionAdmin();
		    int op = InputOutput.askInt("\n Choose an option: ");

		    switch (op) {
		    	case 1: 
		    		addMenu();
		    		break;
		    	case 6:
		    		adminRun = false;
		    		break;
		    	default:
		    		System.out.println("Invalid option.");
		    		break;
		    }
	    }
	}

	private static void addMenu() {
		Utils.ImpresionEntity();
		int op = InputOutput.askInt("\n Choose an option: ");
		switch (op) {
    		case 1: 
    			addPatientByScreen();
    			break;
    		case 2: 
    			addDoctorByScreen();
    			break;
	    		case 3: 
	    			addAppointmentByScreen();
	    			break;
	    		case 4:
	    			addSurgeryByScreen();
	    			break;
	    		case 5:
	    			addEquipmentByScreen();
	    			break;
	    		default:
	    			System.out.println("Invalid entity option.");
	    			break;
		}
	}

	private static void doctorMenu() {
	    System.out.println("Doctor menu");
	}

	private static void patientMenu() {
	    System.out.println("Patient menu");
	}

	private static void addDoctorByScreen() {
	    String name = InputOutput.askString("Introduce doctor's name:");
	    String surname = InputOutput.askString("Introduce doctor's surname:");
	    String email = InputOutput.askEmail("Introduce doctor's email:");
	    String specialty = InputOutput.askString("Introduce doctor's specialty:");
	    LocalDate dob = InputOutput.askDate("Introduce doctor's date of birth:");
	    Sex sex = InputOutput.askSex();
	    if (sex == null) {
	    	return;
	    }
	    byte[] photo = null;
	    try {
	        String photoPath = InputOutput.askUsername("Introduce photo path:");
	        photo = Utils.loadImage(photoPath);
	    } catch (Exception e) {
	        System.out.println("Could not load image.");
	        return;
	    }
	    Doctor doctor = new Doctor(0, name, surname, photo, sex, email, specialty, dob, null, null);
	    doctorManager.insertDoctor(doctor);
	    System.out.println("Doctor added successfully with ID: " + doctor.getId());
	}
	
	private static void addPatientByScreen() {
	    String name = InputOutput.askString("Introduce patient's name:");
	    String surname = InputOutput.askString("Introduce patient's surname:");
	    String email = InputOutput.askEmail("Introduce patient's email:");
	    LocalDate dob = InputOutput.askDate("Introduce patient's date of birth:");
	    int height = InputOutput.askInt("Introduce patient's height:");
	    double weightDouble = InputOutput.askDouble("Introduce patient's weight:");
	    float weight = (float) weightDouble;
	    String info = InputOutput.askString("Introduce patient's info:");
	    Sex sex = InputOutput.askSex();
	    if (sex == null) {
	    	return;
	    }
	    byte[] photo = null;
	    try {
	        String photoPath = InputOutput.askUsername("Introduce photo path:");
	        photo = Utils.loadImage(photoPath);
	    } catch (Exception e) {
	        System.out.println("Could not load image.");
	        return;
	    }
	    Patient patient = new Patient(0, sex, name, surname, dob, height, weight, photo, info, email, null, null);
	    patientManager.insertPatient(patient);
	    System.out.println("Patient added successfully with ID: " + patient.getId());
	}

	private static void addAppointmentByScreen() {
	    Type_of_appointment type = InputOutput.askAppointmentType();
	    if (type == null) {
	    	return;
	    }
	    LocalDate date = InputOutput.askDate("Introduce appointment date:");
	    double price = InputOutput.askDouble("Introduce appointment price:");
	    Turn turn = InputOutput.askTurn();
	    if (turn == null) {
	    	return;
	    }
	    int doctorId = InputOutput.askInt("Introduce doctor ID:");
	    Doctor doctor = doctorManager.getDoctorById(doctorId);

	    if (doctor == null) {
	        System.out.println("Doctor not found.");
	        return;
	    }

	    int patientId = InputOutput.askInt("Introduce patient ID:");
	    Patient patient = patientManager.getPatientById(patientId);

	    if (patient == null) {
	        System.out.println("Patient not found.");
	        return;
	    }
	    Appointment appointment = new Appointment(0, type, date, turn, price, doctor,patient );
	    appointmentManager.insertAppointment(appointment);
	    System.out.println("Appointment added successfully with ID: " + appointment.getId());
	}

	private static void addSurgeryByScreen() {
	    Type_of_surgery type = InputOutput.askSurgeryType();
	    if (type == null) {
	    	return;
	    }
	    LocalDate date = InputOutput.askDate("Introduce surgery date:");
	    double price = InputOutput.askDouble("Introduce surgery price:");
	    Turn turn = InputOutput.askTurn();
	    if (turn == null) {
	    	return;
	    }
	    int patientId = InputOutput.askInt("Introduce patient ID:");
	    Patient patient = patientManager.getPatientById(patientId);
	    if (patient == null) {
	        System.out.println("Patient not found.");
	        return;
	    }
	    Surgery surgery = new Surgery(0, date, type, price, turn, patient, null, null);
	    surgeryManager.insertSurgery(surgery);
	    System.out.println("Surgery added successfully with ID: " + surgery.getId());
	}

	private static void addEquipmentByScreen() {
	    String name = InputOutput.askString("Introduce equipment name:");
	    Category category = InputOutput.askCategory();
	    if (category == null) {
	    	return;
	    }

	    int quantity = InputOutput.askInt("Introduce equipment quantity:");
	    double price = InputOutput.askDouble("Introduce equipment price:");
	    LocalDate expirationDate = InputOutput.askDate("Introduce equipment expiration date:");

	    Equipment equipment = new Equipment(0, name, category, quantity, price, expirationDate, null);
	    equipmentManager.insertEquipment(equipment);
	    System.out.println("Equipment added successfully with ID: " + equipment.getId());
	}
		

		

	}
