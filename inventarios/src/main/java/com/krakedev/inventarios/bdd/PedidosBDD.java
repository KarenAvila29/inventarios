package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBdd;

public class PedidosBDD {

	
	public void insertar(Pedido pedido) throws KrakeDevException{
		Connection con=null;
		PreparedStatement ps=null;
		PreparedStatement psDet=null;
		ResultSet rsClave=null;
		int codigoCabecera=0;
		Date fechaActual=new Date();
		java.sql.Date fechaSQL=new java.sql.Date(fechaActual.getTime());
		try {
			con=ConexionBdd.obtenerConexion();
			 ps=con.prepareStatement( "insert into cabecera_pedido(proveedor, fecha, estado) values (?, ?, ?) returning numero");
			ps.setString(1,pedido.getProveedor().getIdentificador() );
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");
			
		//ps.executeUpdate();
		rsClave = ps.executeQuery();
		
		if(rsClave.next()) {
		codigoCabecera=rsClave.getInt("numero");
		}
		//BARRIDO DEL DETALLE
		ArrayList<DetallePedido> detallesPedidos=pedido.getDetalles();
		DetallePedido det;
		for(int i=0;i<detallesPedidos.size();i++) {
			det = detallesPedidos.get(i);
			psDet=con.prepareStatement("insert into detalle_pedido(cabecera_ped,producto,cantidad,subtotal,cantidad_recibida) "
					+ "values (?,?,?,?,?);");
			psDet.setInt(1, codigoCabecera);
			psDet.setString(2, det.getProducto().getCodigo_pro());
			psDet.setInt(3, det.getCantidadSolicitada());
			
				BigDecimal pv=det.getProducto().getPrecioVenta();
				BigDecimal cantidad=new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal=pv.multiply(cantidad);
			psDet.setBigDecimal(4, subtotal);
			psDet.setInt(5, 0);
			psDet.executeUpdate();
		}
		
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
	
	public void recibir(Pedido pedido) throws KrakeDevException {

	    Connection con = null;
	    PreparedStatement ps = null;
	    PreparedStatement psDet = null;
	    PreparedStatement psHist = null;

	    try {
	        con = ConexionBdd.obtenerConexion();
	        con.setAutoCommit(false);

	        // 1. Cambiar estado a RECIBIDO
	        ps = con.prepareStatement(
	            "UPDATE cabecera_pedido SET estado = 'R' WHERE numero = ?");
	        ps.setInt(1, pedido.getCodigo());
	        ps.executeUpdate();

	        // 2. Recorrer detalles con for clásico
	        ArrayList<DetallePedido> detallesPedidos = pedido.getDetalles();
	        for (int i = 0; i < detallesPedidos.size(); i++) {
	            DetallePedido det = detallesPedidos.get(i);

	            BigDecimal precio = det.getProducto().getPrecioVenta();
	            BigDecimal cantidad = new BigDecimal(det.getCantidadRecibida());
	            BigDecimal subtotal = precio.multiply(cantidad);

	            // actualizar detalle
	            psDet = con.prepareStatement(
	                "UPDATE detalle_pedido " +
	                "SET cantidad_recibida=?, subtotal=? " +
	                "WHERE cabecera_ped=? AND producto=?");

	            psDet.setInt(1, det.getCantidadRecibida());
	            psDet.setBigDecimal(2, subtotal);
	            psDet.setInt(3, pedido.getCodigo());
	            psDet.setString(4, det.getProducto().getCodigo_pro());
	            psDet.executeUpdate();

	            // registrar historial dentro del mismo for
	            psHist = con.prepareStatement(
	                "INSERT INTO historial_stock(fecha, referencia, producto_id, cantidad) " +
	                "VALUES (?, ?, ?, ?)");

	            psHist.setDate(1, new java.sql.Date(new Date().getTime()));
	            psHist.setString(2, "PEDIDO " + pedido.getCodigo());
	            psHist.setString(3, det.getProducto().getCodigo_pro());
	            psHist.setInt(4, det.getCantidadRecibida());
	            psHist.executeUpdate();
	        }

	        con.commit();

	    } catch (SQLException e) {
	        try { if (con != null) con.rollback(); } catch (SQLException ex) {}
	        throw new KrakeDevException("Error al recibir pedido: " + e.getMessage());

	    } finally {
	        try { if (con != null) con.close(); } catch (SQLException e) {}
	    }
	}


}
