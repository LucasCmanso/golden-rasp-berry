package com.golden_raspberry_awards.api.adapters.inbound.DTO;

public record GoldenRaspBerryDataDto(
        Integer awardYear,
        String title,
        String studios,
        String producers,
        String winner) { }
