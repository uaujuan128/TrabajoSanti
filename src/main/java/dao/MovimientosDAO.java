/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Cuenta;
import model.Movimiento;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import servicios.MovimientosServicios;

/**
 *
 * @author Juan
 */
public class MovimientosDAO {
    public List<Movimiento> obtener_movimientos(String cu_ncu, String  fecha_inicial, String  fecha_final)
    {
        long resultado = 0;
        DBConnection db = new DBConnection();
        List<Movimiento> lista = null;
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Movimiento>> h = new BeanListHandler<>(Movimiento.class);
            lista = qr.query(con, "select * from movimientos where mo_ncu = ? and mo_fec between ? and ?", h, cu_ncu, fecha_inicial, fecha_final);
        } catch (Exception ex) {
            Logger.getLogger(MovimientosServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        
        return lista;
    }
    
    public int insertar_movimiento(Movimiento movimiento)
    {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        List<Cliente> lista = null;
        List<Cuenta> lista2 = null;
        int n_cuentas = 0;
        int filas = 0;
        int filas2 = 0;
        int filas3 = 0;
        
        try {
            con = db.getConnection();
            
            //Obtener los dnis asociados al numero de cuenta
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Cuenta>> h = new BeanListHandler<>(Cuenta.class);
            lista2 = qr.query(con, "select * from cuentas where cu_ncu = ?", h, movimiento.getMo_ncu());
            String dni1 = lista2.get(0).getCu_dn1();
            String dni2 = lista2.get(0).getCu_dn2();
            
            //Añadir registro a la tabla movimientos
            String sql="insert into movimientos values (?, ?, ?, ?, ?)";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1,movimiento.getMo_ncu());
            pstm.setString(2,movimiento.getMo_fec());
            pstm.setString(3,movimiento.getMo_hor());
            pstm.setString(4,movimiento.getMo_des());
            pstm.setInt(5,movimiento.getMo_imp());
            
            filas = pstm.executeUpdate();
            
            //Comprobar el numero de cuentas abiertas para restar saldo al cliente
            
            QueryRunner qr2 = new QueryRunner();
            ResultSetHandler<List<Cliente>> h2 = new BeanListHandler<>(Cliente.class);
            lista = qr2.query(con, "select * from clientes where cl_dni= ? or cl_dni= ?", h2, dni1, dni2);
            
            //Como la cuenta puede estar asociada a 1 o 2 clientes, tenemos que asegurar recorrerlos todos
            if(lista.size()>0)
            {
                for (int i = 0; i<lista.size(); i++)
                {
                    String sql4="update clientes set cl_sal = cl_sal+? where cl_dni = ?";

                    pstm3 = con.prepareStatement(sql4);
                    pstm3.setInt(1, movimiento.getMo_imp());
                    pstm3.setString(2, lista.get(i).getCl_dni());

                    filas = pstm3.executeUpdate();
                }
            }
            
            //Actualizar tabla cuentas
            String sql4="update cuentas set cu_sal = cu_sal+? where cu_ncu = ?";
            
            pstm4 = con.prepareStatement(sql4);
            pstm4.setInt(1, movimiento.getMo_imp());
            pstm4.setString(2, movimiento.getMo_ncu());
            filas = pstm4.executeUpdate();
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas;
    }
}
