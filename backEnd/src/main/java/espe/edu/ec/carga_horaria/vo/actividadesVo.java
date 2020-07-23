/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.vo;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Vanessa
 */
public class actividadesVo implements Serializable {

    private String pzptcabperjact_actividad;
    private String ptvjact_desc;
    private String pzptcabperjact_periodo;
    private int pzptcabperjact_horas;
    private String pzptcabperjact_unidad_gestion;
    private String pzptcabperjact_responsable;
    private int pzptcabperjact_pidm;
    private int pzptcabperjact_user_create;
    private Date pzptcabperjact_date_create;
   private int pzptcabperjact_user_edit;
    private Date pzptcabperjact_date_edit;

    public actividadesVo() {
    }

    public actividadesVo(String pzptcabperjact_actividad, String ptvjact_desc, String pzptcabperjact_periodo, int pzptcabperjact_horas, String pzptcabperjact_unidad_gestion, String pzptcabperjact_responsable, int pzptcabperjact_pidm, int pzptcabperjact_user_create, Date pzptcabperjact_date_create, int pzptcabperjact_user_edit, Date pzptcabperjact_date_edit) {
        this.pzptcabperjact_actividad = pzptcabperjact_actividad;
        this.ptvjact_desc = ptvjact_desc;
        this.pzptcabperjact_periodo = pzptcabperjact_periodo;
        this.pzptcabperjact_horas = pzptcabperjact_horas;
        this.pzptcabperjact_unidad_gestion = pzptcabperjact_unidad_gestion;
        this.pzptcabperjact_responsable = pzptcabperjact_responsable;
        this.pzptcabperjact_pidm = pzptcabperjact_pidm;
        this.pzptcabperjact_user_create = pzptcabperjact_user_create;
        this.pzptcabperjact_date_create = pzptcabperjact_date_create;
        this.pzptcabperjact_user_edit = pzptcabperjact_user_edit;
        this.pzptcabperjact_date_edit = pzptcabperjact_date_edit;
    }

    public String getPzptcabperjact_actividad() {
        return pzptcabperjact_actividad;
    }

    public void setPzptcabperjact_actividad(String pzptcabperjact_actividad) {
        this.pzptcabperjact_actividad = pzptcabperjact_actividad;
    }

    public String getPtvjact_desc() {
        return ptvjact_desc;
    }

    public void setPtvjact_desc(String ptvjact_desc) {
        this.ptvjact_desc = ptvjact_desc;
    }

    public String getPzptcabperjact_periodo() {
        return pzptcabperjact_periodo;
    }

    public void setPzptcabperjact_periodo(String pzptcabperjact_periodo) {
        this.pzptcabperjact_periodo = pzptcabperjact_periodo;
    }

    public int getPzptcabperjact_horas() {
        return pzptcabperjact_horas;
    }

    public void setPzptcabperjact_horas(int pzptcabperjact_horas) {
        this.pzptcabperjact_horas = pzptcabperjact_horas;
    }

    public String getPzptcabperjact_unidad_gestion() {
        return pzptcabperjact_unidad_gestion;
    }

    public void setPzptcabperjact_unidad_gestion(String pzptcabperjact_unidad_gestion) {
        this.pzptcabperjact_unidad_gestion = pzptcabperjact_unidad_gestion;
    }

    public String getPzptcabperjact_responsable() {
        return pzptcabperjact_responsable;
    }

    public void setPzptcabperjact_responsable(String pzptcabperjact_responsable) {
        this.pzptcabperjact_responsable = pzptcabperjact_responsable;
    }

    public int getPzptcabperjact_pidm() {
        return pzptcabperjact_pidm;
    }

    public void setPzptcabperjact_pidm(int pzptcabperjact_pidm) {
        this.pzptcabperjact_pidm = pzptcabperjact_pidm;
    }

    public int getPzptcabperjact_user_create() {
        return pzptcabperjact_user_create;
    }

    public void setPzptcabperjact_user_create(int pzptcabperjact_user_create) {
        this.pzptcabperjact_user_create = pzptcabperjact_user_create;
    }

    public Date getPzptcabperjact_date_create() {
        return pzptcabperjact_date_create;
    }

    public void setPzptcabperjact_date_create(Date pzptcabperjact_date_create) {
        this.pzptcabperjact_date_create = pzptcabperjact_date_create;
    }

    public int getPzptcabperjact_user_edit() {
        return pzptcabperjact_user_edit;
    }

    public void setPzptcabperjact_user_edit(int pzptcabperjact_user_edit) {
        this.pzptcabperjact_user_edit = pzptcabperjact_user_edit;
    }

    public Date getPzptcabperjact_date_edit() {
        return pzptcabperjact_date_edit;
    }

    public void setPzptcabperjact_date_edit(Date pzptcabperjact_date_edit) {
        this.pzptcabperjact_date_edit = pzptcabperjact_date_edit;
    }

    
}
