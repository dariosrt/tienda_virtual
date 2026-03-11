package com.example.dario.model;

import java.math.BigDecimal;

import com.example.dario.DB.DAOs.ProductosDAO;

public class Producto {
    
    private Long idProducto;
    private Long idCategoria;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String marca;
    private String imagen;


    public Producto(){
    }
    public Producto(Long idProducto, Long idCategoria, String nombre, String descripcion, BigDecimal precio, String marca, String imagen) {
        this.idProducto = idProducto;
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
        this.imagen = imagen;
    }


    public Long getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Long getIdCategoria() {
        return this.idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String nombreCategoria(){
        
        return ProductosDAO.nombreCategoria(this.idCategoria);
    }


}
