package Main;

import java.sql.Connection;
import Enums.*;
import java.util.*;
import JDBC.*;
import JPA.JPAUser;
import ifaces.*;
import xml.XmlManagerImplement;
import POJOS.*;
import Utils.Utils;


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

		doctorManager = new JDBCDoctorManager(c);
		patientManager = new JDBCPatientManager(c);
		appointmentManager = new JDBCAppointmentManager(c);
		surgeryManager = new JDBCSurgeryManager(c);
		equipmentManager = new JDBCEquipmentManager(c);
		xmlManager = new XmlManagerImplement();
		userManager = new JPAUser();
		String username, password, email;

		boolean run = true;
		System.out.println("\n\n╔══════════════════════════╗");
		System.out.println("║            WELCOME           ║");
		System.out.println("╚══════════════════════════════╝");
		while (run) {
			 System.out.println("\nOPTIONS:");
			    System.out.println(" 1-LOGIN ");
			    System.out.println(" 2-REGISTER");
			    System.out.println(" 3-FORGOT PASSWORD?");
			    System.out.println(" 0-Exit");
			    int op = InputOutput.askInt("\n Choose an option: ");
			    
			    switch(op) {
			    	 case 1:
			    		    username = InputOutput.askString("Introduce your username:");
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
			    		   InputOutput.Impresion();	
			    		   int roleOption = InputOutput.askInt("Choose role:");
			    		   
			    		   String roleName = null;

			    		    switch (roleOption) {
			    		        case 1:
			    		            String adminCode = InputOutput.askPassword("Enter admin creation password:");

			    		            if (!adminCode.equals("1234abc")) {
			    		                System.out.println("Incorrect admin password. Admin user was not created.");
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

			    		     username = InputOutput.askString("Introduce username:");
			    		     email = InputOutput.askString("Introduce email:");
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

			    		    
			    	 }
			     
			    
			    }
			    
			
	}
		
	private static void adminMenu() {
	    System.out.println("Admin menu");
	}

	private static void doctorMenu() {
	    System.out.println("Doctor menu");
	}

	private static void patientMenu() {
	    System.out.println("Patient menu");
	}

	}