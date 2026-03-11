package com.example.dario.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DB.DAOs.ProductosDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.model.Carrito;
import com.example.dario.model.Producto;

@WebServlet(urlPatterns = { "/tienda" })
public class TiendaController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        if(req.getParameter("cerrar_sesion") != null){
            HttpSession session = req.getSession(false); 
            session.removeAttribute("usuario");

            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String cookieName = cookie.getName();
                    if ("guardar_sesion".equals(cookieName)) {
                        cookie.setValue("");
                        cookie.setMaxAge(0);
                        resp.addCookie(cookie);
                    }
                }
            }

            
            // resp.sendRedirect("v_tienda.jsp");
            // return;
        }
        HttpSession session = req.getSession(); 


        if (session.getAttribute("productos") == null) {
            ProductosDAO pDao = new ProductosDAO();
            ArrayList<Producto> productos = new ArrayList<>();
            productos = pDao.allProductos();
            session.setAttribute("productos", productos);
        }


        // resp.sendRedirect("v_tienda.jsp");z
        req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long producto = null;

        HttpSession session = req.getSession();




        String action = req.getParameter("action");
        try {
            producto = Long.parseLong(req.getParameter("producto"));
        } catch (Exception e) {
            req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
            return;
        }

        Carrito carrito = (Carrito) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        switch (action) {
            case "poner":
                // Carrito carrito = (Carrito) session.getAttribute("carrito");
                carrito.addProducto(producto);
                session.setAttribute("carrito", carrito);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(carrito);
                objectOutputStream.close();

                String carritoSerializado = Base64.getEncoder()
                        .encodeToString(byteArrayOutputStream.toByteArray());


                Cookie carrito_cookie = new Cookie("carrito", carritoSerializado);
                carrito_cookie.setMaxAge(60* 60 * 24 * 7);
                resp.addCookie(carrito_cookie);

                req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
                return;

            case "filtrar":
                // Carrito carrito = (Carrito) session.getAttribute("carrito");
                Long id_categoria = Long.parseLong(req.getParameter("filtro_categoria"));

                session.setAttribute("categoriaFiltro", id_categoria);

                req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
                return;
            default:
                break;

        }



    }


}
