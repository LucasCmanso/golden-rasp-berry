package com.golden_raspberry_awards.api.adapters.outbound.Entity;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "movies"
)
public class GoldenRaspBerryMovieEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private int awardYear;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public GoldenRaspBerryMovieEntity(GoldenRaspBerryData data) {
        this.id = null;
        this.awardYear = data.getAwardYear();
        this.title = data.getTitle();
        this.studios = data.getStudios();
        this.producers = data.getProducers();
        this.winner = data.getWinner();
    }
}
