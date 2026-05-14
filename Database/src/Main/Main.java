package Main;

import java.sql.Connection;

import JDBC.*;
import JPA.JPAUser;
import POJOS.Role;
import POJOS.User;
import ifaces.*;
import xml.XmlManagerImplement;

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
	    AdminMenu adminMenu = new AdminMenu(doctorManager, patientManager, appointmentManager, surgeryManager, equipmentManager);
	    adminMenu.run();
	}

	private static void doctorMenu() {
	    DoctorMenu doctorMenu = new DoctorMenu(doctorManager, patientManager, appointmentManager, surgeryManager, equipmentManager);
	    doctorMenu.run();
	}

	private static void patientMenu() {
	    System.out.println("Patient menu");
	}

		

		

	}
