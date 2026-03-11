<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/tienda.css">
    <title>Document</title>
</head>
<body>
    
    
    <header>
        <img src="imgs/amazon.png" class="logo">

        <form action="carrito" method="GET">
            <button class="registrarse">Carrito</button>
        </form>
        <form action="pedido" method="GET">
            <button class="registrarse">Pedidos</button>
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

    <%-- <form action="tienda" method="post">
        <select name="filtro_categoria">
            <option value="0">Todos</option>
            <option value="1">Bebidas</option>
            <option value="2">Electronica</option>
            <option value="3">Mobiliario</option>
        </select>
        <button type="submit" name="action" value="filtrar">Filtrar</button>
    </form> --%>
    <c:choose>
        <c:when test="${not empty productos}">
            <div class="contenedor_productos">
                <c:forEach var="p" items="${productos}">
                    <c:if test="${categoriaFiltro == null || p.idCategoria == categoriaFiltro || categoriaFiltro == 0}">
                        <div class="producto">
                            <img src="${p.imagen}" width="50px">
                            <div class="info_producto">
                                <h5>${p.nombre}</h5>
                                <h5>${p.precio}</h5>
                                <h5>${p.nombreCategoria()}</h5>
                                <h5>${p.marca}</h5>
                                <h5 class="info">${p.descripcion}</h5>
                            </div>

                            <form class="formulario" action="tienda" method="POST">
                                <input type="hidden" name="producto" value="${p.idProducto}">
                                <button class="boton_transacciones" type="submit" name="action" value="poner">Añadir a la cesta</button>
                            </form>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <p>No hay productos disponibles.</p>
        </c:otherwise>
    </c:choose>

    <footer></footer>
</body>
</html>