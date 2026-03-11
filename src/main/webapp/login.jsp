<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/login.css">
    <title>Document</title>
</head>
<body>
    

    <header>
        <img src="imgs/amazon.png" class="logo">
        <form action="tienda" method="GET">
            <button>Home</button>
        </form>
        <form action="carrito" method="GET">
            <button>Carrito</button>
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
            </c:otherwise>
        </c:choose>

    </header>
    
        <c:if test="${not empty error}">
            <div class="message">
                <p>${error}</p>
            </div>
        </c:if>

    <div class="cuerpo">
        <form class="formulario" action="login" method="POST">

            <label>Email</label>
            <input name="email" type="text">

            <label>Contraseña</label>
            <input name="password" type="password">


            <div style="display: flex; align-items: center;">
                <span>Guardar sesión</span>
                <input name="guardar_sesion" type="checkbox">   
            </div>  
            <button type="submit">Iniciar Sesión</button>
        </form>
    </div>
    
    <footer></footer>


</body>
</html>