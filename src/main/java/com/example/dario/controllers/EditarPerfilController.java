package com.example.dario.controllers;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.example.dario.DB.DAOs.UsuarioDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.model.Usuario;
import com.example.dario.service.AuthService;

@WebServlet(urlPatterns = {"/editar_perfil"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 5, // 5MB memoria ram
        maxFileSize = 1024 * 1024 * 20,      // 20MB archivo
        maxRequestSize = 1024 * 1024 * 30    // 30MB request
)
public class EditarPerfilController extends HttpServlet{

    // Tipos permitidos
    private static final Set<String> TIPOS_PERMITIDOS = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );
    private AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
    }

    // guardamos los avatares fuera del WAR para que no se borren al redeplegar
    private static final String RUTA_UPLOAD = System.getProperty("user.home") + File.separator + "app_uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();
        Part archivo = req.getPart("imagen");
        // String RUTA_UPLOAD =  getServletContext().getRealPath("imgs");  // ya no se usa

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

        

        HttpSession session = req.getSession(false);
        UsuarioLoginDTO usuario_logueado = (UsuarioLoginDTO) session.getAttribute("usuario");


        /*Comprobar datos válidos */

        if(email != null && email.trim().length() != 0){
            if(email.trim().length() < 6){
                req.setAttribute("error", "El email es demasiado pequeña");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
            else if(email.length() <= 50){
                if(UsuarioDAO.email_existente(email)){
                    req.setAttribute("error", "El email ya pertenece a un usuario existente");
                    req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                    return;
                }
                if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
                    req.setAttribute("error", "El email no es correcto");
                    req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                    return;
                }
                usuario_logueado.setEmail(email);
            }
            else{
                req.setAttribute("error", "El email es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }


        if(username != null && username.trim().length() != 0){
            if(username != null && username.trim().length() > 0 && username.length() <= 30){
                usuario_logueado.setNombre(username);
            }
            else{
                req.setAttribute("error", "El nombre es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }


        if(password != null && password.trim().length() != 0){
            if(password.trim().length() < 4){
                req.setAttribute("error", "La contraseña es demasiado pequeña");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
            else if(password.length() <= 100){
                usuario_logueado.setPassword(password);
            }
            else{
                req.setAttribute("error", "La contraseña es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }
        


        if(apellidos != null && apellidos.trim().length() != 0){
            if(apellidos.trim().length() < 5){
                req.setAttribute("error", "El Apellido es demasiado pequeño");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
            else if(apellidos.length() <= 80){
                usuario_logueado.setApellidos(apellidos);
            }
            else{
                req.setAttribute("error", "El Apellido es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }

        if(nif != null && nif.trim().length() != 0){
            if(nif.trim().length() > 0 && nif.length() <= 100 ){
                if(!nif.matches("\\d{8}[A-Z]") == true){
                    req.setAttribute("error", "El NIF no es correcto");
                    req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                    return; 
                }
                if(UsuarioDAO.nif_existente(nif)){
                    req.setAttribute("error", "El nif ya pertenece a un usuario existente");
                    req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                    return;
                }
                usuario_logueado.setNif(nif);
            }
        }   


        try {
            if(telefono != null){
                telefono= telefono.trim();
                if (telefono.matches("\\d{9}")){
                    usuario_logueado.setTelefono(telefono);
                }   
            }
        } catch (Exception e) {
            req.setAttribute("error", "El número de teléfono tiene caracteres no permitidos");
            req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            return;
        }


        if(direccion != null && direccion.trim().length() != 0){
            if(direccion.trim().length() < 5){
                req.setAttribute("error", "La direccion es demasiado pequeña");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
            else if(direccion.length() <= 40){
                usuario_logueado.setDireccion(direccion);
            }
            else{
                req.setAttribute("error", "La direccion es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }




        if(codigo_postal != null && codigo_postal.length() == 5 ){
            if(!codigo_postal.matches("\\d{5}")){
                req.setAttribute("error", "El código postal no esta bien escrito");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                return;
            }
            usuario_logueado.setCodigo_postal(codigo_postal);
        }



        if(localidad != null && localidad.trim().length() != 0){
            if(localidad == null || localidad.trim().length() < 5 && localidad.trim().length() != 0){
                req.setAttribute("error", "La localidad es demasiado pequeña");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
            else if(localidad.length() <= 40){
                usuario_logueado.setLocalidad(localidad);
            }
            else{
                req.setAttribute("error", "La localidad es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }


        if(provincia != null && provincia.trim().length() != 0){
            if(provincia.trim().length() < 5 ){
                req.setAttribute("error", "La provincia es demasiado pequeña");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);  

            }
            else if(provincia.length() <= 30){
                usuario_logueado.setProvincia(provincia);
            }
            else{
                req.setAttribute("error", "La provincia es demasiado grande");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            }
        }



        if (archivo != null && archivo.getSize() != 0) {

            String mime = archivo.getContentType();
            if (!TIPOS_PERMITIDOS.contains(mime)) {
                sesion.setAttribute("error", "Formato no permitido");
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                return;
            }

            String extension = obtenerExtension(mime);
            String nombreArchivo = UUID.randomUUID().toString() + extension;
            File carpeta = new File(RUTA_UPLOAD);

            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            Path destino = Paths.get(RUTA_UPLOAD, nombreArchivo);
            try (InputStream input = archivo.getInputStream()) {
                Files.copy(input, destino, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Imagen subida en: " + destino);
            }catch (Exception e) {
                req.setAttribute("error", "Ha habido un error al almacenar la imagen");
                Files.deleteIfExists(destino);
                req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
                return;
            }

            usuario_logueado.setAvatar(nombreArchivo);
        }
     
        Usuario usuario_modificado = authService.editUsuario(usuario_logueado);
        
        if(usuario_modificado == null){

            req.setAttribute("error", "Ha habido un error al intentar crear el usuario en la Base de datos");
            req.getRequestDispatcher("editar_perfil.jsp").forward(req, resp);
            return;
        }

       Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("guardar_sesion".equals(cookie.getName())) {
                    cookie.setValue(usuario_modificado.getEmail());
                }

            }

        }
        

        UsuarioLoginDTO usuario_sesion = new UsuarioLoginDTO(usuario_modificado);
        session.setAttribute("usuario", usuario_sesion);
        req.getRequestDispatcher("v_tienda.jsp").forward(req, resp);
    }

    private String obtenerExtension(String mime) {
        switch (mime) {
            case "image/jpeg": return ".jpg";
            case "image/png": return ".png";
            case "image/webp": return ".webp";
            default: return "";
        }
    }
}
