/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Juan
 */
public class Movimientos {
    private String mo_ncu;
    private String mo_fec;
    private String mo_hor;
    private String mo_des;
    private int mo_imp;

    public Movimientos() {
    }

    public Movimientos(String mo_ncu, String mo_fec, String mo_hor, String mo_des, int mo_imp) {
        this.mo_ncu = mo_ncu;
        this.mo_fec = mo_fec;
        this.mo_hor = mo_hor;
        this.mo_des = mo_des;
        this.mo_imp = mo_imp;
    }

    public String getMo_ncu() {
        return mo_ncu;
    }

    public void setMo_ncu(String mo_ncu) {
        this.mo_ncu = mo_ncu;
    }

    public String getMo_fec() {
        return mo_fec;
    }

    public void setMo_fec(String mo_fec) {
        this.mo_fec = mo_fec;
    }

    public String getMo_hor() {
        return mo_hor;
    }

    public void setMo_hor(String mo_hor) {
        this.mo_hor = mo_hor;
    }

    public String getMo_des() {
        return mo_des;
    }

    public void setMo_des(String mo_des) {
        this.mo_des = mo_des;
    }

    public int getMo_imp() {
        return mo_imp;
    }

    public void setMo_imp(int mo_imp) {
        this.mo_imp = mo_imp;
    }
    
    
}
