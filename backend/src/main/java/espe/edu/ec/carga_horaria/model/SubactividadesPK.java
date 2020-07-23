/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Vanessa
 */
@Embeddable
public class SubactividadesPK implements Serializable {

    private static final long serialVersionUID = 1L;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "PERJACT_PIDM")
    public int pidm;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 6)
    @Column(name = "PERJACT_POSN")
    public String perjactPosn;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 2)
    @Column(name = "PERJACT_SUFF")
    public String perjactSuff;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Column(name = "PERJACT_EFFECTIVE_DATE")
    //@Temporal(TemporalType.DATE)
    // @JsonFormat(pattern="dd-MM-yyyy")
    public Date efectiveDate;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PERJACT_DICD_CODE")
    public String codProvincia;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 4)
    @Column(name = "PERJACT_JACT_CODE")
    public String codActividad;

    //@GeneratedValue
    //@Basic(optional = false)
    //@NotNull
    @Size(min = 1, max = 4)
    @Column(name = "PERJACT_ASTY_CODE")
    public String codSubact;

    public SubactividadesPK() {
    }

    public SubactividadesPK(int pidm, String perjactPosn, String perjactSuff, Date efectiveDate, String codProvincia, String codActividad, String codSubact) {
        this.pidm = pidm;
        this.perjactPosn = perjactPosn;
        this.perjactSuff = perjactSuff;
        this.efectiveDate = efectiveDate;
        this.codProvincia = codProvincia;
        this.codActividad = codActividad;
        this.codSubact = codSubact;
    }

    public int getPidm() {
        return pidm;
    }

    public void setPidm(int pidm) {
        this.pidm = pidm;
    }

    public String getPerjactPosn() {
        return perjactPosn;
    }

    public void setPerjactPosn(String perjactPosn) {
        this.perjactPosn = perjactPosn;
    }

    public String getPerjactSuff() {
        return perjactSuff;
    }

    public void setPerjactSuff(String perjactSuff) {
        this.perjactSuff = perjactSuff;
    }

    public Date getEfectiveDate() {
        return efectiveDate;
    }

    public void setEfectiveDate(Date efectiveDate) {
        this.efectiveDate = efectiveDate;
    }

    public String getCodProvincia() {
        return codProvincia;
    }

    public void setCodProvincia(String codProvincia) {
        this.codProvincia = codProvincia;
    }

    public String getCodActividad() {
        return codActividad;
    }

    public void setCodActividad(String codActividad) {
        this.codActividad = codActividad;
    }

    public String getCodSubact() {
        return codSubact;
    }

    public void setCodSubact(String codSubact) {
        this.codSubact = codSubact;
    }

 

    
//@Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (int) pidm;
//        hash += (perjactPosn != null ? perjactPosn.hashCode() : 0);
//        hash += (perjactSuff != null ? perjactSuff.hashCode() : 0);
//        hash += (efectiveDate != null ? efectiveDate.hashCode() : 0);
//        hash += (codProvincia != null ? codProvincia.hashCode() : 0);
//        hash += (codActividad != null ? codActividad.hashCode() : 0);
//        hash += (codSubact != null ? codSubact.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof SubactividadesPK)) {
//            return false;
//        }
//        SubactividadesPK other = (SubactividadesPK) object;
//        if (this.pidm != other.pidm) {
//            return false;
//        }
//        if ((this.perjactPosn == null && other.perjactPosn != null) || (this.perjactPosn != null && !this.perjactPosn.equals(other.perjactPosn))) {
//            return false;
//        }
//        if ((this.perjactSuff == null && other.perjactSuff != null) || (this.perjactSuff != null && !this.perjactSuff.equals(other.perjactSuff))) {
//            return false;
//        }
//        if ((this.efectiveDate == null && other.efectiveDate != null) || (this.efectiveDate != null && !this.efectiveDate.equals(other.efectiveDate))) {
//            return false;
//        }
//        if ((this.codProvincia == null && other.codProvincia != null) || (this.codProvincia != null && !this.codProvincia.equals(other.codProvincia))) {
//            return false;
//        }
//        if ((this.codActividad == null && other.codActividad != null) || (this.codActividad != null && !this.codActividad.equals(other.codActividad))) {
//            return false;
//        }
//        if ((this.codSubact == null && other.codSubact != null) || (this.codSubact != null && !this.codSubact.equals(other.codSubact))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "SubactividadesPK{" + "pidm=" + pidm + ", perjactPosn=" + perjactPosn + ", perjactSuff=" + perjactSuff + ", efectiveDate=" + efectiveDate + ", codProvincia=" + codProvincia + ", codActividad=" + codActividad + ", codSubact=" + codSubact + '}';
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) pidm;
        hash += (perjactPosn != null ? perjactPosn.hashCode() : 0);
        hash += (perjactSuff != null ? perjactSuff.hashCode() : 0);
        hash += (efectiveDate != null ? efectiveDate.hashCode() : 0);
        hash += (codProvincia != null ? codProvincia.hashCode() : 0);
        hash += (codActividad != null ? codActividad.hashCode() : 0);
        hash += (codSubact != null ? codSubact.hashCode() : 0);

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof SubactividadesPK))
        {
            return false;
        }
        SubactividadesPK other = (SubactividadesPK) object;
       if(this.pidm != other.pidm)
        {
            return false;
        }
        if((this.perjactPosn == null && other.perjactPosn != null) || (this.perjactPosn != null && !this.perjactPosn.equals(other.perjactPosn)))
        {
            return false;
        }
         if((this.perjactSuff == null && other.perjactSuff != null) || (this.perjactSuff != null && !this.perjactSuff.equals(other.perjactSuff)))
        {
            return false;
        }
          if((this.efectiveDate == null && other.efectiveDate != null) || (this.efectiveDate != null && !this.efectiveDate.equals(other.efectiveDate)))
        {
            return false;
        }
           if((this.codProvincia == null && other.codProvincia != null) || (this.codProvincia != null && !this.codProvincia.equals(other.codProvincia)))
        {
            return false;
        }
            if((this.codActividad == null && other.codActividad != null) || (this.codActividad != null && !this.codActividad.equals(other.codActividad)))
        {
            return false;
        }
             if((this.codSubact == null && other.codSubact != null) || (this.codSubact != null && !this.codSubact.equals(other.codSubact)))
        {
            return false;
        }
        return true;    
    }

    @Override
    public String toString() {
        return "PerjactSAPK{" + "pidm=" + pidm + ", perjactPosn=" + perjactPosn + ", perjactSuff=" + perjactSuff + ", efectiveDate=" + efectiveDate + ", codProvincia=" + codProvincia + ", codActividad=" + codActividad + ", codSubact=" + codSubact + '}';
    }

    
    
}
