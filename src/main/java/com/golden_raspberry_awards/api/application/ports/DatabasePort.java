package com.golden_raspberry_awards.api.application.ports;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;

import java.util.List;

public interface DatabasePort {

    List<GoldenRaspBerryData> getAllGoldenRaspBerryData();
    List<GoldenRaspBerryData> getAllProducerWinners();
    GoldenRaspBerryData insertData(GoldenRaspBerryData data);
    GoldenRaspBerryData deleteData(GoldenRaspBerryData data);
    GoldenRaspBerryData updateData(GoldenRaspBerryData data);


}
