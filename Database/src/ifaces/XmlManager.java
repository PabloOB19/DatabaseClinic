package ifaces;

import javax.xml.bind.JAXBException;

import Wrappers.DatabaseWrapper;

public interface XmlManager {
	
	public void export_to_XML(DatabaseWrapper dbWrapper) throws JAXBException;
	public DatabaseWrapper import_to_Java() throws JAXBException;
	void convert_to_HTML();
	

}
