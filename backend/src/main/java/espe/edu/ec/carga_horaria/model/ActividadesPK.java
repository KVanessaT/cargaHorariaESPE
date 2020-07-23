/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Vanessa
 */
@Embeddable
public class ActividadesPK implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @Column(name = "PZPTCABPERJACT_ACTIVIDAD")
    public String actividad;

    //@NotNull
    //@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PZPTCABPERJACT_FECHA_INICIO")
    public Date fechaInicio;

    //@NotNull
    @Column(name = "PZPTCABPERJACT_PIDM")
    public int pidm;

    public ActividadesPK() {
    }

    public ActividadesPK(String actividad, Date fechaInicio, int pidm) {
        this.actividad = actividad;
        this.fechaInicio = fechaInicio;
        this.pidm = pidm;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public int getPidm() {
        return pidm;
    }

    public void setPidm(int pidm) {
        this.pidm = pidm;
    }


@Override
    public int hashCode()
    {
        int hash = 0;
        hash += (actividad != null ? actividad.hashCode() : 0);
        hash += (fechaInicio != null ? fechaInicio.hashCode() : 0);
        hash += (int) pidm;
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof ActividadesPK))
        {
            return false;
        }
        ActividadesPK other = (ActividadesPK) object;
        if((this.actividad == null && other.actividad != null) || (this.actividad != null && !this.actividad.equals(other.actividad)))
        {
            return false;
        }
        if((this.fechaInicio == null && other.fechaInicio != null) || (this.fechaInicio != null && !this.fechaInicio.equals(other.fechaInicio)))
        {
            return false;
        }
        if(this.pidm != other.pidm)
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CabeceraPK{" + "pzptcabperjactActividad=" + actividad + ", pzptcabperjactFechaInicio=" + fechaInicio + ", pzptcabperjactPidm=" + pidm + '}';
    }

}
