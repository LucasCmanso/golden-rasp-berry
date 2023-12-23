package com.golden_raspberry_awards.api.application.core.service;

import com.golden_raspberry_awards.api.application.core.domain.MaxMinResponse;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.RangeOfWinnersResponse;
import com.golden_raspberry_awards.api.application.ports.DatabasePort;
import com.golden_raspberry_awards.api.config.exceptions.CustomApiException;
import jakarta.ws.rs.core.Response;

import java.util.*;
import java.util.stream.Collectors;

public class GoldenRaspBerryService implements GoldenRaspBerryServicePort {

    private final DatabasePort databasePort;
    private static final String ERROR_GAP_WINNERS = "There was an error when searching for the range of winning producers";

    public GoldenRaspBerryService(DatabasePort databasePort) {
        this.databasePort = databasePort;
    }

    @Override
    public MaxMinResponse getTheMaxMinRangeFromWinningProducers() {
        List<GoldenRaspBerryData> winners = databasePort.getAllProducerWinners();

        List<RangeOfWinnersResponse> rangeOfWinnersResponse = findAllProducersGaps(winners);

        List<RangeOfWinnersResponse> rangeOfWinnersResponseMin = rangeOfWinnersResponse.stream().filter(
                producerInterval -> producerInterval.getInterval().equals(
                        rangeOfWinnersResponse.stream()
                                .min(Comparator.comparing(RangeOfWinnersResponse::getInterval))
                                .orElseThrow(NoSuchElementException::new).getInterval()
                )
        ).collect(Collectors.toList());

        List<RangeOfWinnersResponse> rangeOfWinnersResponseMax = rangeOfWinnersResponse.stream().filter(
                producerInterval -> producerInterval.getInterval().equals(
                        rangeOfWinnersResponse.stream()
                                .max(Comparator.comparing(RangeOfWinnersResponse::getInterval))
                                .orElseThrow(NoSuchElementException::new).getInterval()
                )
        ).collect(Collectors.toList());


        return new MaxMinResponse(rangeOfWinnersResponseMin, rangeOfWinnersResponseMax);
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
    public int insertGoldenRaspBerryData(GoldenRaspBerryData data) {
        return databasePort.insertData(data);
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
        Map<String, List<GoldenRaspBerryData>> awardsByProducer = winners.stream().collect(Collectors.groupingBy(GoldenRaspBerryData::getProducers));

        try {
            List<RangeOfWinnersResponse> rangeOfWinnersResponse = new ArrayList<>();

            awardsByProducer.forEach((producer, awards) -> {
                awards.sort(Comparator.comparing(GoldenRaspBerryData::getAwardYear));

                if (awards.size() >= 2) {
                    awards.forEach(award -> {

                            GoldenRaspBerryData nextAward = awards.stream()
                                    .filter(oneAward -> (oneAward.equals(awards.get(awards.indexOf(award)))) && awards.indexOf(award) + 1 < awards.size())
                                    .map(nextSingleAward -> awards.get(awards.indexOf(nextSingleAward) + 1))
                                    .findFirst().orElse(null);

                            if (nextAward != null) {

                                RangeOfWinnersResponse rangeWinner = new RangeOfWinnersResponse();

                                rangeWinner.setProducer(producer);
                                rangeWinner.setPreviousWin(award.getAwardYear());
                                rangeWinner.setFollowingWin(nextAward.getAwardYear());
                                rangeWinner.setInterval(rangeWinner.getFollowingWin() - rangeWinner.getPreviousWin());

                                rangeOfWinnersResponse.add(rangeWinner);
                            }
                    });
                }
            });
            return rangeOfWinnersResponse;
        } catch (Exception e) {
            throw new CustomApiException(ERROR_GAP_WINNERS, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
        }


    }
}
