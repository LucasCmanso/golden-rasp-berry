package com.golden_raspberry_awards.api.application.core.service;

import com.golden_raspberry_awards.api.application.core.domain.MaxMinResponse;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.RangeOfWinnersResponse;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GoldenRaspBerryService implements GoldenRaspBerryServicePort {

    private final DatabasePort databasePort;

    private static final String SPLIT_PRODUCERS_REGEX = ",| and ";
    private static final String ERROR_GAP_WINNERS = "There was an error when searching for the range of winning producers";

    public GoldenRaspBerryService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public MaxMinResponse getTheMaxMinRangeFromWinningProducers() {
        List<GoldenRaspBerryData> winners = databasePort.getAllProducerWinners();
        List<GoldenRaspBerryData> winnersPerProducers = new ArrayList<>();
        winners.forEach(winner -> {

            String[] names = winner.getProducers().split(SPLIT_PRODUCERS_REGEX);

            names = Arrays.stream(names)
                    .map(String::trim)
                    .filter(name -> !name.isEmpty())
                    .toArray(String[]::new);

            Arrays.stream(names).forEach(name -> {
                winnersPerProducers.add(
                        new GoldenRaspBerryData(
                                winner.getId(),
                                winner.getAwardYear(),
                                winner.getTitle(),
                                winner.getStudios(),
                                name,
                                winner.getWinner()
                        ));
            });

        });

        List<RangeOfWinnersResponse> rangeOfWinnersResponse = findAllProducersGaps(winnersPerProducers);

        List<RangeOfWinnersResponse> rangeOfWinnersResponseMin = new ArrayList<>();
        List<RangeOfWinnersResponse> rangeOfWinnersResponseMax = new ArrayList<>();

        rangeOfWinnersResponse.forEach(range -> {
            if (range.getInterval() < 5) {
                rangeOfWinnersResponseMin.add(range);
            } else {
                rangeOfWinnersResponseMax.add(range);
            }
        });


        return new MaxMinResponse(rangeOfWinnersResponseMax, rangeOfWinnersResponseMin);
    }

    @Override
    public List<GoldenRaspBerryData> getAllGoldenRaspBerryData() {
        return databasePort.getAll();
    }

    @Override
    public GoldenRaspBerryData getGoldenRaspBerryDataById(int id) {
        return databasePort.getById(id);
    }

    @Override
    public void insertGoldenRaspBerryData(GoldenRaspBerryData data) {
        databasePort.insertData(data);
    }

    @Override
    public void deleteGoldenRaspBerryDataById(Integer id) {
        databasePort.deleteById(id);
    }

    @Override
    public void updateGoldenRaspBerryData(GoldenRaspBerryData data) {
        databasePort.updateData(data);
    }


    public List<RangeOfWinnersResponse> findAllProducersGaps(List<GoldenRaspBerryData> winners) {
        Map<String, Integer> producerMaxGap = new HashMap<>();
        Map<String, Integer> lastAwardYear = new HashMap<>();

        try {
            for (GoldenRaspBerryData award : winners) {
                String producer = award.getProducers();
                int awardYear = award.getAwardYear();

                if (lastAwardYear.containsKey(producer)) {
                    int gap = awardYear - lastAwardYear.get(producer);
                    producerMaxGap.put(producer, Math.max(producerMaxGap.getOrDefault(producer, 0), gap));
                }

                lastAwardYear.put(producer, awardYear);
            }

            List<RangeOfWinnersResponse> results = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : producerMaxGap.entrySet()) {
                String producer = entry.getKey();
                int maxGap = entry.getValue();
                int previousWin = lastAwardYear.get(producer) - maxGap;
                int followingWin = lastAwardYear.get(producer);
                results.add(new RangeOfWinnersResponse(producer, maxGap, previousWin, followingWin));
            }

            results.sort(Comparator.comparing(RangeOfWinnersResponse::getInterval));

            return results;
        } catch (Exception e) {
            throw new CustomApiException(ERROR_GAP_WINNERS, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
        }


    }
}
