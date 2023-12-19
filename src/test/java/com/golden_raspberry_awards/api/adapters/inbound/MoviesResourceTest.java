package com.golden_raspberry_awards.api.adapters.inbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golden_raspberry_awards.api.adapters.inbound.DTO.GoldenRaspBerryDataDto;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static io.restassured.RestAssured.given;


@QuarkusTest
@TestHTTPEndpoint(MoviesResource.class)
public class MoviesResourceTest {

    @Inject
    GoldenRaspBerryServicePort service;

    List<GoldenRaspBerryData> responseList = new ArrayList<>();
    GoldenRaspBerryData response = new GoldenRaspBerryData();
    GoldenRaspBerryData updateBody = new GoldenRaspBerryData();

    GoldenRaspBerryDataDto postDto = new GoldenRaspBerryDataDto(
            2023,
            "Transformers: The First Knight",
            "Paramount Pictures",
            "Lucas Cordeiro",
            "yes");

    private static final Integer RESPONSE_ID = 20;
    private static final Integer DELETE_ID = 40;
    private static final Integer UPDATE_ID = 30;

    @BeforeEach
    public void setup() {
        responseList = service.getAllGoldenRaspBerryData();
        response = service.getGoldenRaspBerryDataById(RESPONSE_ID);
        updateBody = service.getGoldenRaspBerryDataById(UPDATE_ID);
        updateBody.setAwardYear(2050);
    }

    @Test
    @DisplayName("Test get all data response")
    @Order(1)
    public void testGetAllDataEndpoint() throws JsonProcessingException {
        when().get()
                .then()
                .statusCode(200)
                .body(is(new ObjectMapper().writeValueAsString(responseList)));
    }

    @Test
    @DisplayName("Test get by id data response")
    @Order(2)
    public void testGetByIdDataEndpoint() throws JsonProcessingException {
        given()
                .pathParam("id", RESPONSE_ID)
                .header("Content-Type", ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .body(is(new ObjectMapper().writeValueAsString(response)));
    }

    @Test
    @DisplayName("Test insertion data response")
    @Order(3)
    public void testAddDataEndpoint() {
        given()
                .body(postDto)
                .header("Content-Type", ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(200)
                .body(is("Insertion completed successfully"));

        GoldenRaspBerryData data = service.getGoldenRaspBerryDataById(207);

        Assertions.assertEquals(2023, data.getAwardYear());
        Assertions.assertEquals("Lucas Cordeiro", data.getProducers());
    }

    @Test
    @DisplayName("Test delete data response")
    @Order(4)
    public void testDeleteDataEndpoint() {
        given()
                .pathParam("id", DELETE_ID)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @DisplayName("Test update data response")
    @Order(5)
    public void testUpdateDataEndpoint() {
        given()
                .body(updateBody)
                .header("Content-Type", ContentType.JSON)
                .when()
                .put()
                .then()
                .statusCode(200)
                .body(is("Update completed successfully"));
    }
}
