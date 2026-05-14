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
			    	InputOutput.Impresion();
			    	 int op2 = InputOutput.askInt("\nChoose an option: ");
			    	 switch(op2) {
			    	 case 1:
			    		 
			    	 }
			    	 
			    	
			    }
			    
			    

	}
}}