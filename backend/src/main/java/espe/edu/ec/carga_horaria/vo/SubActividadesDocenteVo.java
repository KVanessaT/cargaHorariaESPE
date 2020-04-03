/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.vo;

import java.io.Serializable;

/**
 *
 * @author Vanessa
 */
public class SubActividadesDocenteVo implements Serializable{
    private String perjact_asty_code;
    private String stvasty_desc;
    private int perjact_std_hrs_per_pay;
    private String perjact_percent;
    private String stvasty_activity_date;
    private String ptvjact_code;

    public SubActividadesDocenteVo() {
    }

    public SubActividadesDocenteVo(String perjact_asty_code, String stvasty_desc, int perjact_std_hrs_per_pay, String perjact_percent, String stvasty_activity_date, String ptvjact_code) {
        this.perjact_asty_code = perjact_asty_code;
        this.stvasty_desc = stvasty_desc;
        this.perjact_std_hrs_per_pay = perjact_std_hrs_per_pay;
        this.perjact_percent = perjact_percent;
        this.stvasty_activity_date = stvasty_activity_date;
        this.ptvjact_code = ptvjact_code;
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

    public String getPerjact_percent() {
        return perjact_percent;
    }

    public void setPerjact_percent(String perjact_percent) {
        this.perjact_percent = perjact_percent;
    }

    public String getStvasty_activity_date() {
        return stvasty_activity_date;
    }

    public void setStvasty_activity_date(String stvasty_activity_date) {
        this.stvasty_activity_date = stvasty_activity_date;
    }

    public String getPtvjact_code() {
        return ptvjact_code;
    }

    public void setPtvjact_code(String ptvjact_code) {
        this.ptvjact_code = ptvjact_code;
    }
 
    
    
    
    
}