package Main;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class InputOutput {
	
	private static final Scanner sc = new Scanner(System.in);
	
	public static String askString(String mensaje) {
		while (true) {
		    System.out.println(mensaje);
		    String input = sc.nextLine().trim();

		    if (input.isEmpty()) {
		        System.out.println("This field cannot be empty.");
		    } else if (input.matches(".*\\d.*")) {
		        System.out.println("Please enter text without numbers.");
		    } else {
		        return input;
		    }
		}
	}

	public static String askUsername(String mensaje) {
		while (true) {
			System.out.println(mensaje);
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				System.out.println("Username cannot be empty.");
			} else {
				return input;
			}
		}
	}
	
	public static int askInt(String mensaje) {
		while (true) {
		    System.out.println(mensaje);
		    String input = sc.nextLine().trim();

		    try {
		        return Integer.parseInt(input);
		    } catch (NumberFormatException e) {
		        System.out.println("Please enter a valid integer number.");
		    }
		}
	}
	
	public static double askDouble(String mensaje) {
		while (true) {
		    System.out.println(mensaje);
		    String input = sc.nextLine().trim();

		    try {
		        return Double.parseDouble(input);
		    } catch (NumberFormatException e) {
		        System.out.println("Please enter a valid decimal number.");
		    }
		}
	}


	public static LocalDate askDate(String mensaje) {
		while (true) {
		    System.out.println(mensaje + " (yyyy-MM-dd)");
		    String input = sc.nextLine().trim();

		    try {
		        return LocalDate.parse(input);
		    } catch (DateTimeParseException e) {
		        System.out.println("Please enter a valid date with format yyyy-MM-dd.");
		    }
		}
	}
	
	public static String askPassword(String mensaje) {
		while (true) {
		    System.out.println(mensaje);
		    String input = sc.nextLine().trim();

		    if (input.isEmpty()) {
		        System.out.println("Password cannot be empty.");
		    } else {
		        return input;
		    }
		}
	}

	public static String askEmail(String mensaje) {
		while (true) {
			System.out.println(mensaje);
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				System.out.println("Email cannot be empty.");
			} else if (!input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				System.out.println("Please enter a valid email address.");
			} else {
				return input;
			}
		}
	}

	
	public static void ImpresionRoles() {
		System.out.println("1-Administrator");
		System.out.println("2-Doctor");
		System.out.println("3-Patient");
		
	}
	
	public static void ImpresionAdmin() {
		 System.out.println("Hello again, what do you want to do?");
		 System.out.println("1- Add");
		 System.out.println("2- Upload");
		 System.out.println("3- Delete");
		 System.out.println("4- List");
		 System.out.println("5- Get");
		 System.out.println("6- Exit");
		 
	}
	
	 
	public static void ImpresionEntity() {
		 System.out.println("1- Patient");
		 System.out.println("2- Doctor");
		 System.out.println("3- Appointment");
		 System.out.println("4- Surgery");
		 System.out.println("5- Equipment");

}
	
	
}
