/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;


/**
 *
 * @author Vanessa
 */
@Entity
@Table(name = "PZPTCABPERJACT", schema = "PAYROLL")
public class Actividades implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    public ActividadesPK id;
    
   
    @Column(name = "PZPTCABPERJACT_PERIODO")
    public String periodo;
    
    @Basic(optional = false)
    @Column(name = "PZPTCABPERJACT_HORAS")
    private int horasA;
    
    @Column(name = "PZPTCABPERJACT_UNIDAD_GESTION")
    private String unidadGestion;
    
    @Size(max = 50)
    @Column(name = "PZPTCABPERJACT_RESPONSABLE")
    private String responsable;

    @Column(name = "PZPTCABPERJACT_USER_CREATE")
    private int userCrear;
    
    @Column(name = "PZPTCABPERJACT_DATE_CREATE")
    private Date fechaCrear;
    
    @Column(name = "PZPTCABPERJACT_USER_EDIT")
    private int userEditar;
    
    @Column(name = "PZPTCABPERJACT_DATE_EDIT")
    private Date fechaEditar;
   
     public Actividades() {
        id = new ActividadesPK();
    }

    

    public ActividadesPK getId() {
        return id;
    }

    public void setId(ActividadesPK id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public int getHorasA() {
        return horasA;
    }

    public void setHorasA(int horasA) {
        this.horasA = horasA;
    }

   

  

    public String getUnidadGestion() {
        return unidadGestion;
    }

    public void setUnidadGestion(String unidadGestion) {
        this.unidadGestion = unidadGestion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public int getUserCrear() {
        return userCrear;
    }

    public void setUserCrear(int userCrear) {
        this.userCrear = userCrear;
    }

    public Date getFechaCrear() {
        return fechaCrear;
    }

    public void setFechaCrear(Date fechaCrear) {
        this.fechaCrear = fechaCrear;
    }

    public int getUserEditar() {
        return userEditar;
    }

    public void setUserEditar(int userEditar) {
        this.userEditar = userEditar;
    }

    public Date getFechaEditar() {
        return fechaEditar;
    }

    public void setFechaEditar(Date fechaEditar) {
        this.fechaEditar = fechaEditar;
    }

    
}
