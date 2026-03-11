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
            <button class="registrarse">Home</button>
        </form>
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
                <form action="login" method="GET">
                    <button class="iniciar_sesion">Iniciar sesión</button>
                </form>
            </c:otherwise>
        </c:choose>

    </header>
<!--         
        username
        password
        email
        apellidos
        nif_str
        telefono_str
        direccion
        codigo_postal
        localidad
        provincia  -->

        <c:if test="${not empty error}">
            <div class="message">
                <p>${error}</p>
            </div>
        </c:if>
        <div class="cuerpo">
            <!-- <p>La contraseña no es válida</p> -->
            <form class="formulario" action="register" method="POST">
            
                <label>Nombre</label>
                <input name="username" type="text">
    
                <label>Contraseña</label>
                <input name="password" type="password">
    
                <label>Email</label>
                <input name="email" type="text">
    
                <label>Apellidos</label>
                <input name="apellidos" type="text">
    
                <label>NIF</label>
                <input name="nif" type="text">
    
                <label>Telefono</label>
                <input name="telefono" type="text">
    
                <label>Direccion</label>
                <input name="direccion" type="text">
    
                <label>Código postal</label>
                <input name="codigo_postal" type="text">
    
                <label>Localidad</label>
                <input name="localidad" type="text">
    
                <label>Provincia</label>
                <input name="provincia" type="text">
    
                <button type="submit">Registrarse</button>
            </form>
        </div>
    
    <footer>


</body>
</html>