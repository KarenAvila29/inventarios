package com.krakedev.inventarios.servicios;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.ProductoBDD;
import com.krakedev.inventarios.entidades.Producto;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("productos")
public class ServiciosProductos {
	@Path("buscar/{subcadena}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscar( @PathParam("subcadena")String subcadena){
		ProductoBDD prodBDD=new ProductoBDD();
		ArrayList<Producto> producto=null;
		try {
			producto = prodBDD.Buscar(subcadena);
			return Response.ok(producto).build();
		} catch (KrakeDevException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}


}
