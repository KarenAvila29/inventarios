package com.krakedev.inventarios.servicios;

import javax.ws.rs.Consumes;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.krakedev.inventarios.bdd.VentasBDD;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;

@Path("ventas")
public class ServiciosVentas {
	@Path("insertar")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertar(Venta venta) {
        System.out.println("VENTA >>> " + venta);

        VentasBDD bdd = new VentasBDD();
        try {
            bdd.insertarVenta(venta);
            return Response.ok().build();
        } catch (KrakeDevException e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

}
