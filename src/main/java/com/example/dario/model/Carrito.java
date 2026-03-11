package com.example.dario.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.dario.DB.DAOs.ProductosDAO;

public class Carrito implements Serializable{
    
    public HashMap<Long, Integer> cesta;


    public Carrito(){
        cesta =new HashMap<Long, Integer>();
    }
    public HashMap<Long,Integer> getCesta() {
        return this.cesta;
    }

    public void setCesta(HashMap<Long,Integer> cesta) {
        this.cesta = cesta;
    }

    public void vaciarCarrito(){
        this.cesta.clear();
    }

    public Integer obtenerUnidades(Long id_producto){
    
        if(this.cesta.containsKey(id_producto)){
            return this.cesta.get(id_producto);
        }
        return 0;
    }

    public boolean productoExistente(Long id_producto){
        // for (Long producto : this.cesta.keySet()) {
        //     if(producto == id_producto){
        //         return true;
        //     }
        // }
        return this.cesta.containsKey(id_producto);
    }

    public void addProducto(Long id_producto){
        if(productoExistente(id_producto)){
            this.cesta.replace(id_producto, this.cesta.get(id_producto) +1);
        }
        else{
            this.cesta.put(id_producto, 1);
        }
    }
    public ArrayList<Producto> obtenerProductos(){
        ProductosDAO productosDAO = new ProductosDAO();
        ArrayList<Producto> productos = new ArrayList<>();
        productos = productosDAO.allProductos();
    
        return productos;
    }
    
    public ArrayList<Producto> obtenerProductos(Long categoria, String nombre){
        ProductosDAO productosDAO = new ProductosDAO();
        ArrayList<Producto> productos = new ArrayList<>();
        productos = productosDAO.allProductos();
        if(categoria != null ){
            for (Producto producto : productos) {
                if(producto.getIdCategoria() != categoria){
                    productos.remove(producto);
                }
            }
        }
        if(nombre != null && !nombre.trim().isEmpty()){
            for (Producto producto : productos) {
                if(categoria.equals(producto.getIdCategoria())){
                    productos.remove(producto);
                }
            }
        }
        return productos;
    }

    public ArrayList<Producto> productos(){
        ProductosDAO productosDAO = new ProductosDAO();
        ArrayList<Producto> productos = new ArrayList<>();
        ArrayList<Producto> productos_cesta = new ArrayList<>();

        productos = productosDAO.allProductos();
        
        for (Producto producto : productos) {
            if(this.cesta.containsKey(producto.getIdProducto())){
                productos_cesta.add(producto);
            }
        }

        return productos_cesta;
    }

    public void quitarProducto(Long id_producto){
        if(this.cesta.containsKey(id_producto)){
            int unidades = this.cesta.get(id_producto) - 1;
            if(unidades <= 0){
                this.cesta.remove(id_producto);
            } else {
                this.cesta.put(id_producto, unidades);
            }
        }
    }

    public BigDecimal calcularImporte(){
        ArrayList<Producto> productos = productos();
        BigDecimal importe = BigDecimal.ZERO;

        for (Producto producto : productos) {
            if(this.cesta.containsKey(producto.getIdProducto())){
                Integer cantidad = this.cesta.get(producto.getIdProducto());
                BigDecimal subtotal = producto.getPrecio().multiply(new BigDecimal(cantidad));
                importe = importe.add(subtotal);
            }
        }
        return importe.setScale(2, java.math.RoundingMode.HALF_UP);
    }

    public BigDecimal calcularIVA(){
        BigDecimal importe = calcularImporte();
        BigDecimal iva = importe.multiply(new BigDecimal("0.21"));
        return iva.setScale(2, java.math.RoundingMode.HALF_UP);
    }
    

}
