package com.example.dario.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DB.DAOs.ProductosDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.DTO.UsuarioSesionDTO;
import com.example.dario.model.Carrito;
import com.example.dario.model.Producto;
import com.example.dario.service.AuthService;

@WebFilter(urlPatterns = { "/*" })
public class FiltroSesion implements Filter {

    private AuthService authService = new AuthService();
    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";
    @Override
    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = httpReq.getSession();

        Cookie[] cookies = httpReq.getCookies();
        if (session.getAttribute("usuario") == null) {
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("guardar_sesion".equals(cookie.getName())) {
                        try {
                            UsuarioLoginDTO usuario = authService.findByUsername(cookie.getValue());
                            session.setAttribute("usuario", usuario);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                    }
                }
            }
        }

        if (session.getAttribute("productos") == null) {
            ProductosDAO pDao = new ProductosDAO();
            ArrayList<Producto> productos = new ArrayList<>();
            productos = pDao.allProductos();
            session.setAttribute("productos", productos);
        }


        if (session.getAttribute("carrito") == null) {

            Carrito carrito = null;
            Cookie[] cookiesCarrito = httpReq.getCookies();

            if (cookiesCarrito != null) {
                for (Cookie cookie : cookiesCarrito) {
                    if ("carrito".equals(cookie.getName())) {
                        try {
                            byte[] datos = Base64.getDecoder().decode(cookie.getValue());
                            ObjectInputStream ois = new ObjectInputStream(
                                    new ByteArrayInputStream(datos));
                            carrito = (Carrito) ois.readObject();
                            ois.close();
                        } catch (Exception e) {
                            carrito = new Carrito();
                        }
                    }
                }
            }
            if (carrito == null) {
                carrito = new Carrito();
            }

            session.setAttribute("carrito", carrito);
        }



        chain.doFilter(req, resp);
    }


    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}