package com.golden_raspberry_awards.api.application.ports;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;

import java.util.List;

public interface DatabasePort {

    List<GoldenRaspBerryData> getAllProducerWinners();
    List<GoldenRaspBerryData> getAll();
    GoldenRaspBerryData getById(int id);
    void insertData(GoldenRaspBerryData data);
    void deleteById(Integer id);
    void updateData(GoldenRaspBerryData data);


}
