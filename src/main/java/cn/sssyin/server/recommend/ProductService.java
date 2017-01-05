package cn.sssyin.server.recommend;

import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public interface ProductService {

	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	List<Integer> retrieveAllProducts(@FormParam("store") String store);

	@GET
	@Path("/product/{store}&{id}")
	@Produces(MediaType.APPLICATION_JSON)
	List<Integer> retrieveProductById(@PathParam("store") String store,@PathParam("id") int id);

	@POST
	@Path("/products")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	List<Integer> retrieveProductsByName(@FormParam("store") String store);

}

