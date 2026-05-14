package Main;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import Enums.Category;
import Enums.Sex;
import Enums.Turn;
import Enums.Type_of_appointment;
import Enums.Type_of_surgery;


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

	public static boolean askYesNo(String mensaje) {
		while (true) {
			System.out.println(mensaje + " (y/n)");
			String input = sc.nextLine().trim().toLowerCase();

			if (input.equals("y") || input.equals("yes") || input.equals("s") || input.equals("si")) {
				return true;
			} else if (input.equals("n") || input.equals("no")) {
				return false;
			} else {
				System.out.println("Please answer yes or no.");
			}
		}
	}

	
	public static Sex askSex() {
	    System.out.println("Choose sex:");
	    System.out.println("1- Male");
	    System.out.println("2- Female");

	    int sexOption = InputOutput.askInt("Choose sex:");

	    switch (sexOption) {
	        case 1:
	            return Sex.MALE;
	        case 2:
	            return Sex.FEMALE;
	        default:
	            System.out.println("Invalid sex option.");
	            return null;
	    }
	}

	public static Turn askTurn() {
	    System.out.println("Choose turn:");
	    System.out.println("1- Early morning");
	    System.out.println("2- Late morning");
	    System.out.println("3- Early afternoon");
	    System.out.println("4- Evening");

	    int turnOption = InputOutput.askInt("Choose turn:");

	    switch (turnOption) {
	        case 1:
	            return Turn.EARLY_MORNING;
	        case 2:
	            return Turn.LATE_MORNING;
	        case 3:
	            return Turn.EARLY_AFTERNOON;
	        case 4:
	            return Turn.EVENING;
	        default:
	            System.out.println("Invalid turn.");
	            return null;
	    }
	}

		public static Type_of_appointment askAppointmentType() {
		    System.out.println("Choose appointment type:");
	    System.out.println("1- General checkup");
	    System.out.println("2- Specialist visit");
	    System.out.println("3- Surgery");
	    System.out.println("4- Follow up");
	    System.out.println("5- Emergency");

	    int typeOption = InputOutput.askInt("Choose type:");

	    switch (typeOption) {
	        case 1:
	            return Type_of_appointment.GENERAL_CHECKUP;
	        case 2:
	            return Type_of_appointment.SPECIALIST_VISIT;
	        case 3:
	            return Type_of_appointment.SURGERY;
	        case 4:
	            return Type_of_appointment.FOLLOW_UP;
	        case 5:
	            return Type_of_appointment.EMERGENCY;
	        default:
	            System.out.println("Invalid appointment type.");
	            return null;
		    }
		}

		public static Type_of_surgery askSurgeryType() {
		    System.out.println("Choose surgery type:");
		    System.out.println("1- Rhinoplasty");
		    System.out.println("2- Blepharoplasty");
		    System.out.println("3- Facelift");
		    System.out.println("4- Neck lift");
		    System.out.println("5- Brow lift");
		    System.out.println("6- Otoplasty");
		    System.out.println("7- Liposuction");
		    System.out.println("8- Abdominoplasty");
		    System.out.println("9- Breast augmentation");
		    System.out.println("10- Breast reduction");
		    System.out.println("11- Breast lift");
		    System.out.println("12- Gynecomastia surgery");

		    int typeOption = InputOutput.askInt("Choose type:");

		    switch (typeOption) {
		        case 1:
		            return Type_of_surgery.RHINOPLASTY;
		        case 2:
		            return Type_of_surgery.BLEPHAROPLOASTY;
		        case 3:
		            return Type_of_surgery.FACELIFT;
		        case 4:
		            return Type_of_surgery.NECK_LIFT;
		        case 5:
		            return Type_of_surgery.BROW_LIFT;
		        case 6:
		            return Type_of_surgery.OTOPLASTY;
		        case 7:
		            return Type_of_surgery.LIPOSUCTION;
		        case 8:
		            return Type_of_surgery.ABDOMINOPLASTY;
		        case 9:
		            return Type_of_surgery.BREAST_AUGMENTATION;
		        case 10:
		            return Type_of_surgery.BREAST_REDUCTION;
		        case 11:
		            return Type_of_surgery.BREAST_LIFT;
		        case 12:
		            return Type_of_surgery.GYNECOMASTIA_SURGERY;
		        default:
		            System.out.println("Invalid surgery type.");
		            return null;
		    }
		}

		public static Category askCategory() {
		    System.out.println("Choose equipment category:");
		    System.out.println("1- Machine");
		    System.out.println("2- Instrument");
		    System.out.println("3- Supply");

		    int categoryOption = InputOutput.askInt("Choose category:");

		    switch (categoryOption) {
		        case 1:
		            return Category.MACHINE;
		        case 2:
		            return Category.INSTRUMENT;
		        case 3:
		            return Category.SUPPLY;
		        default:
		            System.out.println("Invalid category.");
		            return null;
		    }
		}

		public static byte[] askPhoto() {
		    try {
		        String photoPath = InputOutput.askUsername("Introduce photo path:");
		        return Utils.loadImage(photoPath);
		    } catch (Exception e) {
		        System.out.println("Could not load image.");
		        return null;
		    }
		}
	}
