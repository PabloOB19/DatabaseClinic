package Main;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Utils {
	
	 public static byte[] loadImage(String path) throws Exception {
	        return Files.readAllBytes(Paths.get(path));
	    }
	 
	 public static void printMainMenu() {
			System.out.println("\nOPTIONS:");
			System.out.println(" 1-LOG IN ");
			System.out.println(" 2-REGISTER");
			System.out.println(" 3-FORGOT PASSWORD?");
			System.out.println(" 0-Exit");
		}

	 public static void ImpresionRoles() {
			System.out.println("1-Administrator");
			System.out.println("2-Doctor");
			System.out.println("3-Patient");
			
		}
		
		public static void ImpresionAdmin() {
			 System.out.println("Hello again, what do you want to do?");
			 System.out.println("1- Add");
			 System.out.println("2- Update");
			 System.out.println("3- Delete");
			 System.out.println("4- List");
			 System.out.println("5- Get");
			 System.out.println("6- Assign");
			 System.out.println("7- Exit");
			 
		}
		
		 
		public static void ImpresionEntity() {
			 System.out.println("1- Patient");
			 System.out.println("2- Doctor");
			 System.out.println("3- Appointment");
			 System.out.println("4- Surgery");
			 System.out.println("5- Equipment");

	}
		public static void printListMenu() {
	        System.out.println("1- List all patients");
	        System.out.println("2- List all doctors");
	        System.out.println("3- List all appointments");
	        System.out.println("4- List all surgeries");
	        System.out.println("5- List all equipment");
	        System.out.println("6- List doctors by specialty");
	        System.out.println("7- List doctors by surgery");
	        System.out.println("8- List doctors by appointment");
	        System.out.println("9- List equipment by surgery");
	        System.out.println("10- List equipment by category");
	    }

		public static void printGetMenu() {
	        System.out.println("1- Get doctor by ID");
	        System.out.println("2- Get patient by ID");
	        System.out.println("3- Get equipment by ID");
	    }

		public static void printAssignMenu() {
	        System.out.println("1- Add doctor to surgery");
	        System.out.println("2- Add equipment to surgery");
	    }

		public static void printDoctorMenu() {
	        System.out.println("Doctor menu:");
	        System.out.println("1- Add");
	        System.out.println("2- List");
	        System.out.println("3- Get");
	        System.out.println("4- Exit");
	    }

		public static void printDoctorAddMenu() {
	        System.out.println("1- Add equipment to surgery");
	    }

		public static void printDoctorListMenu() {
	        System.out.println("1- List surgeries by doctor");
	        System.out.println("2- List appointments by doctor");
	        System.out.println("3- List doctors by specialty");
	        System.out.println("4- List doctors by surgery");
	        System.out.println("5- List doctors by appointment");
	        System.out.println("6- List equipment by surgery");
	        System.out.println("7- List equipment by category");
	    }

		public static void printPatientMenu() {
	        System.out.println("Patient menu:");
	        System.out.println("1- List");
	        System.out.println("2- Get");
	        System.out.println("3- Exit");
	    }

		public static void printPatientListMenu() {
	        System.out.println("1- List my surgeries");
	        System.out.println("2- List my appointments");
	        System.out.println("3- List doctors by specialty");
	    }

		public static void printPatientGetMenu() {
	        System.out.println("1- Get my surgery by ID");
	        System.out.println("2- Get my patient data");
	    }

		public static void printList(List<?> list, String emptyMessage) {
	        if (list == null || list.isEmpty()) {
	            System.out.println(emptyMessage);
	            return;
	        }

	        for (Object element : list) {
	            System.out.println(element);
	        }
	    }

		public static void printObject(Object object, String notFoundMessage) {
	        if (object == null) {
	            System.out.println(notFoundMessage);
	            return;
	        }

	        System.out.println(object);
	    }

}
