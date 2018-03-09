/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cliente;
import model.Cuenta;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 *
 * @author DAW
 */
public class Apertura_cuentaDAO
{
    public List<Cuenta> comprobar_cuenta_existente(String cu_ncu2)
    {
        long resultado = 0;
        DBConnection db = new DBConnection();
        List<Cuenta> lista = null;
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Cuenta>> h = new BeanListHandler<>(Cuenta.class);
            lista = qr.query(con, "select * from cuentas where cu_ncu = ?", h, cu_ncu2);
            
           
            
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        
        return lista;
    }
    
    public List<Cliente> comprobar_dni_existente(String cu_dn1)
    {
        long resultado = 0;
        DBConnection db = new DBConnection();
        List<Cliente> lista = null;
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Cliente>> h = new BeanListHandler<>(Cliente.class);
            lista = qr.query(con, " select * from clientes where cl_dni = ?", h, cu_dn1);
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        
        return lista;
    }
    
    public int registrar_cliente1(String cl_dni, String cl_nom, String cl_dir, String cl_tel, String cl_ema, String cl_fna, String cl_fcl)
    {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement pstm = null;
        int filas = 0;
        
        try {
            con = db.getConnection();
            
            String sql="insert into clientes values (?, ?, ?, ?, ?, ?, ?, 0, 0);";
            
            pstm = con.prepareStatement(sql);
            
            pstm.setString(1,cl_dni);
            pstm.setString(2,cl_nom);
            pstm.setString(3,cl_dir);
            pstm.setString(4,cl_tel);
            pstm.setString(5,cl_ema);
            pstm.setString(6,cl_fna);
            pstm.setString(7,cl_fcl);
            
            filas = pstm.executeUpdate();
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas;
    }
    
    public int registrar_cuenta(String cu_ncu_2, String cu_dn1_2, String cu_dn2, long cu_sal)
    {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
        PreparedStatement pstm4 = null;
        int filas = 0;
        int filas2 = 0;
        int filas3 = 0;
        int filas4 = 0;
        
        try {
            con = db.getConnection();
            
            String sql="insert into cuentas values (?, ?, ?, ?);";
            
            
            pstm = con.prepareStatement(sql);
            
            pstm.setString(1,cu_ncu_2);
            pstm.setString(2,cu_dn1_2);
            pstm.setString(3,cu_dn2);
            pstm.setLong(4,cu_sal);
            
            filas = pstm.executeUpdate();
            
            //Cliente 1
            String sql2="update clientes set cl_ncu = (select count(cu_ncu) from cuentas where cu_dn1 = ?), cl_sal = (select sum(cu_sal) from cuentas where cu_dn1 = ?) where cl_dni=?";
            pstm2 = con.prepareStatement(sql2);
            pstm2.setString(1, cu_dn1_2);
            pstm2.setString(2, cu_dn1_2);
            pstm2.setString(3, cu_dn1_2);
            
            filas2 = pstm2.executeUpdate();
            
            //Cliente 2
            if (!cu_dn2.equals(""))
            {
                String sql3="update clientes set cl_ncu = (select count(cu_ncu) from cuentas where cu_dn1 = ?), cl_sal = (select sum(cu_sal) from cuentas where cu_dn1 = ?) where cl_dni=?";
                pstm3 = con.prepareStatement(sql3);
                pstm3.setString(1, cu_dn2);
                pstm3.setString(2, cu_dn2);
                pstm3.setString(3, cu_dn2);

                filas3 = pstm3.executeUpdate();
            }
            
            //cuenta movimientos
            LocalDateTime ldt = LocalDateTime.now();
            String hora = ldt.getHour()+"";
            String minuto = ldt.getMinute()+"";
            String segundo = ldt.getSecond()+"";

            if (hora.length() == 1)
            {
                hora = 0+hora;
            }
            if (minuto.length() == 1)
            {
                minuto = 0+minuto;
            }
            if (segundo.length() == 1)
            {
                segundo = 0+segundo;
            }

            String mo_hor = hora+""+minuto+""+segundo;
            String mo_fec = ldt.getYear()+"-"+ldt.getMonth().getValue()+"-"+ldt.getDayOfMonth();
            
            String sql3="insert into movimientos values (?, '"+mo_fec+"', '"+mo_hor+"', \"Alta de cuenta\", ?)";
            pstm4 = con.prepareStatement(sql3);
            pstm4.setString(1, cu_ncu_2);
            pstm4.setLong(2, cu_sal);
            
            filas4 = pstm4.executeUpdate();
            
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas+filas2+filas3+filas4;
    }
    
    public long comprobar_saldo(String cu_ncu_3)
    {
        long resultado = 0;
        DBConnection db = new DBConnection();
        List<Cuenta> lista = null;
        Connection con = null;
        try {
            con = db.getConnection();
            QueryRunner qr = new QueryRunner();
            ResultSetHandler<List<Cuenta>> h = new BeanListHandler<>(Cuenta.class);
            lista = qr.query(con, "select * from cuentas where cu_ncu = ?", h, cu_ncu_3);
            
            if (lista.size()>0)
            {
                resultado = lista.get(0).getCu_sal();
            }
           
            
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        
        return resultado;
    }
    
    public int eliminar_cuenta(String cu_ncu_4)
    {
        DBConnection db = new DBConnection();
        Connection con = null;
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        PreparedStatement pstm3 = null;
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
            lista2 = qr.query(con, "select * from cuentas where cu_ncu = ?", h, cu_ncu_4);
            String dni1 = lista2.get(0).getCu_dn1();
            String dni2 = lista2.get(0).getCu_dn2();
            
            //Eliminar registro de la tabla cuentas
            String sql="delete from cuentas where cu_ncu = ?";
            
            pstm = con.prepareStatement(sql);
            pstm.setString(1,cu_ncu_4);
            
            filas = pstm.executeUpdate();
            
            //Comprobar el numero de cuentas abiertas para eliminar el cliente
            
            QueryRunner qr2 = new QueryRunner();
            ResultSetHandler<List<Cliente>> h2 = new BeanListHandler<>(Cliente.class);
            lista = qr2.query(con, "select * from clientes where cl_dni= ? or cl_dni= ?", h2, dni1, dni2);
            
            if(lista.size()>0)
            {
                for (int i = 0; i<lista.size(); i++)
                {
                    if (lista.get(i).getCl_ncu() == 1)
                    {
                        
                        String sql3="delete from clientes where cl_dni = ?";
            
                        pstm2 = con.prepareStatement(sql3);
                        pstm2.setString(1, lista.get(i).getCl_dni());

                        filas = pstm2.executeUpdate();
                    }
                    else
                    {
                        String sql4="update clientes set cl_ncu = cl_ncu-1  where cl_dni = ?";
            
                        pstm3 = con.prepareStatement(sql4);
                        pstm3.setString(1, lista.get(i).getCl_dni());

                        filas = pstm3.executeUpdate();
                    }
                }
            }
            
            //Eliminar movimientos 
            String sql4="delete from movimientos where mo_ncu = ?";
            
            pstm3 = con.prepareStatement(sql4);
            pstm3.setString(1,cu_ncu_4);
            
            filas = pstm3.executeUpdate();
            
            
            
        } catch (Exception ex) {
            Logger.getLogger(Apertura_cuentaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.cerrarConexion(con);
        }
        return filas;
    }
}






//
//package dao;
//
//import model.Alumno;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import org.apache.commons.dbutils.QueryRunner;
//import org.apache.commons.dbutils.ResultSetHandler;
//import org.apache.commons.dbutils.handlers.BeanListHandler;
//
//public class AlumnosDAO {
//
//    public List<Alumno> getAllAlumnos() {
//        List<Alumno> lista = null;
//        DBConnection db = new DBConnection();
//        Connection con = null;
//        try {
//            con = db.getConnection();
//            QueryRunner qr = new QueryRunner();
//            ResultSetHandler<List<Alumno>> h
//                    = new BeanListHandler<Alumno>(Alumno.class);
//            lista = qr.query(con, "select * FROM ALUMNOS ORDER BY ID DESC", h);
//
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            db.cerrarConexion(con);
//        }
//        return lista;
//    }
//
//    public List<Alumno> getAllAlumnosJDBC() {
//        List<Alumno> lista = new ArrayList<>();
//        Alumno nuevo = null;
//        DBConnection db = new DBConnection();
//        Connection con = null;
//        Statement stmt = null;
//        ResultSet rs = null;
//        try {
//            con = db.getConnection();
//            stmt = con.createStatement();
//            String sql;
//            sql = "SELECT * FROM ALUMNOS ORDER BY ID DESC";
//            rs = stmt.executeQuery(sql);
//
//            //STEP 5: Extract data from result set
//            while (rs.next()) {
//                //Retrieve by column name
//                int id = rs.getInt("id");
//                String nombre = rs.getString("nombre");
//                Date fn = rs.getDate("fecha_nacimiento");
//                Boolean mayor = rs.getBoolean("mayor_edad");
//                nuevo = new Alumno();
//                nuevo.setFecha_nacimiento(fn);
//                nuevo.setId(id);
//                nuevo.setMayor_edad(mayor);
//                nuevo.setNombre(nombre);
//                lista.add(nuevo);
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (rs != null) {
//                    rs.close();
//                }
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            db.cerrarConexion(con);
//        }
//        return lista;
//
//    }
//    
//    public int actualizarAlumno(Alumno u)
//    {
//        DBConnection db = new DBConnection();
//        Connection con = null;
//        PreparedStatement pstm = null;
//        int filas = 0;
//        
//        try {
//            con = db.getConnection();
//            
//            String sql="UPDATE ALUMNOS SET NOMBRE=?, FECHA_NACIMIENTO=?, MAYOR_EDAD=? WHERE ID=?;";
//            
//            pstm = con.prepareStatement(sql);
//            
//            
//            pstm.setString(1,u.getNombre());
//            pstm.setDate(2, new java.sql.Date(u.getFecha_nacimiento().getTime()));
//            pstm.setBoolean(3, u.getMayor_edad());
//            pstm.setLong(4, u.getId());
//            
//            filas = pstm.executeUpdate();
//            
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            db.cerrarConexion(con);
//        }
//        return filas;
//    }
//    
//    public int borrarAlumno(Alumno u)
//    {
//        DBConnection db = new DBConnection();
//        Connection con = null;
//        PreparedStatement pstm = null;
//        int filas = 0;
//        
//        try {
//            con = db.getConnection();
//            
//            String sql="delete from ALUMNOS where id=?;";
//            
//            pstm = con.prepareStatement(sql);
//            
//            pstm.setLong(1, u.getId());
//            
//            filas = pstm.executeUpdate();
//            
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            db.cerrarConexion(con);
//        }
//        return filas;
//    }
//    
//    public int insertarAlumno(Alumno u)
//    {
//        DBConnection db = new DBConnection();
//        Connection con = null;
//        PreparedStatement pstm = null;
//        int filas = 0;
//        
//        try {
//            con = db.getConnection();
//            
//            String sql="INSERT INTO ALUMNOS (NOMBRE, FECHA_NACIMIENTO, MAYOR_EDAD) VALUES (?, ?, ?);";
//            
//            pstm = con.prepareStatement(sql);
//            
//            pstm.setString(1,u.getNombre());
//            pstm.setDate(2, new java.sql.Date(u.getFecha_nacimiento().getTime()));
//            pstm.setBoolean(3, u.getMayor_edad());
//            
//            filas = pstm.executeUpdate();
//            
//        } catch (Exception ex) {
//            Logger.getLogger(AlumnosDAO.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            db.cerrarConexion(con);
//        }
//        return filas;
//    }
//    
//    
//
//}

