package com.smurf.SmurfShop.rest;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.smurf.SmurfShop.data.SmurfDAO;
import com.smurf.SmurfShop.model.Smurf;



@Path("/smurfs")
@Stateless
@LocalBean
public class SmurfWS {

	@EJB
	private SmurfDAO smurfDao;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAllSmurfs() {
		List<Smurf> smurfs=smurfDao.getAllSmurf();
		return Response.status(200).entity(smurfs).build();
	}
	

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findSmurfById(@PathParam("id") int id) {
		Smurf smurf = smurfDao.getSmurf(id);
		return Response.status(200).entity(smurf).build();
	}
	
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveSmurf(Smurf smurf) {
		smurfDao.save(smurf);
		return Response.status(201).entity(smurf).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateSmurf(Smurf smurf, @PathParam("id") int id) {
		System.out.println("----------id is " + smurf.getId());
		smurf.setId(id);
		System.out.println("----------id is " + smurf.getId());
		smurfDao.update(smurf);
		System.out.println("----------id is " + smurf.getId() + smurf.getInstock());
		
		return Response.status(200).entity(smurf).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteSmurf(@PathParam("id") int id) {
		smurfDao.delete(id);
		return Response.status(204).build();
	}
	
	@GET
	@Path("/search/{query}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByName(@PathParam("query") String query) {
		System.out.println("findByName: " + query);
		List<Smurf> smurfs=smurfDao.getSmurfByName(query);
		return Response.status(200).entity(smurfs).build();
	}
}

