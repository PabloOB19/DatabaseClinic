package Utils;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	
	 public static byte[] loadImage(String path) throws Exception {
	        return Files.readAllBytes(Paths.get(path));
	    }


}
