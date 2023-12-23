package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class CsvImportTest {

    @Inject
    private CsvImport csvImport;

    @Inject
    private GoldenRaspBerryServicePort service;

    private static final String SPLIT_PRODUCERS_REGEX = "( and |,)";
    private static final String NAMES_SPLIT_TEST = "Avi Lerner, Kevin King Templeton, Yariv Lerner, and Les Weldon";


    @Test
    @DisplayName("Test csv import validation")
    @Order(1)
    public void testCsvImportValidation() {
        CustomApiException thrown = assertThrows(
                CustomApiException.class,
                () -> csvImport.insertDataFromCsv("movielist_error_test.csv")
        );

        assertEquals(thrown.getMessage(), "The CSV file does not meet the table standard");
    }

    @Test
    @DisplayName("Test csv import")
    @Order(2)
    public void testCsvImport() {
        csvImport.insertDataFromCsv("movielist.csv");
        GoldenRaspBerryData data = service.getGoldenRaspBerryDataById(1);
        assertNotNull(data);
    }

    @Test
    @DisplayName("Test csv import split names")
    @Order(3)
    public void testCsvImportSplitNames() {
        String[] names = NAMES_SPLIT_TEST.split(SPLIT_PRODUCERS_REGEX);

        names = Arrays.stream(names)
                .map(String::trim)
                .filter(name -> !name.isEmpty())
                .toArray(String[]::new);

        assertNotNull(names);
        assertEquals(names[0].trim(), "Avi Lerner");
        assertEquals(names[1].trim(), "Kevin King Templeton");
        assertEquals(names[2].trim(), "Yariv Lerner");
        assertEquals(names[3].trim(), "Les Weldon");
    }
}
