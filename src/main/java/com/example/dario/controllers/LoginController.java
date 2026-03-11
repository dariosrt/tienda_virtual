package com.example.dario.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.service.AuthService;
import javax.security.sasl.AuthenticationException;


@WebServlet(urlPatterns = {"/login"})
public class LoginController extends HttpServlet{

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);


        String email = req.getParameter("email");
        String password = req.getParameter("password");

        boolean guardar_session = req.getParameter("guardar_sesion") != null;


 

        if(email == null || email.trim().equals("")){
                req.setAttribute("error", "El Nombre esta vacio");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
        }
        else if (email.length() > 75){
            req.setAttribute("error", "El Nombre supera el número de caracteres máximos permitidos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        if(password == null || password.trim().equals("")){
            req.setAttribute("error_password", "El Nombre esta vacio");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        else if (password.length() > 100){
            req.setAttribute("error", "La contraseña supera el número de caracteres máximos permitidos");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }



        try {

            UsuarioLoginDTO usuario_sesion = authService.login(email, password);

            session.setAttribute("usuario", usuario_sesion);
            if(guardar_session){
                Cookie cookie_sesion = new Cookie("guardar_sesion", email);
                cookie_sesion.setMaxAge(60 * 60 * 24 * 7);

                resp.addCookie(cookie_sesion);
            }

        } catch (AuthenticationException e) {

            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }



        req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);

    }
}



