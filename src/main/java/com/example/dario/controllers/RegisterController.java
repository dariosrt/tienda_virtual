package com.example.dario.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DB.DAOs.UsuarioDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.model.Usuario;
import com.example.dario.service.AuthService;

@WebServlet(urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {

    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* Conprobar que este logueado el usuario */

        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String apellidos = req.getParameter("apellidos");
        String nif = req.getParameter("nif");
        String telefono = req.getParameter("telefono");
        String direccion = req.getParameter("direccion");
        String codigo_postal = req.getParameter("codigo_postal");
        String localidad = req.getParameter("localidad");
        String provincia = req.getParameter("provincia");


        HttpSession session = req.getSession();




            /* --Registrar usuario-- */

        /*Comprobar datos válidos */

        if(username == null || username.trim().isEmpty()){
                req.setAttribute("error", "El Nombre esta vacio");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
        }
        else if (username.length() > 30){
            req.setAttribute("error", "El Nombre supera el número de caracteres máximos permitidos: 30");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        if(password == null || password.trim().isEmpty()){
            req.setAttribute("error_password", "El Nombre esta vacio");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (password.length() < 4){
            req.setAttribute("error", "La contraseña es demasiado pequeña");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (password.length() > 100){
            req.setAttribute("error", "La contraseña supera el número de caracteres máximos permitidos: 100");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }



        if(apellidos == null || apellidos.trim().isEmpty()){
            req.setAttribute("error", "Los apellidos estan vacios");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (apellidos.length() < 5){
            req.setAttribute("error", "Apellidos tiene que tener por lo menos 5 caracteres");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (apellidos.length() > 20){
            req.setAttribute("error", "Los caracteres de apellidos superan el número de caracteres máximos permitidos: 20");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }



        if(nif == null || nif.trim().isEmpty()){
            req.setAttribute("error", "El Nombre esta vacio");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (nif.matches("\\d{8}[A-Z]") == false){
            req.setAttribute("error", "El nif no es correcto");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }


        try {
            if(telefono != null){
                telefono= telefono.trim();
                if (telefono.trim().isEmpty()){
                    telefono = null;
                }
                else if(!telefono.trim().matches("\\d{9}")){
                    req.setAttribute("error", "El número de teléfono debe tener 9 dígitos");
                    req.getRequestDispatcher("register.jsp").forward(req, resp);
                    return;
                }   
            }


        } catch (Exception e) {
            req.setAttribute("error", "El número de teléfono tiene caracteres no permitidos");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }




        if(direccion == null || direccion.trim().isEmpty()){
                req.setAttribute("error", "La dirección esta vacia");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
        }
        else if (direccion.length() < 5){
            req.setAttribute("error", "La dirección introducida es demasiado pequeña (menos de 5 caracteres)");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (direccion.length() > 40){
            req.setAttribute("error", "La dirección supera el número de caracteres máximos permitidos: 40");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }



        if(codigo_postal == null || codigo_postal.trim().isEmpty()){
                req.setAttribute("error", "El Codigo postal esta vacio");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
        }
        else if (!codigo_postal.matches("\\d{5}")){
            req.setAttribute("error", "El código postal no contiene 5 números");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }



        if(localidad == null || localidad.trim().isEmpty()){
                req.setAttribute("error_localidad", "La localidad esta vacia");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
        }
        else if (localidad.length() < 5){
            req.setAttribute("error", "La localidad esta incompleta, tiene menos de 5 caracteres");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (localidad.length() > 40){
            req.setAttribute("error", "La loclidad supera el número de caracteres: 40");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        

        if(provincia == null || provincia.trim().isEmpty()){
                req.setAttribute("error", "La provincia esta vacia");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
        }
        else if (provincia.length() > 30){
            req.setAttribute("error", "La provincia supera el número de caracteres máximos permitidos: 30");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }




        if(email == null || email.trim().isEmpty()){
            req.setAttribute("error", "El Email esta vacio");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            req.setAttribute("error", "El email no es correcto");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (email.length() < 6){
            req.setAttribute("error", "El email es muy corto (menos de 6 caracteres)");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        else if (email.length() > 75){
            req.setAttribute("error", "El email es muy grande (menos de 75 caracteres)");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }


        /*Comprobar no existe ningún usuario con igual email */

        if(UsuarioDAO.email_existente(email)){
            req.setAttribute("error", "El email ya pertenece a un usuario existente");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }
        if(UsuarioDAO.nif_existente(nif)){
            req.setAttribute("error", "El NIF ya pertenece a un usuario existente");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

               
    
        Usuario usuario_register = authService.signUp(email, password, username, apellidos, nif, telefono, direccion, codigo_postal, localidad, provincia);
        
        if(usuario_register == null){

            req.setAttribute("error", "Ha habido un error al intentar crear el usuario en la Base de datos");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        UsuarioLoginDTO usuario_logueado = new UsuarioLoginDTO(usuario_register);

        session.setAttribute("usuario", usuario_logueado);
        req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);

    }
}
