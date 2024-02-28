package ro.ubbcluj.cs.map.template.Utilities;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/trenuri";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final String VIEWER_LABEL = " other user(s) are looking at the same route.";
    public static final String MOST_SOLD_TICKET_LABEL = "The most sold ticket is: ";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
}
