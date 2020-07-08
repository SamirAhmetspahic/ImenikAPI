package org.imenik.rest.resources;

import org.imenik.rest.models.Contact;
import org.imenik.rest.services.ContactService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/resources")
public class ResourcesController {

    @Inject
    private ContactService queryService;

    @PermitAll
    @GET
    @Path("contacts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        List<Contact> contacts = queryService.getContactList();
        return Response.ok(contacts).build();
    }

    @PermitAll
    @GET
    @Path("contact/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContactById(@PathParam("id") long id) {
        Contact contact = queryService.getContact(id);
        return Response.ok(contact).build();
    }

    @Transactional
    @RolesAllowed("ADMIN")
    @POST
    @Path("add-contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addContact(@Context HttpServletRequest servletRequest, Contact contact) {
        Contact c = queryService.persistContact(contact);
        return Response.ok(c).build();
    }

    @RolesAllowed("ADMIN")
    @DELETE
    @Path("delete-contact/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContact(@PathParam("id") long id) {
        queryService.deleteContact(id);
        return Response.ok().build();
    }

    @RolesAllowed("ADMIN")
    @PUT
    @Path("update-contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(Contact contact) {
        System.out.print("################ THIS IS UPDATE CONTROLLER ###########################");
        Contact c = queryService.updateContact(contact);
        return Response.ok(c).build();
    }
}
