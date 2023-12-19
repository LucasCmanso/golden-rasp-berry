package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

public class CsvValidator {

    private static final String ERROR_INVALID_CSV = "The CSV file does not meet the table standard";
    private static final String ERROR_COLUMNS_LENGHT = "Invalid number of columns, the correct one is five (year, title, studios, producers and winner)";
    private static final String[] CSV_FIELDS = new String[]{"year", "title", "studios", "producers", "winner"};

    public static void validateCsvNameFields(String firstLine) {
        String[] columnsNames = firstLine.split(";");

        if (columnsNames.length != 5)
            throwError(ERROR_COLUMNS_LENGHT);

        if (!columnsNames[0].equalsIgnoreCase(CSV_FIELDS[0])) {
            throwError("The first column should be " + CSV_FIELDS[0]);
        } else if (!columnsNames[1].equalsIgnoreCase(CSV_FIELDS[1])) {
            throwError("The second column should be " + CSV_FIELDS[1]);
        } else if (!columnsNames[2].equalsIgnoreCase(CSV_FIELDS[2])) {
            throwError("The third column should be " + CSV_FIELDS[2]);
        } else if (!columnsNames[3].equalsIgnoreCase(CSV_FIELDS[3])) {
            throwError("The fourth column should be " + CSV_FIELDS[3]);
        } else if (!columnsNames[4].equalsIgnoreCase(CSV_FIELDS[4])) {
            throwError("The fifth column should be " + CSV_FIELDS[4]);
        }
    }

    private static void throwError(String details) {
        throw new CustomApiException(ERROR_INVALID_CSV, Response.Status.BAD_REQUEST.getStatusCode(), details);
    }
}
