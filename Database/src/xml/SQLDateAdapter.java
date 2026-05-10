package xml;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.adapters.XmlAdapter;



public class SQLDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public String marshal(LocalDate date) throws Exception {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }

    @Override
    public LocalDate unmarshal(String value) throws Exception {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalDate.parse(value, FORMATTER);
    }
}
