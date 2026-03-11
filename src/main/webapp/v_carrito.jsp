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
        <form action="tienda" method="GET">
            <button class="registrarse">Home</button>
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


    <c:if test="${not empty error}">
        <div class="message">
            <p>${error}</p>
        </div>
    </c:if>


    <c:choose>
        <c:when test="${not empty carrito}">
            <div class="contenedor_productos">
                <c:forEach var="p" items="${carrito.productos()}">
                    <div class="producto">
                        <img src="${p.imagen}" width="50px">
                        <div class="info_producto">
                            <h5>${p.nombre}</h5>
                            <h5>${p.precio}</h5>
                            <span>Unidades: </span>
                            <span id="cantidad-${p.idProducto}">${carrito.obtenerUnidades(p.idProducto)}</span>
                        </div>
                        <button name="sumar" onclick="enviarPostSumar(${p.idProducto})" type="submit">+</button>
                        <button name="restar" onclick="enviarPostRestar(${p.idProducto})" type="submit">-</button>
                    </div>
                </c:forEach>
            </div>
            <div class="resumen_pedido">
                <h3>Resumen del Pedido</h3>
                <h4>Importe: <span id="importe">${carrito.calcularImporte()}</span>€</h4>
                <h4>IVA (21%): <span id="iva">${carrito.calcularIVA()}</span>€</h4>
                <h4>Total: <span id="total">${carrito.calcularImporte() + carrito.calcularIVA()}</span>€</h4>
                <form method="POST" action="pedido">
                    <button type="submit" class="btn_realizar_pedido">Realizar Pedido</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <p>No hay productos en el carrito.</p>
        </c:otherwise>
    </c:choose>



    <%-- <c:if test="${not empty imagenes}" >
        <c:forEach items="${imagenes}" var="imagenID">
            <img src="media?id=${imagenID}" width="200">
        </c:forEach>
    </c:if> --%>

    <footer></footer>
<script>
const contextPath = '<%= request.getContextPath() %>';

function enviarPostSumar(idProducto) {
    fetch(contextPath + '/carrito/sumar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ producto: idProducto })
    })
    .then(response => response.json())
    .then(data => {
        const h5Cantidad = document.getElementById("cantidad-" + idProducto);
        h5Cantidad.textContent = data.cantidad;


        // actualizar totales
        const imp = document.getElementById("importe");
        const iva = document.getElementById("iva");
        const total = document.getElementById("total");
        if (imp) imp.textContent = data.importe;
        if (iva) iva.textContent = data.iva;
        if (total) total.textContent = data.total;
    });
}

function enviarPostRestar(idProducto) {
    fetch(contextPath + '/carrito/restar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ producto: idProducto })
    })
    .then(response => response.json())
    .then(data => {
        const h5Cantidad = document.getElementById("cantidad-" + idProducto);
        h5Cantidad.textContent = data.cantidad;

        const imp = document.getElementById("importe");
        const iva = document.getElementById("iva");
        const total = document.getElementById("total");
        if (imp) imp.textContent = data.importe;
        if (iva) iva.textContent = data.iva;
        if (total) total.textContent = data.total;
    });
}

</script>
</body>
</html>