/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.Apertura_cuentaDAO;
import dao.DBConnection;
import dao.MovimientosDAO;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Movimiento;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author Juan
 */
public class MovimientosServicios {
    
    public String obtener_movimientos(String cu_ncu, String  fecha_inicial, String  fecha_final)
    {
        MovimientosDAO a = new MovimientosDAO();
        List<Movimiento> lista = a.obtener_movimientos(cu_ncu, fecha_inicial, fecha_final);
        String resultado = "";
        
        resultado += "<table border = 1>";
        resultado += "<tr><th>Nº cuenta</th><th>Fecha</th><th>Hora</th><th>Modo</th><th>Importe</th></tr>";
        for (int i = 0; i < lista.size(); i++)
        {
            resultado += "<tr><td>"+lista.get(i).getMo_ncu()+"</td><td>"+lista.get(i).getMo_fec()+"</td><td>"+lista.get(i).getMo_hor()+"</td><td>"+lista.get(i).getMo_des()+"</td><td>"+lista.get(i).getMo_imp()+"</td></tr>";
        }
        resultado += "</table>";
        
        return resultado;
    }
    
    public int insertar_movimiento(Movimiento movimiento)
    {
        MovimientosDAO a = new MovimientosDAO();
        return a.insertar_movimiento(movimiento);
    }
    
}
