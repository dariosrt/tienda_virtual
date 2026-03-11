package com.example.dario.filters;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
import com.example.dario.model.Producto;
import com.example.dario.service.AuthService;

@WebFilter(urlPatterns = { "/index.html", "/" })
public class FiltroInicio implements Filter{
    


    private AuthService authService = new AuthService();

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session = httpReq.getSession();

        if (session.getAttribute("productos") == null) {
            ProductosDAO pDao = new ProductosDAO();
            ArrayList<Producto> productos = new ArrayList<>();
            productos = pDao.allProductos();
            session.setAttribute("productos", productos);
        }
        req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
        return;
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
