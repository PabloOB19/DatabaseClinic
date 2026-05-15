package JDBC;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;

final class JDBCDateUtils {

    private JDBCDateUtils() {
    }

    static LocalDate parseLocalDate(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        String trimmedValue = value.trim();

        try {
            return LocalDate.parse(trimmedValue);
        } catch (DateTimeParseException e) {
            if (trimmedValue.matches("-?\\d+")) {
                long timestamp = Long.parseLong(trimmedValue);
                return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate();
            }

            throw e;
        }
    }
}
