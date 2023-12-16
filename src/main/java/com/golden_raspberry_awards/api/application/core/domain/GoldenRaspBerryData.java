package com.golden_raspberry_awards.api.application.core.domain;

public class GoldenRaspBerryData {
    private int year;
    private String title;
    private String studios;
    private String producers;
    private String winner;

    public GoldenRaspBerryData(){};

    public GoldenRaspBerryData(int year, String title, String studios, String producers, String winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public String isWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
