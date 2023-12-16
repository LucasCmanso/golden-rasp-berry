package com.golden_raspberry_awards.api.adapters.outbound.Entity;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name = "movies"
)
public class GoldenRaspBerryMovieEntity extends GoldenRaspBerryData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Integer id;
}
