package com.smurf.SmurfShop.rest;

import java.util.List;
import java.util.logging.Logger;

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
	
	private final static Logger LOG = Logger.getLogger(SmurfWS.class.getName());
	
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
		LOG.info("---------line 55: post /save");
		if(smurf.getId()<1) {smurf.setId( findSmurfMaxId() );}
		smurfDao.save(smurf);
		LOG.info("---------line 58: new smurf id is " +smurf.getId());
		return Response.status(201).entity(smurf).build();
	}
	
	public int findSmurfMaxId() {
		Smurf smurf = smurfDao.getSmurfMaxId().get(0);
		if(smurf.getId()>0) return smurf.getId()+1;
		return 0;
	}
	
	
	@PUT
	@Path("/{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateSmurf(Smurf smurf, @PathParam("id") int id) {
		LOG.info("---------line 73: put");
		System.out.println("----------line 74 id is " + smurf.getId());
		smurf.setId(id);
		System.out.println("----------line 77 id is " + smurf.getId());
		smurfDao.update(smurf);
		System.out.println("----------id is " + smurf.getId() + smurf.getInstock());
		//this is test jenkins
		System.out.println("----------id is " + smurf.getId());
		return Response.status(200).entity(smurf).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteSmurf(@PathParam("id") int id) {
		LOG.info("---------line 87: delete");
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

