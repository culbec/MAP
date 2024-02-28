package ro.ubbcluj.cs.map.template.Utilities;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/practic";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final DateTimeFormatter DATE_TIME_FORMATTER= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
}
