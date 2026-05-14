package Main;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class InputOutput {
	
	
	public static String askString(String mensaje) {
		try (Scanner sc = new Scanner(System.in)) {
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
	}
	
	public static int askInt(String mensaje) {
		try (Scanner sc = new Scanner(System.in)) {
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
	}
	
	public static double askDouble(String mensaje) {
		try (Scanner sc = new Scanner(System.in)) {
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
	}


	public static LocalDate askDate(String mensaje) {
		try (Scanner sc = new Scanner(System.in)) {
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
	}


}
