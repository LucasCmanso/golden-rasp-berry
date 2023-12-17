package com.golden_raspberry_awards.api.adapters.inbound;

import com.golden_raspberry_awards.api.adapters.inbound.DTO.GoldenRaspBerryDataDto;
import com.golden_raspberry_awards.api.adapters.outbound.mappers.GoldenRaspBerryMapper;
import com.golden_raspberry_awards.api.application.core.domain.GoldenRaspBerryData;
import com.golden_raspberry_awards.api.application.ports.GoldenRaspBerryServicePort;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("api/movies")
public class MoviesResource {

    @Inject
    private GoldenRaspBerryServicePort service;

    @GET
    public Response getAllMovies() {
        return Response.ok(service.getAllGoldenRaspBerryData()).build();
    }

    @GET
    @Path("/{id}")
    public Response getAllMovies(@PathParam("id") int id) {
        return Response.ok(service.getGoldenRaspBerryDataById(id)).build();
    }

    @POST
    public Response addMovie(GoldenRaspBerryDataDto request) {
        service.insertGoldenRaspBerryData(GoldenRaspBerryMapper.INSTANCE.goldenRaspBerryDtoToData(request));
        return Response.ok("Insertion completed successfully").build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMovieById(@PathParam("id") int id) {
        service.deleteGoldenRaspBerryDataById(id);
        return Response.noContent().build();
    }

    @PUT
    public Response updateMovie(GoldenRaspBerryData request) {
        service.updateGoldenRaspBerryData(request);
        return Response.ok("Update completed successfully").build();
    }
}
