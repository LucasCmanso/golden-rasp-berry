package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@ApplicationScoped
@Slf4j
@Transactional
public class CsvImport {

    private static final String FETCH_CSV_DATA_ERROR = "An error occurred while fetching csv data";
    private static final String INVALID_CSV_DATA_ERROR = "Invalid CSV file";
    private static final String INVALID_CSV_FORMAT_ERROR = "The csv file is not in a valid format";

    public void insertDataFromCsv(String csvFilePath) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvFilePath)) {
            assert inputStream != null;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

                br.readLine();

                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");

                    if (data.length < 4) {
                        throw new CustomApiException(INVALID_CSV_DATA_ERROR, Response.Status.BAD_REQUEST.getStatusCode(), INVALID_CSV_FORMAT_ERROR);
                    }

                    String winner = data.length < 5 ? "no" : data[4];

                    GoldenRaspBerryMovieEntity myEntity = new GoldenRaspBerryMovieEntity();

                    myEntity.setAwardYear(Integer.parseInt(data[0]));
                    myEntity.setTitle(data[1]);
                    myEntity.setStudios(data[2]);
                    myEntity.setProducers(data[3]);
                    myEntity.setWinner(winner);

                    myEntity.persist();
                }
            }
        } catch (IOException | NullPointerException exception) {
            throw new CustomApiException(FETCH_CSV_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }

    }
}
