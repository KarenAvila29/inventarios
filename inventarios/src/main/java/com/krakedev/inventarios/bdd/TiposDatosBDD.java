package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.TipoDato;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBdd;



public class TiposDatosBDD {
	
	public ArrayList<TipoDato> Buscar() throws KrakeDevException{
		ArrayList<TipoDato> TiposDatos=new ArrayList<TipoDato>();
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		TipoDato  tipoDato=null;
		try {
			con=ConexionBdd.obtenerConexion();
			ps=con.prepareStatement("select upper(codigo) as codigo, descripcion from tipo_documento ");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String codigo=rs.getString("codigo");
				String descripcion=rs.getString("descripcion");
			
				
				tipoDato=new TipoDato(codigo,descripcion);
				TiposDatos.add(tipoDato);
			}
		} catch (KrakeDevException e) {
			
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al consultar. Detalle: " + e.getMessage());
		}
		return TiposDatos;
	}

}
