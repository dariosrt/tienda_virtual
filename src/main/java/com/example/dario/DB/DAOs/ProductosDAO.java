package com.example.dario.DB.DAOs;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.dario.DB.Conexion;
import com.example.dario.model.Producto;

public class ProductosDAO{



    
    public Producto findByProductname(String productname) {
        String sql = """
                SELECT 
                    id_producto,
                    id_categoria,
                    nombre,
                    descripcion,
                    precio,
                    marca,
                    imagen
                FROM user
                WHERE nombre = ?
                """;
        try (Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, productname);
            ResultSet rs = ps.executeQuery();
            Producto product = null;
            if (rs.next()){
                product=new Producto();
                product.setIdProducto(rs.getLong("id_producto"));
                product.setIdCategoria(rs.getLong("id_categoria"));
                product.setNombre(rs.getString("nombre"));
                product.setDescripcion(rs.getString("descripcion"));
                product.setPrecio(rs.getBigDecimal("precio"));
                product.setMarca(rs.getString("marca"));
                product.setImagen(rs.getString("imagen"));
            }
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Producto> allProductos() {
        String sql = """
                SELECT 
                    id_producto,
                    id_categoria,
                    nombre,
                    descripcion,
                    precio,
                    marca,
                    imagen
                FROM productos
                """;

        ArrayList<Producto> productos = new ArrayList<>();

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto product = new Producto();
                product.setIdProducto(rs.getLong("id_producto"));
                product.setIdCategoria(rs.getLong("id_categoria"));
                // product.setNombreCategoria(rs.getString("nombre_categoria"));
                product.setNombre(rs.getString("nombre"));
                product.setDescripcion(rs.getString("descripcion"));
                product.setPrecio(rs.getBigDecimal("precio"));
                product.setMarca(rs.getString("marca"));
                product.setImagen(rs.getString("imagen"));

                productos.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public static String nombreCategoria(Long id_categoria) {
        String sql = """
                SELECT 
                    nombre
                FROM categorias where id_categoria = ?
                """;
        try (Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id_categoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getString("nombre");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }

    // public static String nombre(String nombre) {
    //     String sql = """
    //             SELECT 
    //                 nombre
    //             FROM productos where nombre = ?
    //             """;
    //     try (Connection con = Conexion.getConexion();
    //         PreparedStatement ps = con.prepareStatement(sql)) {

    //         ps.setString(1, nombre);
    //         ResultSet rs = ps.executeQuery();
    //         if (rs.next()){
    //             return rs.getString("nombre");
    //         }
    //         return null;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    


}