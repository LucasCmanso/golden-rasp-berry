package com.golden_raspberry_awards.api.application.core.service;

import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.RangeOfWinnersResponse;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;

import java.util.List;

public class GoldenRaspBerryService implements GoldenRaspBerryServicePort {

    private final DatabasePort databasePort;

    public GoldenRaspBerryService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public RangeOfWinnersResponse getTheMaxMinRangeFromWinningProducers() {
        var data = databasePort.getAllProducerWinners();
        return null;
    }

    @Override
    public List<GoldenRaspBerryData> getAllGoldenRaspBerryData() {
        return databasePort.getAll();
    }

    @Override
    public GoldenRaspBerryData insertGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.insertData(data);
    }

    @Override
    public void deleteGoldenRaspBerryDataById(Integer id) {
        databasePort.deleteById(id);
    }

    @Override
    public GoldenRaspBerryData updateGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.updateData(data);
    }

    private RangeOfWinnersResponse findMaxGap(List<GoldenRaspBerryData> list) {
        return null;
    }

    private RangeOfWinnersResponse findMinGap(List<GoldenRaspBerryData> list) {
        return null;
    }
}
