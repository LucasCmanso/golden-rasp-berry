package com.golden_raspberry_awards.api.adapters.inbound;

import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/movies")
public class MoviesResource {

    @Inject
    private GoldenRaspBerryServicePort service;

    @GET
    public Response getAllMovies() {
        return Response.ok(service.getAllGoldenRaspBerryData()).build();
    }

    @POST
    public Response addMovie(GoldenRaspBerryData request) {
        return Response.ok(service.insertGoldenRaspBerryData(request)).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteMovieById(int id) {
        service.deleteGoldenRaspBerryDataById(id);
        return Response.noContent().build();
    }

    @PATCH
    public Response updateMovie(GoldenRaspBerryData request) {
        return Response.ok(service.updateGoldenRaspBerryData(request)).build();
    }
}
