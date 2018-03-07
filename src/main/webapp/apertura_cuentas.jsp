<%-- 
    Document   : apertura_cuentas
    Created on : 31-ene-2018, 10:00:34
    Author     : DAW
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Apertura de cuentas</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <script>
            function apertura_cuenta()
            {
                var cu_ncu = document.getElementById("cu_ncu").value;
                var cu_dn1 = document.getElementById("cu_dn1").value;
                var cu_dn2 = document.getElementById("cu_dn2").value;
                var cu_sal = document.getElementById("cu_sal").value;
                
                var comprobacion = true;
                
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
                
                //Comprobacion del numero en el servidor
                
                var datos = "cu_ncu="+cu_ncu+"&op=comprobar_ncuenta";
                
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos,
                        success:function(resp)
                        {
                            if (!resp)
                            
                            {
                                alert("(servidor)El numero de 10º dígito debe corresponder al resto de dividir la suma de los otros 9 entre 9");
                                return;
                            }
                        }
                })
                
                //Comprobar que la cuenta no está dada de alta en el servidor
                
                var datos2 = "cu_ncu="+cu_ncu+"&op=comprobar_cuenta_existente";
                
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos2,
                        success:function(resp)
                        {
                            if(resp == 0)
                            {
                                alert("La cuenta "+cu_ncu+" no existe, puede ser creada");
                            }
                            else if (resp >0)
                            {
                                alert("La cuenta "+cu_ncu+" ya existe, pon otro número");
                                return;
                            }
                            else
                            {
                                alert("Ha habido un error en la comprobacion de cuenta");
                                return;
                            }
                        }
                })
                
                //Comprobar formato dni en cliente
                expresion = new RegExp ("^[0-7][0-9]{7}[A-Z]$", "g");
                valido = (expresion.test (cu_dn1));
                    if (valido == false)
                    {
                        alert("Asegúrate que el formato del DNI 1 es el siguiente: '^[0-7][0-9]{7}[A-Z]$', 'g1'");
                        return;
                    }
               
                if(cu_dn2)
                {
                    valido2 = (expresion.test (cu_dn2));
                    if (valido2 == false)
                    {
                        alert("Asegúrate que el formato del DNI 2 es el siguiente: '^[0-7][0-9]{7}[A-Z]$', 'g1'");
                        return;
                    }
                }
                    
                //Comprobar si el dni1 ya esta dado de alta en la tabla clientes
                var datos3 = "cu_dn1="+cu_dn1+"&op=comprobar_dni_existente";
                
                $.ajax({
                        type:'get',
                        url:'apertura_cuenta',
                        data:datos3,
                        success:function(resp)
                        {
                            if (resp == "Este cliente no existe, tienes que crearlo antes")
                            {
                                var respuesta = confirm("Este cliente no existe, quieres crearlo antes?");
                                if (respuesta)
                                {
                                    document.getElementById("registrar_cliente").innerHTML = 
                                            '<h2>Registrar cliente para DNI 1</h2>'+
                                    'Dni: <input type="text" name="cl_dni" value="'+cu_dn1+'" /><br>'+
                                    'Nombre: <input type="text" name="cl_nom" value="" /><br>'+
                                    'Direción: <input type="text" name="cl_dir" value="" /><br>'+
                                    'Télefono: <input type="text" name="cl_tel" value="" /><br>'+
                                    'Email: <input type="text" name="cl_ema" value="" /><br>'+
                                    'Nacimiento: <input type="date" name="cl_fna" value="" /><br>'+
                                    'Salario: <input type="number" name="cl_sal"><br> '+
                                    '<button onclick="registrar_cliente1()">Registrar</button>';
                                }
                                else
                                {
                                    alert("Has cancelado la operacion");
                                    return;
                                }
                            }
                        }
                })
                
                //registrar cliente y continuar con dni2
                
                function registrar_cliente1()
                {
                    var cl_dni = document.getElementById("cl_dni").value;
                    var cl_nom = document.getElementById("cl_nom").value;
                    var cl_dir = document.getElementById("cl_dir").value;
                    var cl_tel = document.getElementById("cl_tel").value;
                    var cl_ema = document.getElementById("cl_ema").value;
                    var cl_fna = document.getElementById("cl_fna").value;
                    var cl_fcl = new Date.now();
                    var cl_sal = document.getElementById("cl_sal").value;
                    
                    var datos3 = "cu_dni="+cu_dni+"&cl_nom="+cl_nom+"&cl_dir="+cl_dir+"&cl_tel="+cl_tel+"&cl_ema="+cl_ema+"&cl_fna="+cl_fna+"&cl_fcl="+cl_fcl+"&cl_sal="+cl_sal+"&op=registrar_cliente1";
                
                    $.ajax({
                            type:'get',
                            url:'apertura_cuenta',
                            data:datos3,
                            success:function(resp)
                            {
                                if (resp == "Este cliente no existe, tienes que crearlo antes")
                                {
                                    var respuesta = confirm("Este cliente no existe, quieres crearlo antes?");
                                    if (respuesta)
                                    {
                                        document.getElementById("registrar_cliente").innerHTML = 
                                                '<h2>Registrar cliente para DNI 1</h2>'+
                                        'Dni: <input type="text" name="cl_dni" value="'+cu_dn1+'" /><br>'+
                                        'Nombre: <input type="text" name="cl_nom" value="" /><br>'+
                                        'Direción: <input type="text" name="cl_dir" value="" /><br>'+
                                        'Télefono: <input type="text" name="cl_tel" value="" /><br>'+
                                        'Email: <input type="text" name="cl_ema" value="" /><br>'+
                                        'Nacimiento: <input type="date" name="cl_fna" value="" /><br>'+
                                        'Salario: <input type="number" name="cl_sal"><br> '+
                                        '<button onclick="registrar_cliente()">Registrar</button>';
                                    }
                                    else
                                    {
                                        alert("Has cancelado la operacion");
                                        return;
                                    }
                                }
                            }
                        })
                }
            }
        </script>
    </head>
    <body>
        <h1>Apertura de cuentas</h1>
        Número de cuenta:<input type="text" id="cu_ncu"><br>
        DNI titular 1<input type="text" id="cu_dn1"><br>
        DNI titular 2 (opcional)<input type="text" id="cu_dn2"><br>
        Salario:<input type="number" id="cu_sal"><br>
        <button onclick="apertura_cuenta()">Crear cuenta</button><br>
        <div id ="registrar_cliente">
            
        </div>
    </body>
</html>
