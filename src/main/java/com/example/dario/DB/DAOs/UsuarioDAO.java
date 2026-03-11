package com.example.dario.DB.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.dario.DB.Conexion;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.DTO.UsuarioRegistroDTO;
import com.example.dario.model.Usuario;
import com.example.dario.security.PasswordHasherArgon2;


public class UsuarioDAO {

    public Usuario findByEmail(String email) {
        String sql = """
                SELECT *
                FROM user
                WHERE email = ? 
                """;

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            Usuario user = null;
            if (rs.next()) {
                // String hashPassword = rs.getString("password");
                // String password = PasswordHasherArgon2.hashPassword(rs.getString("password").toCharArray());
                // if(PasswordHasherArgon2.verifyPassword(hashPassword, password.toCharArray())){
                    user = new Usuario();
                    user.setId(rs.getLong("user_id"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setNombre(rs.getString("nombre"));
                    user.setApellidos(rs.getString("apellidos"));
                    user.setNif(rs.getString("nif"));/* */
                    user.setTelefono(rs.getString("telefono"));
                    user.setDireccion(rs.getString("direccion"));
                    user.setCodigo_postal(rs.getString("codigo_postal"));
                    user.setLocalidad(rs.getString("localidad"));
                    user.setProvincia(rs.getString("provincia"));
                    user.setUltimo_acceso(rs.getDate("ultimo_acceso"));
                    user.setAvatar(rs.getString("avatar"));


            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario findById(Long id) {
        String sql = """
                SELECT *
                FROM user
                WHERE user_id = ?
                """;

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            Usuario user = null;
            if (rs.next()) {
                user = new Usuario();
                user.setId(rs.getLong("user_id"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setNombre(rs.getString("nombre"));
                user.setApellidos(rs.getString("apellidos"));
                user.setNif(rs.getString("nif"));/* */
                user.setTelefono(rs.getString("telefono"));
                user.setDireccion(rs.getString("direccion"));
                user.setCodigo_postal(rs.getString("codigo_postal"));
                user.setLocalidad(rs.getString("localidad"));
                user.setProvincia(rs.getString("provincia"));
                user.setUltimo_acceso(rs.getDate("ultimo_acceso"));
                user.setAvatar(rs.getString("avatar"));
                

            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean create(UsuarioRegistroDTO usuarioRegistroDTO) {

        String sql = """
                INSERT INTO user (
                    email,
                    password,
                    nombre,
                    apellidos,
                    nif,
                    telefono,
                    direccion,
                    codigo_postal,
                    localidad,
                    provincia,
                    ultimo_acceso,
                    avatar)

                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            String passwordHash = PasswordHasherArgon2.hashPassword(usuarioRegistroDTO.getPassword().toCharArray());

            ps.setString(1, usuarioRegistroDTO.getEmail());
            ps.setString(2, passwordHash);
            ps.setString(3, usuarioRegistroDTO.getNombre());
            ps.setString(4, usuarioRegistroDTO.getApellidos());
            ps.setString(5, usuarioRegistroDTO.getNif());
            ps.setString(6, usuarioRegistroDTO.getTelefono());
            ps.setString(7, usuarioRegistroDTO.getDireccion());
            ps.setString(8, usuarioRegistroDTO.getCodigo_postal());
            ps.setString(9, usuarioRegistroDTO.getLocalidad());
            ps.setString(10, usuarioRegistroDTO.getProvincia());
            ps.setTimestamp(11, new java.sql.Timestamp(usuarioRegistroDTO.getUltimo_acceso().getTime()));
            ps.setString(12, "avatar_default.jpg");
            /* Continuar */

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR SQL: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean update(UsuarioLoginDTO usuario_logueado) {

        String sql = "UPDATE user "+
                    "\n SET"+
                    "\n email = ?,"+
                    "\n password = ?,"+
                    "\n nombre = ?,"+
                    "\n apellidos = ?,"+
                    "\n nif = ?,"+
                    "\n telefono = ?,"+
                    "\n direccion = ?,"+
                    "\n codigo_postal = ?,"+
                    "\n localidad = ?,"+
                    "\n provincia = ?,"+
                    "\n ultimo_acceso = ?,"+
                    "\n    avatar = ?"+
                    "\n WHERE user_id = ?";
                
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            String passwordHash = PasswordHasherArgon2.hashPassword(usuario_logueado.getPassword().toCharArray());

            ps.setString(1, usuario_logueado.getEmail());
            ps.setString(2, passwordHash);
            ps.setString(3, usuario_logueado.getNombre());
            ps.setString(4, usuario_logueado.getApellidos());
            ps.setString(5, usuario_logueado.getNif());
            ps.setString(6, usuario_logueado.getTelefono());
            ps.setString(7, usuario_logueado.getDireccion());
            ps.setString(8, usuario_logueado.getCodigo_postal());
            ps.setString(9, usuario_logueado.getLocalidad());
            ps.setString(10, usuario_logueado.getProvincia());
            ps.setTimestamp(11, new java.sql.Timestamp(usuario_logueado.getUltimo_acceso().getTime()));
            ps.setString(12, usuario_logueado.getAvatar());
            ps.setLong(13, usuario_logueado.getId());
            /* Continuar */

            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERROR SQL: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(int id_usuario) {
        String sql = """
                DELETE fron user where user_id = ?;
                """;

        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean email_existente(String email) {
        String sql = """
                select count(*) from user where email = ?;
                """;
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            int instancias = 0;

            if (rs.next()) {
                instancias = rs.getInt(1);
            }

            return instancias > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean nif_existente(String nif) {
        String sql = """
                select count(*) from user where nif = ?;
                """;
        try (Connection con = Conexion.getConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nif);
            ResultSet rs = ps.executeQuery();
            int instancias = 0;

            if (rs.next()) {
                instancias = rs.getInt(1);
            }

            return instancias > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
