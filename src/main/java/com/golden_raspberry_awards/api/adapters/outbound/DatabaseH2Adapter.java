package com.golden_raspberry_awards.api.adapters.outbound;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class DatabaseH2Adapter implements DatabasePort {

    private static final String FETCH_DATA_ERROR = "An error occurred while fetching all data";
    private static final String NO_DATA_ERROR = "No data found";
    private static final String WINNERS_QUERY = "SELECT * FROM movies WHERE winner = 'yes'";


    @Override
    public List<GoldenRaspBerryData> getAllProducerWinners() {
        try {
            List<GoldenRaspBerryMovieEntity> entityList = GoldenRaspBerryMovieEntity.find(WINNERS_QUERY).list();

            if (entityList.size() == 0) {
                throw new CustomApiException(FETCH_DATA_ERROR, Response.Status.NOT_FOUND.getStatusCode(), NO_DATA_ERROR);
            }

            return entityList.stream()
                    .map(entity -> new GoldenRaspBerryData(entity.getId(), entity.getAwardYear(), entity.getTitle(), entity.getStudios(), entity.getProducers(), entity.getWinner()))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CustomApiException(FETCH_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }

    @Override
    public List<GoldenRaspBerryData> getAll() {
        try {
            List<GoldenRaspBerryMovieEntity> entityList = GoldenRaspBerryMovieEntity.findAll().list();

            if (entityList.size() == 0) {
                throw new CustomApiException(FETCH_DATA_ERROR, Response.Status.NOT_FOUND.getStatusCode(), NO_DATA_ERROR);
            }

            return entityList.stream()
                    .map(entity -> new GoldenRaspBerryData(entity.getId(), entity.getAwardYear(), entity.getTitle(), entity.getStudios(), entity.getProducers(), entity.getWinner()))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CustomApiException(FETCH_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }

    @Override
    public GoldenRaspBerryData getById() {
        return null;
    }

    @Override
    public GoldenRaspBerryData insertData(GoldenRaspBerryData data) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {
    }

    @Override
    public GoldenRaspBerryData updateData(GoldenRaspBerryData data) {
        return null;
    }
}
