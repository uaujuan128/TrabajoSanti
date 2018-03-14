<%-- 
    Document   : listado_movimientos
    Created on : 08-mar-2018, 21:08:45
    Author     : Juan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet/less" type="text/css" href="styles.less" />
        <script src="//cdnjs.cloudflare.com/ajax/libs/less.js/3.0.0/less.min.js" ></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            function comprobar_movimientos()
            {
                var cu_ncu = document.getElementById("cu_ncu").value;
                var fecha_inicial = document.getElementById("fecha_inicial").value;
                var fecha_final = document.getElementById("fecha_final").value;
                
                
                
                //Comprobacion de formato de numero en cliente
                var expresion = new RegExp("^[0-9]{10}$", "gi");
                if (expresion.test(cu_ncu)!== true)
                {
                    alert("El numero de cuenta tiene que tener 10 digitos");
                    return;
                }
                var resultado = 0;
                for (var i = 0; i<9; i++)
                {
                        resultado += parseInt(cu_ncu.substring(i, i+1));
                }
                var resto = resultado%9;
                if (resto != cu_ncu.substring(9, 10))
                {
                        alert("(cliente)El numero de 10º dígito debe corresponder al resto de dividir la suma de los otros 9 entre 9");
                        return;
                }
                
                var datos2 = "cu_ncu="+cu_ncu+"&op=comprobar_cuenta_existente";
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos2,
                        success:function(resp)
                        {
                            if (resp == 0)
                            {
                                alert("La cuenta "+cu_ncu+" no existe");
                                return;
                            }
                        }
                })
                
                if (Date.parse(fecha_inicial) >= Date.parse(fecha_final))
                {
                    alert ("La fecha inicial es mayor o igual que la final");
                    return;
                }
                
                obtener(cu_ncu, fecha_inicial, fecha_final);
                
                function obtener(cu_ncu, fecha_inicial, fecha_final)
                {
                    var datos3 = "cu_ncu="+cu_ncu+"&fecha_inicial="+fecha_inicial+"&fecha_final="+fecha_final+"&op2=obtener_movimientos";
                    $.ajax({
                            type:'get',
                            url:'movimientos',
                            data:datos3,
                            success:function(resp)
                            {
                                document.getElementById("movimientos").innerHTML = resp;
                            }
                    })
                }
                
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
        
        <h2>Listado de movimientos</h2>
        Nº de cuenta: <input type="text" id="cu_ncu" value="" />
        Fecha inicial: <input type="date" id ="fecha_inicial">
        Fecha final<input type="date" id ="fecha_final">
        <button onclick="comprobar_movimientos()">Comprobar movimientos</button><br><br>
        </div>
        
        <div id="movimientos"></div>
        
    </body>
</html>
