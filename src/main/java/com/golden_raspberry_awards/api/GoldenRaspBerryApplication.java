package com.golden_raspberry_awards.api;

import com.golden_raspberry_awards.api.config.utils.CsvImport;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@QuarkusMain
public class GoldenRaspBerryApplication {

    @Inject
    private CsvImport csvImport;

    public static void main(String[] args) {
        Quarkus.run(args);
    }

    public void onStart(@Observes StartupEvent ev) {
        csvImport.insertDataFromCsv("movielist.csv");
    }
}
