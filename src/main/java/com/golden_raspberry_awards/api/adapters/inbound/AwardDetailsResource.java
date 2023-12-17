package com.golden_raspberry_awards.api.adapters.inbound;

import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("api/awards-details")
public class AwardDetailsResource {

    @Inject
    private GoldenRaspBerryServicePort service;

    @GET
    public Response getAllMovies() {
        return Response.ok(service.getTheMaxMinRangeFromWinningProducers()).build();
    }
}
