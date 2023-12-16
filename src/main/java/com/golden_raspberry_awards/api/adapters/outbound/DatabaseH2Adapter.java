package com.golden_raspberry_awards.api.adapters.outbound;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import com.golden_raspberry_awards.api.adapters.outbound.Repository.GoldenRaspBerryMoviesRepository;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@ApplicationScoped
@Slf4j
public class DatabaseH2Adapter implements DatabasePort {

    @Inject
    private GoldenRaspBerryMoviesRepository repository;

    @Override
    public List<GoldenRaspBerryData> getAllGoldenRaspBerryData() {
        return null;
    }

    @Override
    public List<GoldenRaspBerryData> getAllProducerWinners() {
        PanacheQuery<GoldenRaspBerryMovieEntity> winnersQuery =
                repository.find(
                        "SELECT * FROM movies WHERE winner = true"
                );

        log.info(winnersQuery.firstResult().getProducers());

        return null;
    }

    @Override
    public GoldenRaspBerryData insertData(GoldenRaspBerryData data) {
        return null;
    }

    @Override
    public GoldenRaspBerryData deleteData(GoldenRaspBerryData data) {
        return null;
    }

    @Override
    public GoldenRaspBerryData updateData(GoldenRaspBerryData data) {
        return null;
    }
}
