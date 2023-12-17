package com.golden_raspberry_awards.api.adapters.outbound.Entity;

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
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "increment")
    private Integer id;

    private int awardYear;
    private String title;
    private String studios;
    private String producers;
    private String winner;

}
