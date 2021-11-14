package com.soa.controller;

import com.soa.enums.MovieGenre;
import com.soa.service.OscarService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

@Path("oscar")
public class OscarController extends Application {

    private OscarService oscarService;

    public OscarController() {
        oscarService = new OscarService();
    }

    @GET
    public Response ping() {
        return Response.ok().entity("I AM ALIVE!").build();
    }

    @GET
    @Path("genres/redistribute-rewards/{from-genre}/{to-genre}")
    public Response reallocateOscars(@PathParam("from-genre") MovieGenre fromGenre,
                                     @PathParam("to-genre") MovieGenre toGenre) {
        oscarService.reallocateOscars(fromGenre, toGenre);
        return Response.ok().build();
    }

    @GET
    @Path("movies/honor-by-length/{min-length: [1-9]\\d*}/{oscars-to-add: [1-9]\\d*}")
    public Response increaseOscarsWhereDurationIsGreater(@PathParam("min-length") Long duration,
                                                         @PathParam("oscars-to-add") Integer oscars) {
        oscarService.increaseOscars(duration, oscars);
        return Response.ok().build();
    }


}
