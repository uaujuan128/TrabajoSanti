/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Movimientos;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import servicios.MovimientosServicios;

/**
 *
 * @author Juan
 */
public class MovimientosDAO {
    public List<Movimientos> obtener_movimientos(String cu_ncu, String  fecha_inicial, String  fecha_final)
    {
        long resultado = 0;
        DBConnection db = new DBConnection();
        List<Movimientos> lista = null;
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Movimientos>> h = new BeanListHandler<>(Movimientos.class);
            lista = qr.query(con, "select * from movimientos where mo_ncu = ? and mo_fec between ? and ?", h, cu_ncu, fecha_inicial, fecha_final);
        } catch (Exception ex) {
            Logger.getLogger(MovimientosServicios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        
        return lista;
    }
}
