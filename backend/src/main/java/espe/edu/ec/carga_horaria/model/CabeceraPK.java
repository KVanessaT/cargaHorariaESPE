/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vanessa
 */
@Embeddable
public class CabeceraPK implements Serializable {

    //private static final long serialVersionUID = 1L;

    @NotNull
    @Column(name = "PZPTCABPERJACT_ACTIVIDAD")
    private String pzptcabperjactActividad;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "PZPTCABPERJACT_FECHA_INICIO")
    private Date pzptcabperjactFechaInicio;

    @NotNull
    @Column(name = "PZPTCABPERJACT_PIDM")
    private long pzptcabperjactPidm;

//    @NotNull
//    @Column(name = "PZPTCABPERJACT_PERIODO")
//    public String pzptcabperjactPeriodo;

    public CabeceraPK() {
    }

    public String getPzptcabperjactActividad() {
        return pzptcabperjactActividad;
    }

    public void setPzptcabperjactActividad(String pzptcabperjactActividad) {
        this.pzptcabperjactActividad = pzptcabperjactActividad;
    }

    public Date getPzptcabperjactFechaInicio() {
        return pzptcabperjactFechaInicio;
    }

    public void setPzptcabperjactFechaInicio(Date pzptcabperjactFechaInicio) {
        this.pzptcabperjactFechaInicio = pzptcabperjactFechaInicio;
    }

    public long getPzptcabperjactPidm() {
        return pzptcabperjactPidm;
    }

    public void setPzptcabperjactPidm(long pzptcabperjactPidm) {
        this.pzptcabperjactPidm = pzptcabperjactPidm;
    }

//    public String getPzptcabperjactActividad() {
//        return pzptcabperjactActividad;
//    }
//
//    public void setPzptcabperjactActividad(String pzptcabperjactActividad) {
//        this.pzptcabperjactActividad = pzptcabperjactActividad;
//    }
//
//    public long getPzptcabperjactPidm() {
//        return pzptcabperjactPidm;
//    }
//
//    public void setPzptcabperjactPidm(long pzptcabperjactPidm) {
//        this.pzptcabperjactPidm = pzptcabperjactPidm;
//    }
//
//    public String getPzptcabperjactPeriodo() {
//        return pzptcabperjactPeriodo;
//    }
//
//    public void setPzptcabperjactPeriodo(String pzptcabperjactPeriodo) {
//        this.pzptcabperjactPeriodo = pzptcabperjactPeriodo;
//    }

}
