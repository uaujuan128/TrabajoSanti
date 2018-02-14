/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
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
