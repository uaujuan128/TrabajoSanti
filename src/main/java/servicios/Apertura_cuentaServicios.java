/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.Apertura_cuentaDAO;
import java.util.List;
import model.Cliente;
import model.Cuenta;

/**
 *
 * @author DAW
 */
public class Apertura_cuentaServicios
{
    public String comprobar_cuenta_existente(String cu_ncu2)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        List<Cuenta> lista = dao.comprobar_cuenta_existente(cu_ncu2);
        String resultado = "";
        
        if(lista.size() > 0)
        {
            for (int i = 0; i<lista.size(); i++)
            {
                resultado += "Datos de la cuenta: "+lista.get(i).getCu_ncu()+" - "+lista.get(i).getCu_dn1()+" - "+lista.get(i).getCu_dn2()+" - "+lista.get(i).getCu_sal();
            }
        }
        return resultado;
    }
    public String comprobar_dni_existente(String cu_dn1)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        
        List<Cliente> lista_cliente = (List<Cliente>) dao.comprobar_dni_existente(cu_dn1);
        String resultado = null;
        if (lista_cliente.size() > 0)
        {
            Cliente cliente = lista_cliente.get(0);
            resultado = "Este cliente ya existe. Estos son sus datos: "+lista_cliente.get(0).getCl_dni()+", "+lista_cliente.get(0).getCl_nom()+", "+lista_cliente.get(0).getCl_dir()+", "+lista_cliente.get(0).getCl_tel()+", "+lista_cliente.get(0).getCl_ema()+", "+lista_cliente.get(0).getCl_fna()+", "+lista_cliente.get(0).getCl_fcl()+", "+lista_cliente.get(0).getCl_ncu()+", "+lista_cliente.get(0).getCl_sal();
        }
        else
        {
            resultado = "Este cliente no existe, tienes que crearlo antes";
        }
        
        return resultado;
    }
    
    public int registrar_cliente1(String cl_dni, String cl_nom, String cl_dir, String cl_tel, String cl_ema, String cl_fna, String cl_fcl)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        return dao.registrar_cliente1(cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl);
    }
    public int registrar_cuenta(String cu_ncu_2, String cu_dn1_2, String cu_dn2, long cu_sal)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        return dao.registrar_cuenta(cu_ncu_2, cu_dn1_2, cu_dn2, cu_sal);
    }
    
    public long comprobar_saldo(String cu_ncu_3)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        return dao.comprobar_saldo(cu_ncu_3);
    }
    
    public int eliminar_cuenta(String cu_ncu_4)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        return dao.eliminar_cuenta(cu_ncu_4);
    }
}
