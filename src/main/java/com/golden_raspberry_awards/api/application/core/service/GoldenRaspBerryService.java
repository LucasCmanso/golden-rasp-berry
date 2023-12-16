package com.golden_raspberry_awards.api.application.core.service;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.RangeOfWinnersResponse;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;

import java.util.List;

public class GoldenRaspBerryService {

    DatabasePort databasePort;

    public GoldenRaspBerryService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    public RangeOfWinnersResponse getTheMaxMinRangeFromWinningProducers() {
        return null;
    }

    public List<GoldenRaspBerryData> getAllWinnersGoldenRaspBerryData() {
        return null;
    }

    public List<GoldenRaspBerryData> getAllLosersGoldenRaspBerryData() {
        return null;
    }

    public List<GoldenRaspBerryData> getAllGoldenRaspBerryData() {
        return databasePort.getAllGoldenRaspBerryData();
    }

    public GoldenRaspBerryData insertGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.insertData(data);
    }

    public GoldenRaspBerryData deleteGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.deleteData(data);
    }

    public GoldenRaspBerryData updateGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.updateData(data);
    }
}
