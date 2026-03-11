package com.example.dario.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DTO.ProductRequest;
import com.example.dario.model.Carrito;
import com.fasterxml.jackson.databind.ObjectMapper;    



@WebServlet(urlPatterns = { "/carrito/restar" })
public class CarritoAjaxRestar extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        ProductRequest data = mapper.readValue(req.getInputStream(), ProductRequest.class);
        Long productoId = data.getProducto();

        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
        }

        if (carrito.productoExistente(productoId)) {
            Integer unidades = carrito.obtenerUnidades(productoId);
            if (unidades > 1) {
                carrito.cesta.put(productoId, unidades - 1);
            } else {
                carrito.cesta.remove(productoId);
            }
        }

        session.setAttribute("carrito", carrito);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(carrito);
        oos.close();

        String carritoSerializado = Base64.getEncoder().encodeToString(baos.toByteArray());
        Cookie carritoCookie = new Cookie("carrito", carritoSerializado);
        carritoCookie.setMaxAge(60 * 60 * 24 * 7);
        carritoCookie.setPath("/");
        resp.addCookie(carritoCookie);

        String importe = carrito.calcularImporte().toString();
        String iva = carrito.calcularIVA().toString();
        String total = carrito.calcularImporte().add(carrito.calcularIVA()).toString();
        
        resp.getWriter().write("{\"cantidad\": " + carrito.obtenerUnidades(productoId) + ", \"importe\": \"" + importe + "\", \"iva\": \"" + iva + "\", \"total\": \"" + total + "\"}");

    }
}

