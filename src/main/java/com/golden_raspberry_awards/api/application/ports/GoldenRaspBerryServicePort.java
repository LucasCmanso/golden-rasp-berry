package com.golden_raspberry_awards.api.application.ports;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.MaxMinResponse;

import java.util.List;

public interface GoldenRaspBerryServicePort {

    MaxMinResponse getTheMaxMinRangeFromWinningProducers();
    List<GoldenRaspBerryData> getAllGoldenRaspBerryData();
    GoldenRaspBerryData getGoldenRaspBerryDataById(int id);
    void insertGoldenRaspBerryData(GoldenRaspBerryData data);
    void deleteGoldenRaspBerryDataById(Integer id);
    void updateGoldenRaspBerryData(GoldenRaspBerryData data);
}
