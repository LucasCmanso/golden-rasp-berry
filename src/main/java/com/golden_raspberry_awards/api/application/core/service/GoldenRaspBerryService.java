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
import java.util.stream.Collectors;

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
        winners.sort(Comparator.comparing(GoldenRaspBerryData::getAwardYear));
        String names = "Simon Kinberg, Matthew Vaughn, Hutch Parker, Robert Kulzer and Gregory Goodman";
        Arrays.stream(names.split("(and|,)")).forEach(log::info);
        winners.forEach(winner -> log.info("Ano: {}, Produtor: {}", winner.getAwardYear(), winner.getProducers()));
        Map<String, List<GoldenRaspBerryData>> awardsByProducer = winners.stream().collect(Collectors.groupingBy(GoldenRaspBerryData::getProducers));

        try {
            List<RangeOfWinnersResponse> rangeOfWinnersResponse = new ArrayList<>();

            awardsByProducer.forEach((producer, awards) -> {
                awards.sort(Comparator.comparing(GoldenRaspBerryData::getAwardYear));

                if (awards.size() >= 2) {
                    awards.stream()
                            .map(award -> awards.indexOf(award) + 1 != awards.size() ? awards.get(awards.indexOf(award) + 1) : null).findFirst()
                            .ifPresent(nextAward -> {
                                awards.forEach(award -> {
                                    if (!award.getAwardYear().equals(nextAward.getAwardYear())) {
                                        RangeOfWinnersResponse rangeWinner = new RangeOfWinnersResponse();
                                        int previousWin = award.getAwardYear();
                                        int followingWin = nextAward.getAwardYear();
                                        if (award.getAwardYear() > nextAward.getAwardYear()) {
                                            previousWin = nextAward.getAwardYear();
                                            followingWin = award.getAwardYear();
                                        }
                                        rangeWinner.setProducer(producer);
                                        rangeWinner.setPreviousWin(previousWin);
                                        rangeWinner.setFollowingWin(followingWin);
                                        rangeWinner.setInterval(rangeWinner.getFollowingWin() - rangeWinner.getPreviousWin());

                                        rangeOfWinnersResponse.add(rangeWinner);
                                    }
                                });
                            });
                }


            });
            return rangeOfWinnersResponse;
        } catch (Exception e) {
            throw new CustomApiException(ERROR_GAP_WINNERS, Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage());
        }


    }
}
