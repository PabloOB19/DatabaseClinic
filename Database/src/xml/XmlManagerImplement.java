package xml;

import javax.xml.bind.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.*;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import Wrappers.DatabaseWrapper;
import ifaces.XmlManager;

public class XmlManagerImplement implements XmlManager {

	private static final String DTD_FILE = "DataDTD.dtd";
	private static final String XML_FILE = "DataXML.xml";
	private static final String XSL_FILE = "DataXSL.xsl";
	private static final String HTML_FILE = "DataHTML.html";

	@Override
	public void export_to_XML(DatabaseWrapper dbWrapper) throws JAXBException {
		File fileDTD = getFile(DTD_FILE);
		if (!fileDTD.exists()) {
			throw new IllegalArgumentException("File not found");
		}

		File xmlFile = getFile(XML_FILE);
		File directory = xmlFile.getParentFile();
		if (directory != null && !directory.exists()) {
			directory.mkdirs();
		}

		JAXBContext context = JAXBContext.newInstance(DatabaseWrapper.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		marshaller.setProperty("com.sun.xml.bind.xmlHeaders",
				"<!DOCTYPE DataBase SYSTEM \"" + DTD_FILE + "\">");

		File tempFile = null;
		try {
			tempFile = File.createTempFile("DataXML", ".tmp", directory);
			marshaller.marshal(dbWrapper, tempFile);
			validateXML(tempFile);
			Files.move(tempFile.toPath(), xmlFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (JAXBException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new JAXBException("Error exporting XML", ex);
		} finally {
			if (tempFile != null && tempFile.exists()) {
				tempFile.delete();
			}
		}
	}

	@Override
	public DatabaseWrapper import_to_Java() throws JAXBException {
		File xmlFile = getFile(XML_FILE);
		if (!xmlFile.exists()) {
			throw new IllegalArgumentException("File not found");
		}
		validateXML(xmlFile);

		JAXBContext context = JAXBContext.newInstance(DatabaseWrapper.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		try (FileInputStream dtdInput = new FileInputStream(getFile(DTD_FILE));
			 FileInputStream xmlInput = new FileInputStream(xmlFile)) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader reader = factory.newSAXParser().getXMLReader();
			reader.setEntityResolver((publicId, systemId) -> new InputSource(dtdInput));
			SAXSource source = new SAXSource(reader, new InputSource(xmlInput));
			return (DatabaseWrapper) unmarshaller.unmarshal(source);
		} catch (JAXBException ex) {
			throw new JAXBException("Error importing XML", ex);
		} catch (Exception ex) {
			throw new JAXBException("Error importing XML", ex);
		}
	}

	@Override
	public void convert_to_HTML() {
		try {
			File xml = getFile(XML_FILE);
			File xsl = getFile(XSL_FILE);
			File html = getFile(HTML_FILE);
			validateXML(xml);

			Source xmlSource = new StreamSource(xml);
			Source xslSource = new StreamSource(xsl);
			Result result = new StreamResult(html);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(xslSource);
			transformer.transform(xmlSource, result);
		} catch (Exception ex) {
			throw new IllegalStateException("Error generating HTML", ex);
		}
	}

	private File getFile(String fileName) {
		File databaseExecutionPath = new File("XmlFiles", fileName);
		if (databaseExecutionPath.exists() || databaseExecutionPath.getParentFile().exists()) {
			return databaseExecutionPath;
		}
		return new File("Database/XmlFiles", fileName);
	}

	private void validateXML(File xmlFile) {
		try (FileInputStream dtdInput = new FileInputStream(getFile(DTD_FILE));
			 FileInputStream xmlInput = new FileInputStream(xmlFile)) {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			factory.setValidating(true);
			XMLReader reader = factory.newSAXParser().getXMLReader();
			reader.setEntityResolver((publicId, systemId) -> new InputSource(dtdInput));
			reader.setErrorHandler(new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					throw exception;
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
					throw exception;
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					throw exception;
				}
			});
			reader.parse(new InputSource(xmlInput));
		} catch (Exception ex) {
			throw new IllegalStateException("XML validation failed", ex);
		}
	}
}
