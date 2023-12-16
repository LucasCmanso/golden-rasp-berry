package com.golden_raspberry_awards.api.config.utils;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.adapters.outbound.Repository.GoldenRaspBerryMoviesRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;

@ApplicationScoped
@Slf4j
public class CsvImport {

    @Inject
    private GoldenRaspBerryMoviesRepository repository;

    @Transactional
    public void insertDataFromCsv(String csvFilePath) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(csvFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";");

                if (data.length < 4) {
                    throw new RuntimeException("Invalid CSV file");
                }

                String winner = data.length < 5 ? "no" : data[4];
                GoldenRaspBerryMovieEntity myEntity = new GoldenRaspBerryMovieEntity();
                myEntity.setYear(Integer.parseInt(data[0]));
                myEntity.setTitle(data[1]);
                myEntity.setStudios(data[2]);
                myEntity.setProducers(data[3]);
                myEntity.setWinner(winner);

                repository.persist(myEntity);
            }
        } catch (IOException | NullPointerException exception) {
            throw new RuntimeException(exception);
        }

    }
}
