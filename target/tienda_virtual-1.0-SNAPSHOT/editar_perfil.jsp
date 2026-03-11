<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles/registro.css">
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
        <div>
            <!-- <p>La contraseña no es válida</p> -->
            <form class="formulario" action="editar_perfil" method="POST" enctype="multipart/form-data">
            
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

                <label>Imagen</label>
                <input type="file" name="imagen" id="imagen" accept=".jpg,.jpeg,.png,.webp">


                <button type="submit">Subir cambios</button>
            </form>
        </div>
    
    <footer>


</body>
</html>