package utils;

import exceptions.InvalidFormatDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Provides methods to conversion between String and LocalDateTime
 */
public class DateConvertor {
    /**
     * Template for LocalDateTime
     */
    private static DateTimeFormatter localDateFormatter =  new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy[ [HH][:mm][:ss][.SSS]]")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();

    private static DateTimeFormatter localDateFormat =  new DateTimeFormatterBuilder().appendPattern("dd/MM/yyyy")
            .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
            .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
            .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
            .toFormatter();




    /**
     * Convert String to LocalDateTime
     * @param s date in String from user input
     * @return parsed date from String
     * @throws InvalidFormatDateException if date is incorrect
     */
    public static LocalDateTime parseLocalDate(String s) throws InvalidFormatDateException {
        try {
            return LocalDateTime.parse(s, localDateFormatter);
        } catch (java.time.format.DateTimeParseException e) {
            throw new InvalidFormatDateException();
        }
    }

    /**
     * Convert LocalDateTime to String
     * @param date
     * @return date converted from localDateTime to string
     */
    public static String dateToString(LocalDateTime date) {
        return localDateFormatter.format(date);
    }

    public static String dateToString1(LocalDateTime date) {
        return localDateFormat.format(date);
    }


}

