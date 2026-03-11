package com.example.dario.service;

import java.util.Date;

import javax.security.sasl.AuthenticationException;

import com.example.dario.DB.DAOs.UsuarioDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.DTO.UsuarioRegistroDTO;
import com.example.dario.model.Usuario;
import com.example.dario.security.PasswordHasherArgon2;

public class AuthService {
    private UsuarioDAO userDAO = new UsuarioDAO();

    public UsuarioLoginDTO login(String email, String password) throws AuthenticationException {

        Usuario user = userDAO.findByEmail(email);

        if (user == null) {
            throw new AuthenticationException("Error, usuario no existente");
        }

        
        // if (!PasswordHasherArgon2.verifyPassword(user.getPassword(), password.toCharArray())) {
        //     throw new AuthenticationException("Error, usuario o contraseña incorrectos");
        // }

        
        return new UsuarioLoginDTO(user);
    }

    public UsuarioLoginDTO findByUsername(String email) throws AuthenticationException{
        Usuario user = userDAO.findByEmail(email);

        if (user == null) {
            throw new AuthenticationException("Error, usuario no existe");
        }

        return new UsuarioLoginDTO(user);
    }

    public Usuario signUp(String email, String password,
            String username,
            String apellidos,
            String nif,
            String telefono,
            String direccion,
            String codigo_postal,
            String localidad,
            String provincia) {
        UsuarioRegistroDTO nuevo_usuario = new UsuarioRegistroDTO(email, password, username, apellidos, nif, telefono,
                direccion, codigo_postal, localidad, provincia, new Date(), "avatar_dafault.jpg");

         if (userDAO.create(nuevo_usuario)) {
            Usuario usuarioCreado = userDAO.findByEmail(email);
            return usuarioCreado;
         }
         return null;
    }

    public Usuario editUsuario(UsuarioLoginDTO usuarioLogin) {

         if (userDAO.update(usuarioLogin)) {
            Usuario usuarioCreado = userDAO.findByEmail(usuarioLogin.getEmail());
            return usuarioCreado;
         }
         return null;
    }
}