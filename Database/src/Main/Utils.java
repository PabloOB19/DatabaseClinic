package Main;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	
	 public static byte[] loadImage(String path) throws Exception {
	        return Files.readAllBytes(Paths.get(path));
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
