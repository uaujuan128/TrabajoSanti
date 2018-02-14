/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicios;

import dao.Apertura_cuentaDAO;

/**
 *
 * @author DAW
 */
public class Apertura_cuentaServicios
{
    public int comprobar_cuenta_existente(String cu_ncu2)
    {
        Apertura_cuentaDAO dao = new Apertura_cuentaDAO();
        return dao.comprobar_cuenta_existente(cu_ncu2);
    }
}
