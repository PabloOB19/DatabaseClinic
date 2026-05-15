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

	public static String askText(String mensaje) {
		while (true) {
			System.out.println(mensaje);
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				System.out.println("Cannot be empty.");
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

	public static int askPositiveInt(String mensaje) {
		while (true) {
			int input = askInt(mensaje);

			if (input > 0) {
				return input;
			}

			System.out.println("Please enter a positive integer number.");
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

	public static double askPositiveDouble(String mensaje) {
		while (true) {
			double input = askDouble(mensaje);

			if (input > 0) {
				return input;
			}

			System.out.println("Please enter a positive decimal number.");
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
	
	public static LocalDate askFutureDate(String mensaje) {
	    while (true) {
	        LocalDate date = askDate(mensaje);

	        if (date.isBefore(LocalDate.now())) {
	            System.out.println("Date cannot be in the past.");
	        } else {
	            return date;
	        }
	    }
	}

	public static LocalDate askPastDate(String mensaje) {
	    while (true) {
	        LocalDate date = askDate(mensaje);

	        if (date.isAfter(LocalDate.now())) {
	            System.out.println("Date cannot be in the future.");
	        } else {
	            return date;
	        }
	    }
	}


	public static String askOptionalString(String mensaje, String currentValue) {
		while (true) {
			System.out.println(mensaje + " (press Enter to keep current: " + currentValue + ")");
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				return currentValue;
			} else if (input.matches(".*\\d.*")) {
				System.out.println("Please enter text without numbers.");
			} else {
				return input;
			}
		}
	}
		public static String askOptionalText(String mensaje, String currentValue) {
			while (true) {
				System.out.println(mensaje + " (press Enter to keep current: " + currentValue + ")");
				String input = sc.nextLine().trim();

				if (input.isEmpty()) {
					return currentValue;
				} else {
					return input;
				}
			}
		}

	public static String askOptionalEmail(String mensaje, String currentValue) {
		while (true) {
			System.out.println(mensaje + " (press Enter to keep current: " + currentValue + ")");
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				return currentValue;
			} else if (!input.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
				System.out.println("Please enter a valid email address.");
			} else {
				return input;
			}
		}
	}

	public static LocalDate askOptionalDate(String mensaje, LocalDate currentValue) {
		while (true) {
			System.out.println(mensaje + " (yyyy-MM-dd, press Enter to keep current: " + currentValue + ")");
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				return currentValue;
			}

			try {
				return LocalDate.parse(input);
			} catch (DateTimeParseException e) {
				System.out.println("Please enter a valid date with format yyyy-MM-dd.");
			}
		}
	}
	public static LocalDate askOptionalFutureDate(String mensaje, LocalDate currentValue) {
	    while (true) {
	        LocalDate date = askOptionalDate(mensaje, currentValue);

	        if (date.isBefore(LocalDate.now())) {
	            System.out.println("Date cannot be in the past.");
	        } else {
	            return date;
	        }
	    }
	}

	public static LocalDate askOptionalPastDate(String mensaje, LocalDate currentValue) {
	    while (true) {
	        LocalDate date = askOptionalDate(mensaje, currentValue);

	        if (date.isAfter(LocalDate.now())) {
	            System.out.println("Date cannot be in the future.");
	        } else {
	            return date;
	        }
	    }
	}


	public static int askOptionalPositiveInt(String mensaje, int currentValue) {
		while (true) {
			System.out.println(mensaje + " (press Enter to keep current: " + currentValue + ")");
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				return currentValue;
			}

			try {
				int value = Integer.parseInt(input);
				if (value > 0) {
					return value;
				}
				System.out.println("Please enter a positive integer number.");
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid integer number.");
			}
		}
	}

	public static double askOptionalPositiveDouble(String mensaje, double currentValue) {
		while (true) {
			System.out.println(mensaje + " (press Enter to keep current: " + currentValue + ")");
			String input = sc.nextLine().trim();

			if (input.isEmpty()) {
				return currentValue;
			}

			try {
				double value = Double.parseDouble(input);
				if (value > 0) {
					return value;
				}
				System.out.println("Please enter a positive decimal number.");
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid decimal number.");
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
	    while (true) {
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
		    }
	    }
	}

	public static Turn askTurn() {
	    while (true) {
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
		    }
	    }
	}

		public static Type_of_appointment askAppointmentType() {
		    while (true) {
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
			    }
		    }
		}

		public static Type_of_surgery askSurgeryType() {
		    while (true) {
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
			    }
		    }
		}

		public static Category askCategory() {
		    while (true) {
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
			    }
		    }
		}

		public static byte[] askPhoto() {
		    while (true) {
			    try {
			        String photoPath = InputOutput.askText("Introduce photo path:");
			        return Utils.loadImage(photoPath);
			    } catch (Exception e) {
			        System.out.println("Could not load image. Please introduce a valid path.");
			    }
		    }
		}
	}
