package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.DetalleVenta;
import com.krakedev.inventarios.entidades.Venta;
import com.krakedev.inventarios.excepciones.KrakeDevException;
import com.krakedev.inventarios.utils.ConexionBdd;

public class VentasBDD {
	
	public void insertarVenta(Venta venta) throws KrakeDevException {

	    Connection con = null;
	    PreparedStatement ps = null;
	    PreparedStatement psDet = null;
	    PreparedStatement psHist = null;
	    ResultSet rsClave = null;

	    int codigoCabecera = 0;
	    Date fechaActual = new Date();
	    java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

	    BigDecimal totalSinIva = BigDecimal.ZERO;
	    BigDecimal totalIva = BigDecimal.ZERO;

	    try {
	        con = ConexionBdd.obtenerConexion();
	        con.setAutoCommit(false);

	        // 1. INSERTAR CABECERA
	        ps = con.prepareStatement(
	            "INSERT INTO cabecera_venta(fecha, subtotal, iva, total) " +
	            "VALUES (?, ?, ?, ?) RETURNING codigo");

	        ps.setDate(1, fechaSQL);
	        ps.setBigDecimal(2, BigDecimal.ZERO);
	        ps.setBigDecimal(3, BigDecimal.ZERO);
	        ps.setBigDecimal(4, BigDecimal.ZERO);

	        rsClave = ps.executeQuery();
	        if (rsClave.next()) {
	            codigoCabecera = rsClave.getInt("codigo");
	        }

	        // 2. BARRIDO DEL DETALLE
	        ArrayList<DetalleVenta> detalles = venta.getDetalle();

	        for (int i = 0; i < detalles.size(); i++) {
	            DetalleVenta det = detalles.get(i);

	            // Ver si el producto tiene IVA
	            ps = con.prepareStatement(
	                "SELECT tiene_iva FROM producto WHERE codigo_pro = ?");
	            ps.setString(1, det.getProducto().getCodigo_pro());

	            ResultSet rsProd = ps.executeQuery();
	            if (!rsProd.next()) {
	                throw new KrakeDevException("Producto no existe: " +
	                        det.getProducto().getCodigo_pro());
	            }

	            boolean tieneIva = rsProd.getBoolean("tiene_iva");

	            BigDecimal precio = det.getPrecioVenta();
	            BigDecimal cantidad = new BigDecimal(det.getCantidad());
	            BigDecimal subtotal = precio.multiply(cantidad);

	            BigDecimal subtotalConIva = subtotal;

	            if (tieneIva) {
	                BigDecimal ivaDetalle = subtotal.multiply(new BigDecimal("0.15"));
	                subtotalConIva = subtotal.add(ivaDetalle);
	                totalIva = totalIva.add(ivaDetalle);
	            }

	            totalSinIva = totalSinIva.add(subtotal);

	            // INSERTAR DETALLE
	            psDet = con.prepareStatement(
	                "INSERT INTO detalle_venta " +
	                "(cabecera, producto, cantidad, precio_venta, subtotal, total_iva) " +
	                "VALUES (?, ?, ?, ?, ?, ?)");

	            psDet.setInt(1, codigoCabecera);
	            psDet.setString(2, det.getProducto().getCodigo_pro());
	            psDet.setInt(3, det.getCantidad());
	            psDet.setBigDecimal(4, precio);
	            psDet.setBigDecimal(5, subtotal);
	            psDet.setBigDecimal(6, subtotalConIva);
	            psDet.executeUpdate();

	            // HISTORIAL STOCK (SALIDA)
	            psHist = con.prepareStatement(
	                "INSERT INTO historial_stock(fecha, referencia, producto_id, cantidad) " +
	                "VALUES (?, ?, ?, ?)");

	            psHist.setDate(1, fechaSQL);
	            psHist.setString(2, "VENTA " + codigoCabecera);
	            psHist.setString(3, det.getProducto().getCodigo_pro());
	            psHist.setInt(4, det.getCantidad() * -1);
	            psHist.executeUpdate();
	        }

	        // 3. ACTUALIZAR CABECERA
	        ps = con.prepareStatement(
	            "UPDATE cabecera_venta " +
	            "SET subtotal=?, iva=?, total=? " +
	            "WHERE codigo=?");

	        ps.setBigDecimal(1, totalSinIva);
	        ps.setBigDecimal(2, totalIva);
	        ps.setBigDecimal(3, totalSinIva.add(totalIva));
	        ps.setInt(4, codigoCabecera);
	        ps.executeUpdate();

	        con.commit();

	    } catch (SQLException e) {
	        try { if (con != null) con.rollback(); } catch (SQLException ex) {}
	        throw new KrakeDevException("Error al insertar venta. Detalle: " + e.getMessage());

	    } finally {
	        try { if (con != null) con.close(); } catch (SQLException e) {}
	    }
	}


}
