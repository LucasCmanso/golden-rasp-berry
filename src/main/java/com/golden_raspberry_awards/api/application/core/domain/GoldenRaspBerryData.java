package com.golden_raspberry_awards.api.application.core.domain;

public class GoldenRaspBerryData {

    private Integer id;
    private Integer awardYear;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public GoldenRaspBerryData(){};

    public GoldenRaspBerryData(Integer id, Integer awardYear, String title, String studios, String producers, String winner) {
        this.id = id;
        this.awardYear = awardYear;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAwardYear() {
        return awardYear;
    }

    public void setAwardYear(Integer awardYear) {
        this.awardYear = awardYear;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public String getProducers() {
        return producers;
    }

    public void setProducers(String producers) {
        this.producers = producers;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
