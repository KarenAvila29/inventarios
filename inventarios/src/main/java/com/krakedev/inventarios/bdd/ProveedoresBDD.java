package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBdd;

public class ProveedoresBDD {
	public ArrayList<Proveedor> Buscar(String subcadena) throws KrakeDevException{
		ArrayList<Proveedor> proveedores=new ArrayList<Proveedor>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Proveedor  proveedor=null;
		try {
			con=ConexionBdd.obtenerConexion();
			ps=con.prepareStatement("select identificacion,tipo_docum,nombre,telefono,correo,direccion "
					+ "from proveedor "
					+ "where upper(nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String identificacion=rs.getString("identificacion");
				String tipo_docum=rs.getString("tipo_docum");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");
				
				proveedor=new Proveedor(identificacion,tipo_docum,nombre,telefono,correo,direccion);
				proveedores.add(proveedor);
			}
		} catch (KrakeDevException e) {
			
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
		return proveedores;
	}

}
