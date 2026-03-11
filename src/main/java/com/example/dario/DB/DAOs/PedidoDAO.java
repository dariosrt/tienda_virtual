package com.example.dario.DB.DAOs;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.dario.DB.Conexion;
import com.example.dario.model.Pedido;
import com.example.dario.model.Enums.EstadoEnum;
import com.example.dario.model.LineaPedido;

public class PedidoDAO {
    
    public Long insertarPedido(Long idUsuario, BigDecimal importe, BigDecimal iva) {
        String sql = "INSERT INTO pedidos (fecha, estado, id_usuario, importe, iva) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setDate(1, new Date(System.currentTimeMillis()));
            pstmt.setString(2, "c");
            pstmt.setLong(3, idUsuario);
            pstmt.setBigDecimal(4, importe);
            pstmt.setBigDecimal(5, iva);
            
            int resultado = pstmt.executeUpdate();
            
            if (resultado > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getLong(1);
                    }
                }
            }
            return null;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertarLineaPedido(Long idPedido, Long idProducto, Integer cantidad) {
        String sql = "INSERT INTO lineaspedidos (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
        
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setLong(1, idPedido);
            pstmt.setLong(2, idProducto);
            pstmt.setInt(3, cantidad);
            
            int resultado = pstmt.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Pedido> obtenerPedidosPorUsuario(Long idUsuario) {
        String sql = "SELECT id_pedido, fecha, estado, id_usuario, importe, iva FROM pedidos WHERE id_usuario = ? ORDER BY fecha DESC";
        ArrayList<Pedido> pedidos = new ArrayList<>();
        
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setLong(1, idUsuario);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getLong("id_pedido"));
                    pedido.setFecha(rs.getDate("fecha"));
                    pedido.setEstado(EstadoEnum.valueOf(rs.getString("estado")));
                    pedido.setIdUsuario(rs.getLong("id_usuario"));
                    pedido.setImporte(rs.getBigDecimal("importe"));
                    pedido.setIva(rs.getBigDecimal("iva"));

                    ArrayList<LineaPedido> lineas = obtenerLineasPedido(rs.getLong("id_pedido"));
                    // rellenar nombre/precio en las lineas si es necesario (ya lo hace obtenerLineasPedido)
                    pedido.setLineas(lineas);
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return pedidos;
    }

    public ArrayList<LineaPedido> obtenerLineasPedido(Long idPedido) {
        String sql = "SELECT lp.id_linea, lp.id_pedido, lp.id_producto, lp.cantidad, p.nombre, p.precio " +
                     "FROM lineaspedidos lp " +
                     "JOIN productos p ON lp.id_producto = p.id_producto " +
                     "WHERE lp.id_pedido = ?";
        ArrayList<LineaPedido> lineas = new ArrayList<>();
        
        try (Connection conexion = Conexion.getConexion();
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            
            pstmt.setLong(1, idPedido);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    LineaPedido linea = new LineaPedido();
                    linea.setIdLinea(rs.getLong("id_linea"));
                    linea.setIdPedido(rs.getLong("id_pedido"));
                    linea.setIdProducto(rs.getLong("id_producto"));
                    linea.setCantidad(rs.getInt("cantidad"));
                    linea.setNombreProducto(rs.getString("nombre"));
                    linea.setPrecioProducto(rs.getBigDecimal("precio"));
                    
                    lineas.add(linea);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lineas;
    }
}
