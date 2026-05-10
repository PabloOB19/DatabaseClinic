package xml;

import javax.xml.bind.*;

import POJOS.Doctor;
import POJOS.Patient;

import java.io.File;


public class XmlManager {

	private JAXBContext context;

	public XmlManager() 
	{
		try {
	            context = JAXBContext.newInstance(Doctor.class, Patient.class); // Registras todas las clases que vas a usar
	    } catch (JAXBException e) 
		{
	    	e.printStackTrace();
	    }
}

// =========================
// MARSHALL (Object -> XML)
// =========================
	public void marshal(Object obj, String filePath) {
		try {
			Marshaller marshaller = context.createMarshaller();
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

	        marshaller.marshal(obj, new File(filePath));
	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }
}

// =========================
// UNMARSHALL (XML -> Object)
// =========================
	public Object unmarshal(String filePath, Class<?> clazz) {
	    try {
	        Unmarshaller unmarshaller = context.createUnmarshaller();
	        return unmarshaller.unmarshal(new File(filePath));
	    } catch (JAXBException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
}


