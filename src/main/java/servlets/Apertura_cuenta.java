/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servicios.Apertura_cuentaServicios;

/**
 *
 * @author DAW
 */
@WebServlet(name = "Apertura_cuenta", urlPatterns =
{
    "/apertura_cuenta"
})
public class Apertura_cuenta extends HttpServlet
{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String op = request.getParameter("op");
        switch(op)
        {
            case ("comprobar_ncuenta"):
                String cu_ncu = request.getParameter("cu_ncu");
                int resultado = 0;
                for (int i = 0; i<9; i++)
                {
                        resultado += parseInt(cu_ncu.substring(i, i+1));
                }
                int resto = resultado%9;
                if (resto != cu_ncu.charAt(9))
                {
                    response.getWriter().print(true);
                }
                else
                {
                    response.getWriter().print(false);
                }
                break;

            case ("comprobar_cuenta_existente"):
                String cu_ncu2 = request.getParameter("cu_ncu");
                Apertura_cuentaServicios servicio = new Apertura_cuentaServicios();

                response.getWriter().print(servicio.comprobar_cuenta_existente(cu_ncu2));
                break;
                
            case ("comprobar_dni_existente"):
                String cu_dn1 = request.getParameter("cu_dn1");
                Apertura_cuentaServicios servicio1 = new Apertura_cuentaServicios();
                    
                response.getWriter().print(servicio1.comprobar_dni_existente(cu_dn1));
                break;
                
            case ("registrar_cliente1"):
                String cl_dni = request.getParameter("cl_dni");
                String cl_nom = request.getParameter("cl_nom");
                String cl_dir = request.getParameter("cl_dir");
                String cl_tel = request.getParameter("cl_tel");
                String cl_ema = request.getParameter("cl_ema");
                String cl_fna = request.getParameter("cl_fna");
                String cl_fcl = request.getParameter("cl_fcl");
                
                Instant timestamp =  Instant.ofEpochMilli(parseLong(cl_fcl));
                ZoneId zone = null;
                LocalDateTime ldt = LocalDateTime.ofInstant(timestamp, zone.systemDefault());
                cl_fcl = ldt.getYear()+"-"+ldt.getMonth().getValue()+"-"+ldt.getDayOfMonth();
                
                Apertura_cuentaServicios servicio2 = new Apertura_cuentaServicios();
                    
                response.getWriter().print(servicio2.registrar_cliente1(cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl));
                break;
            case ("registrar_cuenta"):
                String cu_ncu_2 = request.getParameter("cu_ncu");
                String cu_dn1_2 = request.getParameter("cu_dn1");
                String cu_dn2 = request.getParameter("cu_dn2");
                long cu_sal = parseLong(request.getParameter("cu_sal"));
                
                
                Apertura_cuentaServicios servicio3 = new Apertura_cuentaServicios();
                    
                response.getWriter().print(servicio3.registrar_cuenta(cu_ncu_2, cu_dn1_2, cu_dn2, cu_sal));
                break;
            case ("comprobar_saldo"):
                String cu_ncu_3 = request.getParameter("cu_ncu");
                
                
                Apertura_cuentaServicios servicio4 = new Apertura_cuentaServicios();
                    
                response.getWriter().print(servicio4.comprobar_saldo(cu_ncu_3));
                break;
            case ("eliminar_cuenta"):
                String cu_ncu_4 = request.getParameter("cu_ncu");
                
                
                Apertura_cuentaServicios servicio5 = new Apertura_cuentaServicios();
                    
                response.getWriter().print(servicio5.eliminar_cuenta(cu_ncu_4));
                break;
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
