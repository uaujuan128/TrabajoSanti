<%-- 
    Document   : cierre_cuentas
    Created on : 09-mar-2018, 9:00:17
    Author     : DAW
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cerrar cuentas</title>
        <link rel="stylesheet/less" type="text/css" href="styles.less" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js" ></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            function eliminar_cuenta()
            {
                var cu_ncu = document.getElementById("cu_ncu").value;
                
                //comprobar existencia de la cuenta y sacar sus datos
                
                var datos = "cu_ncu="+cu_ncu+"&op=comprobar_cuenta_existente";
                
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos,
                        success:function(resp)
                        {
                            if (resp == "")
                            {
                                alert("La cuenta "+cu_ncu+" no existe");
                                return;
                            }
                            else
                            {
                                 alert(resp);
                            }
                        }
                })
                
                //Comprobar saldo = 0
                var datos2 = "cu_ncu="+cu_ncu+"&op=comprobar_saldo";
                
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos2,
                        success:function(resp)
                        {
                            if (resp != 0)
                            {
                                alert("El saldo es "+resp+". Tiene que ser 0 para borrar la cuenta");
                            }
                            else
                            {
                                var eliminar  = confirm("Confima eliminar la cuenta "+cu_ncu+"?");
                                
                                if(eliminar)
                                {
                                    var datos2 = "cu_ncu="+cu_ncu+"&op=eliminar_cuenta";
                
                                    $.ajax({
                                            type:'get',
                                            url:'apertura_cuenta',
                                            data:datos2,
                                            success:function(resp)
                                            {
                                                if (resp != 0)
                                                {
                                                    alert("La cuenta se ha borrado correctamente");
                                                }
                                                else
                                                {
                                                    alert("Error al borrar la cuenta");
                                                }
                                            }
                                    })
                                }
                            }
                        }
                })
                
                    
                
            }
        </script>
    </head>
    <body>
        <div>
            <ul>
            <li><a class="active" href="http://localhost:8080/TrabajoSanti/apertura_cuentas.jsp">Apertura de cuentas</a></li>
            <li><a href="http://localhost:8080/TrabajoSanti/listado_movimientos.jsp">Listado de movimientos</a></li>
            <li><a href="http://localhost:8080/TrabajoSanti/cierre_cuentas.jsp">Cierre de cuentas</a></li>
            <li><a href="http://localhost:8000/movimientos.php">Movimientos en cuenta</a></li>
        </ul>
        
        <h2>Cerrar cuentas</h2>
        Introduce numero de cuenta a eliminar: <input type="text" id="cu_ncu">
        <button onclick="eliminar_cuenta()">Eliminar</button>
        </div>
        
        
    </body>
</html>
