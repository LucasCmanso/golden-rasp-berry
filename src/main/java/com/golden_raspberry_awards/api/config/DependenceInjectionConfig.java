package com.golden_raspberry_awards.api.config;

import com.golden_raspberry_awards.api.application.core.service.GoldenRaspBerryService;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;

@Dependent
public class DependenceInjectionConfig {

    @Default
    public GoldenRaspBerryService goldenRaspBerryService(DatabasePort databasePort) {
        return new GoldenRaspBerryService(databasePort);
    }
}
