package com.highway2urhell.rest;

import com.highway2urhell.domain.FilterEntryPath;
import com.highway2urhell.domain.ThunderApp;
import com.highway2urhell.domain.ThunderStat;
import com.highway2urhell.rest.domain.DataAnalysis;
import com.highway2urhell.rest.domain.MessageGlobalStat;
import com.highway2urhell.rest.domain.MessageStat;
import com.highway2urhell.rest.domain.MessageType;
import com.highway2urhell.service.LaunchService;
import com.highway2urhell.service.ThunderAppService;
import com.highway2urhell.service.ThunderStatService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;
import java.util.List;

@Named
@Path("/ThunderApp")
@Api(value = "/ThunderApp", description = "ThunderApp management")
public class ThunderAppRest {

	private static final Logger LOG = LoggerFactory
			.getLogger(ThunderAppRest.class);

	@Inject
	private ThunderAppService thunderAppService;
	@Inject
	private LaunchService launchService;
	@Inject
	private ThunderStatService thunderStatService;


	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Find th")
	@Path("/findThunderAppByToken/{token}")
	public Response findThunderAppByToken(@PathParam("token") String token) {
		LOG.info("Call findThunderAppByToken ");
		ThunderApp th = thunderAppService.findAppByToken(token);
		return Response.status(Status.ACCEPTED).entity(th).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Find all Thunder App")
	@Path("/findAllThunderApp")
	public Response findAll() {
		LOG.info("Call findAllThunder ");
		List<ThunderApp> listThunderApp = thunderAppService.findAll();
		for (ThunderApp app : listThunderApp) {
			MessageStat stat = thunderStatService.analysisStat(app.getToken());
			app.setNumberEntryPoints(stat.getListThunderStat().size());
		}
		return Response.status(Status.ACCEPTED).entity(listThunderApp).build();
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Find findGlobalStat")
	@Path("/findGlobalStat")
	public Response findGlobalStat() {
		LOG.info("Call findGlobalStat ");
		MessageGlobalStat mg = thunderAppService.findMessageGlobalStat();
		return Response.status(Status.ACCEPTED).entity(mg).build();
	}
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Find all Thunder App")
	@Path("/findAllType")
	public Response findAllType() {
		LOG.info("Call findAllType ");
		Collection<MessageType> mt = thunderAppService.findMessageType();
		return Response.status(Status.ACCEPTED).entity(mt).build();
	}



	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Find all paths")
	@Path("/findAllPaths/{token}")
	public Response findAllPaths(@PathParam("token") String token) {
		LOG.info("Call findAllPaths ");
		String msg = launchService.findAllPaths(token);
		return Response.status(Status.ACCEPTED).entity(msg).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation("Launch analysis")
	@Path("/launchAnalysis")
	public Response launchAnalysis(DataAnalysis da) {
		LOG.info("Call launchAnalysis ");
		String msg = launchService.launchAnalysis(da);
		return Response.status(Status.ACCEPTED).entity(msg).build();
	}

	

}
