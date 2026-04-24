package com.bilt.warmup;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/greetings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {

    @GET
    public List<Greeting> list() {
        return Greeting.listAll();
    }

    @GET
    @Path("/{id}")
    public Greeting get(@PathParam("id") Long id) {
        Greeting g = Greeting.findById(id);
        if (g == null) throw new NotFoundException();
        return g;
    }

    @POST
    @Transactional
    public Response create(Greeting g) {
        g.persist();
        return Response.status(Response.Status.CREATED).entity(g).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        boolean deleted = Greeting.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(404).build();
    }
}
