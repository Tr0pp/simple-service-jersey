package com.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

@Path("users")
public class UsersResource {

    UserRepository repository = new UserRepository();
    private static List<User> users = new ArrayList<User>();

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> users() {
        List<User> users = repository.getUsers();
        return users;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User users(@PathParam("id") int id) {
        User user = repository.getUser(id);
        return user;
    }

    @POST
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User save(User user) {
        User createdUser = repository.create(user);
        return createdUser;
    }

    @PATCH
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User update(User user) {
        User updatedUser = repository.update(user);
        return updatedUser;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String delete(@PathParam("id") int id) {
      User user = repository.getUser(id);

      if(repository.delete(user)){
          return "User "+ user.getName()+" deleted";
      } else {
          return "Not Found.";
      }
    }

}
