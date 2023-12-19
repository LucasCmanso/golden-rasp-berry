package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class CsvImportTest {

    @Inject
    private CsvImport csvImport;

    @Test
    @DisplayName("Test csv import")
    @Order(1)
    public void testCsvImport() {
        CustomApiException thrown = assertThrows(
                CustomApiException.class,
                () -> csvImport.insertDataFromCsv("movielist_error_test.csv")
        );

        assertEquals(thrown.getMessage(), "The CSV file does not meet the table standard");


    }
}
