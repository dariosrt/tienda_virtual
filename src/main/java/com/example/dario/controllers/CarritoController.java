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

import com.example.dario.model.Carrito;

@WebServlet(urlPatterns = { "/carrito" })
public class CarritoController extends HttpServlet {
    


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("v_carrito.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long producto = null;

        HttpSession session = req.getSession(false);

        String action = req.getParameter("action");
        try {
            producto = (long) Integer.parseInt(req.getParameter("producto"));
        } catch (Exception e) {
            req.getRequestDispatcher("v_carrito.jsp").forward(req, resp);
            return;
        }

        switch (action) {
            case "comprar":

                Carrito carrito = (Carrito) session.getAttribute("carrito");
                carrito.addProducto(producto);
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
                return;
            default:
                break;

        }
    }


}

