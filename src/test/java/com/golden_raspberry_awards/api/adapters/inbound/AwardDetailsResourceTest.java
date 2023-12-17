package com.golden_raspberry_awards.api.adapters.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golden_raspberry_awards.api.adapters.inbound.DTO.GoldenRaspBerryDataDto;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.core.domain.MaxMinResponse;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(AwardDetailsResource.class)
public class AwardDetailsResourceTest {

    @Inject
    GoldenRaspBerryServicePort service;

    MaxMinResponse response = new MaxMinResponse();

    @BeforeEach
    public void setup() {
        response = service.getTheMaxMinRangeFromWinningProducers();
    }

    @Test
    @Order(1)
    public void testTheMaxMinRangeFromWinningProducersEndpoint() throws JsonProcessingException {
        when().get()
                .then()
                .statusCode(200)
                .body(is(new ObjectMapper().writeValueAsString(response)));
    }
}
