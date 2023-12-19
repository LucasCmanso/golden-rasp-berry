package com.golden_raspberry_awards.api.adapters.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golden_raspberry_awards.api.application.core.domain.MaxMinResponse;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;


import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(AwardDetailsResource.class)
public class AwardDetailsResourceTest {

    @Test
    @DisplayName("Test min and max range response")
    @Order(1)
    public void testTheMaxMinRangeFromWinningProducersEndpoint() {
        MaxMinResponse response = when().get()
                .then()
                .statusCode(200)
                .assertThat().extract().body().as(MaxMinResponse.class);

        Assertions.assertEquals(1, response.getMin().get(0).getInterval());
        Assertions.assertEquals(13, response.getMax().get(0).getInterval());


    }
}
