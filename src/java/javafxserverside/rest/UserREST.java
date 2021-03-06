/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxserverside.rest;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxserverside.ejb.UserManagerEJBLocal;
import javafxserverside.entity.Department;
import javafxserverside.entity.Profile;
import javafxserverside.entity.User;
import javafxserverside.exception.CreateException;
import javafxserverside.exception.DeleteException;
import javafxserverside.exception.ReadException;
import javafxserverside.exception.UpdateException;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author javi
 */
@Path("users")
public class UserREST{

    private static final Logger LOGGER =
            Logger.getLogger("javafxserverside");

    @EJB
    private UserManagerEJBLocal ejb;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create(User user) {
        try {
            LOGGER.log(Level.INFO,"UserREST: create {0}.",user);
            ejb.createUser(user);
        } catch (CreateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @PUT
    @Consumes({"application/xml", "application/json"})
    public void update(User user) {
        try {
            LOGGER.log(Level.INFO,"UserREST: update {0}.",user);
            ejb.updateUser(user);
        } catch (UpdateException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    @DELETE
    @Path("{id}")
    //@Consumes({"application/xml", "application/json"})
    public void delete(@PathParam("id") String id) {
        try {
            LOGGER.log(Level.INFO,"UserREST: delete User by id={0}.",id);
            ejb.deleteUser(ejb.findUserByLogin(id));
        } catch (ReadException | DeleteException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") String id) {
        User user=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: find User by id={0}.",id);
            user=ejb.findUserByLogin(id);
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return user;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
        List<User> users=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: find all users.");
            users=ejb.findAllUsers();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return users;
    }

    @GET
    @Path("profile/{profile}")
    @Produces({"application/xml", "application/json"})
    public List<User> findUsersByProfile(@PathParam("profile") String profile) {
        List<User> users=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: find users by profile {0}.",profile);
            users=ejb.findUsersByProfile(Profile.valueOf(profile));
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    @GET
    @Path("departments")
    @Produces({"application/xml", "application/json"})
    public List<Department> findAllDepartments() {
        List<Department> departments=null;
        try {
            LOGGER.log(Level.INFO,"UserREST: find departments for users.");
            departments=ejb.findAllDepartments();
        } catch (ReadException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return departments;
    }

}
