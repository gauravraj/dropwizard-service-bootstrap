package com.myntra.hackathon.resource.v1;

import com.myntra.hackathon.models.response.EchoResponse;
import com.myntra.hackathon.models.response.GenericResponse;
import com.myntra.hackathon.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.print.attribute.standard.Media;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@Singleton
@Path("v1/products")
@Api(value = "v1/products", description = "Add and manipulate a single trend")
public class ProductController {

    ProductService productService;

    @Inject
    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Path("{message}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Echo response" , response = EchoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Demo response", response = EchoResponse.class)
    })
    public Response getTrendResponse(@Context ContainerRequestContext requestContext,
                                     @PathParam("message") String message) {
        EchoResponse response = productService.getEchoResponse(message);
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("/seedData")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value="Seed Data", response = GenericResponse.class)
    public Response seedData(@Context ContainerRequestContext requestContext) {
        productService.seedData();
        return Response.status(Response.Status.OK).entity(new GenericResponse("SUCCESS", null)).build();
    }
}
