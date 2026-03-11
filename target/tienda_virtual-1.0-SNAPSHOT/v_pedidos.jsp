<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/pedidos.css">
    <title>Mis Pedidos</title>
</head>
<body>
   
    <header>
        <img src="imgs/amazon.png" class="logo">
        <form action="tienda" method="GET">
            <button class="registrarse">Home</button>
        </form>
        <form action="carrito" method="GET">
            <button class="registrarse">Carrito</button>
        </form>

        <c:choose>
            <c:when test="${not empty usuario.nombre}">

                <form action="tienda" method="GET">
                    <button class="cerrar_sesion" name="cerrar_sesion" value="cerrar_sesion">Cerrar sesión</button>
                </form>
                <form action="editar_perfil" method="GET">
                    <button class="box_register">
                        <div class="box_user">
                            <img src="media?file=${usuario.avatar}" onerror="this.onerror=null; this.src='imgs/usuario_sin_registrar.png';" />
                        </div>
                        <h4>${usuario.nombre}</h4>
                    </button>
                </form>

            </c:when>
            <c:otherwise>
                <form action="register" method="GET">
                    <button class="registrarse">Registrarse</button>
                </form>
                <form action="login" method="GET">
                    <button class="iniciar_sesion">Iniciar sesión</button>
                </form>
            </c:otherwise>
        </c:choose>

    </header>

    <div class="contenedor_pedidos">
        <h2>Mis Pedidos</h2>
        
        <c:choose>
            <c:when test="${not empty pedidos}">
                <c:forEach var="pedido" items="${pedidos}">
                    <div class="pedido">
                        <div class="cabecera_pedido">
                            <h3>Pedido #${pedido.idPedido}</h3>
                            <h5><strong>Fecha:</strong> <fmt:formatDate value="${pedido.fecha}" pattern="dd/MM/yyyy"/></h5>
                            <h5><strong>Estado:</strong> 
                                <c:choose>
                                    <c:when test="${pedido.estado.name() == 'c'}">creado</c:when>
                                    <c:when test="${pedido.estado.name() == 'f'}">finalizado</c:when>
                                    <c:otherwise>${pedido.estado}</c:otherwise>
                                </c:choose>
                            </h5>
                        </div>

                        <div class="lineas_pedido">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Producto</th>
                                        <th>Precio Unitario</th>
                                        <th>Cantidad</th>
                                        <th>Subtotal</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="linea" items="${pedido.lineas}">
                                        <tr>
                                            <td>${linea.nombreProducto}</td>
                                            <td><fmt:formatNumber value="${linea.precioProducto}" type="currency" currencySymbol="€"/></td>
                                            <td>${linea.cantidad}</td>
                                            <td><fmt:formatNumber value="${linea.subtotal}" type="currency" currencySymbol="€"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                        <div class="resumen_pedido">
                            <h5><strong>Importe:</strong> <fmt:formatNumber value="${pedido.importe}" type="currency" currencySymbol="€"/></h5>
                            <h5><strong>IVA (21%):</strong> <fmt:formatNumber value="${pedido.iva}" type="currency" currencySymbol="€"/></h5>
                            <h5><strong>Total:</strong> <fmt:formatNumber value="${pedido.total}" type="currency" currencySymbol="€"/></h5>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p>No tienes pedidos realizados.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <footer></footer>

</body>
</html>