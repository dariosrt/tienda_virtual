package com.example.dario.model;

import java.math.BigDecimal;

public class LineaPedido {

    private Long idLinea;
    private Long idPedido;
    private Long idProducto;
    private Integer cantidad;
    private String nombreProducto;
    private BigDecimal precioProducto;

    public LineaPedido(){

    }

    public LineaPedido(Long idLinea, Long idPedido, Long idProducto, Integer cantidad) {
        this.idLinea = idLinea;
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    public Long getIdLinea() {
        return this.idLinea;
    }

    public void setIdLinea(Long idLinea) {
        this.idLinea = idLinea;
    }

    public Long getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Long getIdProducto() {
        return this.idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public BigDecimal getSubtotal() {
        if (precioProducto == null || cantidad == null) return BigDecimal.ZERO;
        return precioProducto.multiply(new BigDecimal(cantidad)).setScale(2, java.math.RoundingMode.HALF_UP);
    }

}
