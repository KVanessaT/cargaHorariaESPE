/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Vanessa
 */
public class SubActividadesDocenteVo implements Serializable{
    private String perjact_asty_code;
    private String stvasty_desc;
    private int perjact_std_hrs_per_pay;
    private BigDecimal perjact_percent;
    private Date stvasty_activity_date;
    private String suff;
    private String posn;
    private int pidm;
    private Date fechae;
    private String actividad;
    private String codprovincia;




    public SubActividadesDocenteVo() {
    }

    public SubActividadesDocenteVo(String perjact_asty_code, String stvasty_desc, int perjact_std_hrs_per_pay, BigDecimal perjact_percent, Date stvasty_activity_date, String suff, String posn, int pidm, Date fechae, String actividad, String codprovincia) {
        this.perjact_asty_code = perjact_asty_code;
        this.stvasty_desc = stvasty_desc;
        this.perjact_std_hrs_per_pay = perjact_std_hrs_per_pay;
        this.perjact_percent = perjact_percent;
        this.stvasty_activity_date = stvasty_activity_date;
        this.suff = suff;
        this.posn = posn;
        this.pidm = pidm;
        this.fechae = fechae;
        this.actividad = actividad;
        this.codprovincia = codprovincia;
    }

    public String getPerjact_asty_code() {
        return perjact_asty_code;
    }

    public void setPerjact_asty_code(String perjact_asty_code) {
        this.perjact_asty_code = perjact_asty_code;
    }

    public String getStvasty_desc() {
        return stvasty_desc;
    }

    public void setStvasty_desc(String stvasty_desc) {
        this.stvasty_desc = stvasty_desc;
    }

    public int getPerjact_std_hrs_per_pay() {
        return perjact_std_hrs_per_pay;
    }

    public void setPerjact_std_hrs_per_pay(int perjact_std_hrs_per_pay) {
        this.perjact_std_hrs_per_pay = perjact_std_hrs_per_pay;
    }

    public BigDecimal getPerjact_percent() {
        return perjact_percent;
    }

    public void setPerjact_percent(BigDecimal perjact_percent) {
        this.perjact_percent = perjact_percent;
    }

    public Date getStvasty_activity_date() {
        return stvasty_activity_date;
    }

    public void setStvasty_activity_date(Date stvasty_activity_date) {
        this.stvasty_activity_date = stvasty_activity_date;
    }

    public String getSuff() {
        return suff;
    }

    public void setSuff(String suff) {
        this.suff = suff;
    }

    public String getPosn() {
        return posn;
    }

    public void setPosn(String posn) {
        this.posn = posn;
    }

    public int getPidm() {
        return pidm;
    }

    public void setPidm(int pidm) {
        this.pidm = pidm;
    }

    public Date getFechae() {
        return fechae;
    }

    public void setFechae(Date fechae) {
        this.fechae = fechae;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getCodprovincia() {
        return codprovincia;
    }

    public void setCodprovincia(String codprovincia) {
        this.codprovincia = codprovincia;
    }

       
}