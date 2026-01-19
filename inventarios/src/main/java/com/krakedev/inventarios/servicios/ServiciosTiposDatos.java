package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.message.internal.MediaTypes;
import com.krakedev.inventarios.bdd.TiposDatosBDD;
import com.krakedev.inventarios.entidades.TipoDato;
import com.krakedev.inventarios.excepciones.KrakeDevException;


@Path("tiposdocumentos")
public class ServiciosTiposDatos {
	@Path("recuperar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar(){
		TiposDatosBDD TipoDatoBDD=new TiposDatosBDD();
		ArrayList<TipoDato> TiposDatos=null;
		try {
			
			TiposDatos = TipoDatoBDD.Buscar();
			return Response.ok(TiposDatos).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}

}
