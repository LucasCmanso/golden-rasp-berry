package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.io.*;
import java.util.*;

@ApplicationScoped
@Transactional
public class CsvImport {

    private static final String FETCH_CSV_DATA_ERROR = "An error occurred while fetching csv data";
    private static final String SPLIT_PRODUCERS_REGEX = "( and |,)";

    public void insertDataFromCsv(String csvFilePath) {

        List<String[]> lines = getCsvData(csvFilePath);

        List<GoldenRaspBerryMovieEntity> myEntityList = new ArrayList<>();

        lines.forEach(data -> {
            String winner = data.length < 5 ? "no" : data[4];

            String[] names = data[3].split(SPLIT_PRODUCERS_REGEX);

            GoldenRaspBerryData newData = new GoldenRaspBerryData();

            for (String name : names) {
                newData.setAwardYear(Integer.parseInt(data[0]));
                newData.setTitle(data[1].trim());
                newData.setStudios(data[2].trim());
                newData.setProducers(name.trim());
                newData.setWinner(winner);

                myEntityList.add(new GoldenRaspBerryMovieEntity(newData));
            }
        });

        GoldenRaspBerryMovieEntity.persist(myEntityList);

    }

    public List<String[]> getCsvData(String csvFilePath) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvFilePath)) {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

                String firstLine = br.readLine();

                CsvValidator.validateCsvNameFields(firstLine);

                List<String[]> lines = new ArrayList<>();

                String line;
                while ((line = br.readLine()) != null) {
                    String[] dataLine = line.split(";");
                    lines.add(dataLine);
                }

                return lines;
            }
        } catch (IOException | NullPointerException exception) {
            throw new CustomApiException(FETCH_CSV_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }
}
