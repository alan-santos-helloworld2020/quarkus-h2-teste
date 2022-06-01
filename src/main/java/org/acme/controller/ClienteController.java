/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.acme.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.acme.models.Cliente;

/**
 *
 * @author alan
 */
@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteController {

    @GET
    @Path("/")
    public Response findAll() {
        List<Cliente> clientes = Cliente.listAll();
        System.out.print(Cliente.count());
        return Response.ok(clientes).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Optional<Cliente> cliente = Cliente.findByIdOptional(id);
        if (cliente.isPresent()) {
            return Response.ok(cliente).build();
        } else {
            return Response.status(403).build();
        }
    }

    @POST
    @Transactional
    public Response save(Cliente cliente) {
        cliente.persist();
        return Response.ok(cliente).status(201).build();

    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Cliente cliente) {
        Optional<Cliente> old = Cliente.findByIdOptional(id);
        if (old.isPresent()) {
            old.get().nome = cliente.nome;
            old.get().telefone = cliente.telefone;
            old.get().email = cliente.email;
            old.get().cep = cliente.cep;
            old.get().persist();
            return Response.status(201).build();
        } else {
            return Response.status(403).build();
        }

    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Cliente cliente = Cliente.findById(id);
        if (cliente == null) {
            throw new NotFoundException();
        }
        cliente.delete();
    }

}
