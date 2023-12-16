package com.golden_raspberry_awards.api.adapters.outbound.Repository;

import com.golden_raspberry_awards.api.adapters.outbound.Entity.GoldenRaspBerryMovieEntity;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GoldenRaspBerryMoviesRepository implements PanacheRepository<GoldenRaspBerryMovieEntity> {
}
