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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.dario.DTO.ProductRequest;
import com.example.dario.model.Carrito;


@WebServlet(urlPatterns = { "/carrito/sumar" })
public class CarritoAjaxController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // HttpSession session = req.getSession(false);
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
        carrito.addProducto(productoId);
        session.setAttribute("carrito", carrito);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(carrito);
        objectOutputStream.close();

        String carritoSerializado = Base64.getEncoder()
                .encodeToString(byteArrayOutputStream.toByteArray());

        Cookie carrito_cookie = new Cookie("carrito", carritoSerializado);
        carrito_cookie.setMaxAge(60 * 60 * 24 * 7);
        carrito_cookie.setPath("/");
        resp.addCookie(carrito_cookie);

        String importe = carrito.calcularImporte().toString();
        String iva = carrito.calcularIVA().toString();
        String total = carrito.calcularImporte().add(carrito.calcularIVA()).toString();
        
        resp.getWriter().write("{\"cantidad\": " + carrito.obtenerUnidades(productoId) + ", \"importe\": \"" + importe + "\", \"iva\": \"" + iva + "\", \"total\": \"" + total + "\"}");

    }
}
