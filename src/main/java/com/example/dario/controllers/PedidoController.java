package com.example.dario.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.example.dario.DB.DAOs.PedidoDAO;
import com.example.dario.DTO.UsuarioLoginDTO;
import com.example.dario.model.Carrito;
import com.example.dario.model.Pedido;

@WebServlet(urlPatterns = { "/pedido" })
public class PedidoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        UsuarioLoginDTO usuario = (UsuarioLoginDTO) session.getAttribute("usuario");

        if (usuario == null) {
            req.setAttribute("error", "Tienes que registrarte para ver los pedidos");
            resp.sendRedirect("login.jsp");
            return;
        }

        PedidoDAO pedidoDAO = new PedidoDAO();
        ArrayList<Pedido> pedidos = pedidoDAO.obtenerPedidosPorUsuario(usuario.getId());

        req.setAttribute("pedidos", pedidos);

        req.getRequestDispatcher("v_pedidos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        UsuarioLoginDTO usuario = (UsuarioLoginDTO) session.getAttribute("usuario");
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if(usuario == null){
            req.setAttribute("error", "Necesitas iniciar sesion o registrarte para poder comprar");
            req.getRequestDispatcher("v_carrito.jsp").forward(req, resp);
            return;
        }
        if (carrito == null || carrito.getCesta().isEmpty()) {
            req.setAttribute("error", "El carrito esta vacio");
            req.getRequestDispatcher("v_carrito.jsp").forward(req, resp);
            return;
        }

        BigDecimal importe = carrito.calcularImporte();
        BigDecimal iva = carrito.calcularIVA();

        PedidoDAO pedidoDAO = new PedidoDAO();
        Long idPedido = pedidoDAO.insertarPedido(usuario.getId(), importe, iva);

        if (idPedido != null) {
            for (Map.Entry<Long, Integer> item : carrito.getCesta().entrySet()) {
                Long idProducto = item.getKey();
                Integer cantidad = item.getValue();
                pedidoDAO.insertarLineaPedido(idPedido, idProducto, cantidad);
            }

            carrito.vaciarCarrito();
            session.setAttribute("carrito", carrito);

            Cookie carritoCookie = new Cookie("carrito", "");
            carritoCookie.setMaxAge(0);
            carritoCookie.setPath("/");
            resp.addCookie(carritoCookie);

            resp.sendRedirect("v_tienda.jsp");
        } else {
            resp.sendRedirect("v_carrito.jsp");
        }
    }
}
