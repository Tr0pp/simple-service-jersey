package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Path("users")
public class UsersResource {

    private static List<User> users = new ArrayList<User>();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> users() {
        return users;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User users(@PathParam("id") String id) {
        User filteredUser = new User();

        return filteredUser = users.stream()
                .filter(user -> id.equals(Integer.toString(user.getId())))
                .findAny()
                .orElse(null);
    }

    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User save(User user) {
        user.setId(users.size() + 1);
        users.add(user);

        return user;
    }

    @PATCH
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User update(User user) {

        return user;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") String id) {
        User deletedUser = users().stream()
                .filter(user -> id.equals(Integer.toString(user.getId())))
                .findFirst()
                .orElse(null);

        users.remove(deletedUser);

        return "User id " + id + " deleted.";
    }
}
