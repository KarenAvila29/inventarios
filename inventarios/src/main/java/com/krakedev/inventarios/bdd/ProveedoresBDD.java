package com.krakedev.inventarios.bdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDato;
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
			ps=con.prepareStatement("select prov.identificacion,prov.tipo_docum, td.descripcion, prov.nombre,prov.telefono,prov.correo,prov.direccion "
					+ "from proveedor prov, tipo_documento td "
					+"where  prov.tipo_docum = td.codigo "
					+ "and upper(nombre) like ?");
			ps.setString(1, "%"+subcadena.toUpperCase()+"%");
			rs=ps.executeQuery();
			
			while(rs.next()) {
				String identificacion=rs.getString("identificacion");
				String codigoTipoDocumento=rs.getString("tipo_docum");
				String descripcionTipoDocumento=rs.getString("descripcion");
				String nombre=rs.getString("nombre");
				String telefono=rs.getString("telefono");
				String correo=rs.getString("correo");
				String direccion=rs.getString("direccion");
				TipoDato td=new TipoDato(codigoTipoDocumento,descripcionTipoDocumento);
				proveedor=new Proveedor(identificacion,td,nombre,telefono,correo,direccion);
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

	
	
	
	public void crear(Proveedor proveedor) throws KrakeDevException{
		Connection con=null;
		try {
			con=ConexionBdd.obtenerConexion();
			PreparedStatement ps=con.prepareStatement("insert into proveedor(identificacion,tipo_docum, nombre,telefono,correo,direccion) values(?,?,?,?,?,?)");
			
		ps.setString(1, proveedor.getIdentificador());
		ps.setString(2, proveedor.getTipoDocumento().getCodigo());
		ps.setString(3, proveedor.getNombre());
		ps.setString(4, proveedor.getTelefono());
		ps.setString(5, proveedor.getCorreo());
		ps.setString(6, proveedor.getDireccion());
		
		ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakeDevException("Error al insertar el cliente. Detalle: " + e.getMessage());
	
		} catch (KrakeDevException e) {
			throw e;
		}finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
