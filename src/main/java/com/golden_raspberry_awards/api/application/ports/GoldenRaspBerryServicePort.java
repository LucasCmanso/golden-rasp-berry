package com.golden_raspberry_awards.api.application.ports;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.RangeOfWinnersResponse;

import java.util.List;

public interface GoldenRaspBerryServicePort {

    RangeOfWinnersResponse getTheMaxMinRangeFromWinningProducers();
    List<GoldenRaspBerryData> getAllGoldenRaspBerryData();
    GoldenRaspBerryData insertGoldenRaspBerryData(GoldenRaspBerryData data);
    void deleteGoldenRaspBerryDataById(Integer id);
    GoldenRaspBerryData updateGoldenRaspBerryData(GoldenRaspBerryData data);
}
