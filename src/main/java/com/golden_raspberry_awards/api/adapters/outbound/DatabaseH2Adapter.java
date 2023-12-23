package com.golden_raspberry_awards.api.adapters.outbound;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
public class DatabaseH2Adapter implements DatabasePort {

    private static final String FETCH_DATA_ERROR = "An error occurred while fetching data from table movies";
    private static final String INSERT_DATA_ERROR = "An error occurred while insert data in table movies";
    private static final String DELETE_DATA_ERROR = "An error occurred while delete data from table movies";
    private static final String NO_DATA_ERROR = "No data found";
    private static final String UPDATE_DATA_ERROR = "An error occurred while update data in table movies";
    private static final String WINNERS_QUERY = "winner = ?1";


    @Override
    public List<GoldenRaspBerryData> getAllProducerWinners() {
        try {
            List<GoldenRaspBerryMovieEntity> entityList = GoldenRaspBerryMovieEntity.find(WINNERS_QUERY, "yes").list();

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
    public GoldenRaspBerryData getById(int id) {
        try {
            GoldenRaspBerryMovieEntity entity = GoldenRaspBerryMovieEntity.findById(id);
            return new GoldenRaspBerryData(
                    entity.getId(),
                    entity.getAwardYear(),
                    entity.getTitle(),
                    entity.getStudios(),
                    entity.getProducers(),
                    entity.getWinner()
            );
        } catch (Exception exception) {
            throw new CustomApiException(FETCH_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }

    @Override
    public int insertData(GoldenRaspBerryData data) {
        try {
            GoldenRaspBerryMovieEntity entity = new GoldenRaspBerryMovieEntity(
                    null,
                    data.getAwardYear(),
                    data.getTitle(),
                    data.getStudios(),
                    data.getProducers(),
                    data.getWinner()
            );
            GoldenRaspBerryMovieEntity.persist(entity);

            return getAll().size();
        } catch (Exception exception) {
            throw new CustomApiException(INSERT_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            GoldenRaspBerryMovieEntity.deleteById(id);
        } catch (Exception exception) {
            throw new CustomApiException(DELETE_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }

    @Override
    public void updateData(GoldenRaspBerryData data) {
        try {
            GoldenRaspBerryMovieEntity entity = GoldenRaspBerryMovieEntity.findById(data.getId());

            if (entity != null) {
                entity.setAwardYear(data.getAwardYear());
                entity.setTitle(data.getTitle());
                entity.setStudios(data.getStudios());
                entity.setProducers(data.getProducers());
                entity.setWinner(data.getWinner());
            } else {
                throw new CustomApiException(NO_DATA_ERROR, Response.Status.NOT_FOUND.getStatusCode(), UPDATE_DATA_ERROR);
            }
        } catch (Exception exception) {
            throw new CustomApiException(UPDATE_DATA_ERROR, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), exception.getMessage());
        }
    }
}
