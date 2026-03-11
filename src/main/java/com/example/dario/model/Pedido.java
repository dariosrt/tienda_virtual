package com.example.dario.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

import com.example.dario.model.Enums.EstadoEnum;
import com.example.dario.model.LineaPedido;

public class Pedido {

    private Long idPedido;
    private Date fecha;
    private EstadoEnum estado; // "c" o "f"
    private Long idUsuario;
    private BigDecimal importe;
    private BigDecimal iva;
    private ArrayList<LineaPedido> lineas;

    public Pedido(){
        this.lineas = new ArrayList<>();
    }

    public Pedido(Long idPedido, Date fecha, EstadoEnum estado, Long idUsuario, BigDecimal importe, BigDecimal iva) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.importe = importe;
        this.iva = iva;
        this.lineas = new ArrayList<>();
    }

    public Long getIdPedido() {
        return this.idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoEnum getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public BigDecimal getImporte() {
        return this.importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getIva() {
        return this.iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public ArrayList<LineaPedido> getLineas() {
        return this.lineas;
    }

    public void setLineas(ArrayList<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public BigDecimal getTotal() {
        if (this.importe == null) return BigDecimal.ZERO;
        if (this.iva == null) return this.importe;
        return this.importe.add(this.iva);
    }

}
