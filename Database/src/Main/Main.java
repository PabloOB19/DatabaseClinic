package Main;

import java.sql.Connection;

import JDBC.*;
import JPA.JPAUser;
import POJOS.Role;
import POJOS.User;
import ifaces.*;
import xml.XmlManagerImplement;

public class Main {
	private static final String ROLE_ADMIN = "Admin";
	private static final String ROLE_DOCTOR = "Doctor";
	private static final String ROLE_PATIENT = "Patient";
	private static final String ADMIN_CODE = "1234abc";

	private static JDBCDoctorManager doctorManager;
	private static JDBCPatientManager patientManager;
	private static JDBCAppointmentManager appointmentManager;
	private static JDBCSurgeryManager surgeryManager;
	private static JDBCEquipmentManager equipmentManager;
	private static JPAUser userManager;
	private static XmlManager xmlManager;
	
		public static void main(String[] args) {
	    	
			JDBCConnectionManager connectionManager = new JDBCConnectionManager();
			Connection connection = connectionManager.manager();

			try {
				initializeManagers(connection);

				boolean appRunning = true;
				System.out.println("\n\n╔══════════════════════════════╗");
				System.out.println("║            WELCOME           ║");
				System.out.println("╚══════════════════════════════╝");
				while (appRunning) {
					    Utils.printMainMenu();
					    int option = InputOutput.askInt("\n Choose an option: ");
					    
					    switch(option) {
					    	 case 1:
					    		    login();
					    		    break;
					    	 case 2:
					    		    register();
					    		    break;

					    	 case 3:
					    		    forgotPassword();
					    		    break;

					    	 case 0:
					    		    appRunning = false;
					    		    System.out.println("Exit");
					    		    break;
					    	 default:
					    		    System.out.println("Invalid option.");
					    		    break;
					     }}} finally {
				if (userManager != null) {
					userManager.close();
				}
				connectionManager.closeConnection();
				}}

	private static void initializeManagers(Connection connection) {
		doctorManager = new JDBCDoctorManager(connection);
		patientManager = new JDBCPatientManager(connection);
		appointmentManager = new JDBCAppointmentManager(connection);
		surgeryManager = new JDBCSurgeryManager(connection);
		equipmentManager = new JDBCEquipmentManager(connection);
		xmlManager = new XmlManagerImplement();
		userManager = new JPAUser();
	}


	private static void login() {
		String username = InputOutput.askText("Enter your username:");
		String password = InputOutput.askPassword("Enter your password:");
		User loggedUser = userManager.login(username, password);

		if (loggedUser == null) {
			System.out.println("Incorrect username or password.");
		} else if (loggedUser.getRole() == null) {
			System.out.println("This user does not have a role assigned.");
		} else {
			openMenuByRole(loggedUser);
		}
	}

	private static void openMenuByRole(User loggedUser) {
		String roleName = loggedUser.getRole().getName();

		switch (roleName) {
			case ROLE_ADMIN:
				adminMenu(loggedUser);
				break;
			case ROLE_DOCTOR:
				doctorMenu(loggedUser);
				break;
			case ROLE_PATIENT:
				patientMenu(loggedUser);
				break;
			default:
				System.out.println("Unknown role: " + roleName);
				break;
		}
	}

	private static void register() {
		String roleName = askRoleNameForRegister();

		if (roleName == null) {
			return;
		}

		String username = InputOutput.askText("Enter username:");
		String email = InputOutput.askEmail("Enter email:");
		String password = InputOutput.askPassword("Enter password:");

		User newUser = new User(username, password, email);
		userManager.register(newUser);

		User savedUser = userManager.getUser(username, email);

		if (savedUser == null) {
			System.out.println("User could not be registered. Username or email may already exist.");
			return;
		}

		Role role = userManager.getRole(roleName);

		if (role == null) {
			System.out.println("Role not found.");
			return;
		}

		userManager.assignRole(savedUser, role);

		System.out.println("User registered successfully as " + roleName + ".");
	}

	private static String askRoleNameForRegister() {
		Utils.ImpresionRoles();
		int roleOption = InputOutput.askInt("Choose role:");

		switch (roleOption) {
			case 1:
				String adminCode = InputOutput.askPassword("Enter admin creation code:");

				if (!adminCode.equals(ADMIN_CODE)) {
					System.out.println("Incorrect admin code. Admin user was not created.");
					return null;
				}

				return ROLE_ADMIN;
			case 2:
				return ROLE_DOCTOR;
			case 3:
				return ROLE_PATIENT;
			default:
				System.out.println("Invalid role option.");
				return null;
		}
	}

	private static void forgotPassword() {
		String username = InputOutput.askText("Enter your username:");
		String email = InputOutput.askEmail("Enter your email:");

		User user = userManager.getUser(username, email);

		if (user == null) {
			System.out.println("No user found with that username and email.");
			return;
		}

		String password = InputOutput.askPassword("Enter your new password:");
		userManager.updatePassword(user, password);
		System.out.println("Password updated successfully.");
	}
		
	private static void adminMenu(User loggedUser) {
	    AdminMenu adminMenu = new AdminMenu(doctorManager, patientManager, appointmentManager, surgeryManager,
	    		equipmentManager, userManager, xmlManager, loggedUser);
	    adminMenu.run();
	}

	private static void doctorMenu(User loggedUser) {
	    DoctorMenu doctorMenu = new DoctorMenu(loggedUser, doctorManager, patientManager, appointmentManager, surgeryManager, equipmentManager);
	    doctorMenu.run();
	}

	private static void patientMenu(User loggedUser) {
	    PatientMenu patientMenu = new PatientMenu(loggedUser, doctorManager, patientManager, appointmentManager, surgeryManager);
	    patientMenu.run();
	}

		

		

	}
