/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.vo;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vanessa
 */
@Service
public class apiVo {

    private static String datosEmp = " FROM BANINST1.BZSVEMPLEADO";

    private static String docentes = " FROM BANINST1.BZSVEMPLEADO BZ "
            + "INNER JOIN PAYROLL.PZPTCABPERJACT PZ ON BZ.PEBEMPL_PIDM = PZ.PZPTCABPERJACT_PIDM";

    private static String datosPer = " FROM SATURN.STVTERM";

    private static String datosPeriodosActualesDocente = " FROM saturn.Stvterm ,saturn.Sobterm, payroll.pzptcabperjact";

    //private static String datosActividadesDocentes = " FROM payroll.Pzptcabperjact CP ";
    private static String actividades = " from payroll.pzptcabperjact CP "
            /* + " INNER JOIN PAYROLL.PERJACT P ON CP.pzptcabperjact_actividad = P.perjact_jact_code "*/
            + " INNER JOIN PAYROLL.ptvjact PJ ON CP.PZPTCABPERJACT_actividad = pj.ptvjact_code";

    private static String subactividades = " from PAYROLL.PERJACT P"
            + " INNER JOIN payroll.pzptcabperjact CP ON p.perjact_pidm = CP.pzptcabperjact_pidm "
            + " INNER JOIN SATURN.STVASTY S ON p.perjact_asty_code = s.stvasty_code  "
            + " INNER JOIN SATURN.STVTERM ST ON cp.pzptcabperjact_fecha_inicio = st.stvterm_start_date "
            + " INNER JOIN PAYROLL.PERJACT P ON cp.pzptcabperjact_actividad = P.PERJACT_JACT_CODE ";

    private static String subactividadesCode = " from PAYROLL.PERJACT P"
            + " INNER JOIN payroll.pzptcabperjact CP ON p.perjact_pidm = CP.pzptcabperjact_pidm "
            + " INNER JOIN SATURN.STVASTY S ON p.perjact_asty_code = s.stvasty_code  "
            + " INNER JOIN SATURN.STVTERM ST ON cp.pzptcabperjact_periodo = st.stvterm_code "
            + " INNER JOIN PAYROLL.PERJACT P ON cp.pzptcabperjact_actividad = P.PERJACT_JACT_CODE ";

    private static String subActiListCode = " from saturn.stvasty";

    private static String cabecera = " from PAYROLL.PZPTCABPERJACT";

    private static String posn = " FROM NBRJOBS";
    
    private static String seccion = " FROM FTVORGN F "
            + " INNER JOIN PEBEMPL P ON F.FTVORGN_ORGN_CODE = P.PEBEMPL_ORGN_CODE_HOME ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //lista de empleados desde baninst1.bzsvempleado
    public List<empleadoVo> getEmpleado(String q) throws SQLException {

        String datosE = "SELECT PEBEMPL_PIDM, ID_BANNER, APELLIDO, NOMBRES, PREFIJO, DEDICACION,CODE_DEDICACION as CODE ";
        return jdbcTemplate.query(datosE + datosEmp + q, new BeanPropertyRowMapper<>(empleadoVo.class));
    }

    //empleados mismo campus, departamento y periodo
    public List<empleadoVo> getEmpCDP(String q) throws SQLException {

        String datosE = "SELECT DISTINCT PEBEMPL_PIDM, ID_BANNER, APELLIDO, NOMBRES, DEDICACION,CODE_DEDICACION AS CODE ";
        return jdbcTemplate.query(datosE + docentes + q, new BeanPropertyRowMapper<>(empleadoVo.class));
    }

    public List<empleadoVo> getSeccion(String q) throws SQLException {
        String datosE = "SELECT DISTINCT SECCION ";
        return jdbcTemplate.query(datosE + datosEmp + q, new BeanPropertyRowMapper<>(empleadoVo.class));
    }

    //lista de periodos desde saturn.stvterm 
    public List<periodoVo> getPeriodo(String q) throws SQLException {
        String datosP = "SELECT stvterm_code, stvterm_desc, stvterm_start_date, stvterm_end_date";
        return jdbcTemplate.query(datosP + datosPeriodosActualesDocente + q, new BeanPropertyRowMapper<>(periodoVo.class));
    }

    //lista de los periodos actuales del docente
    public List<periodoVo> getPeriodosActualesDocente(String q) throws SQLException {

        String datosPerActDoc = "SELECT stvterm_code, stvterm_desc, stvterm_start_date, stvterm_end_date";
        return jdbcTemplate.query(datosPerActDoc + datosPeriodosActualesDocente + q, new BeanPropertyRowMapper<>(periodoVo.class));
    }

    //lista actividades del docente
    public List<actividadesVo> getActDoc(String q) throws SQLException {
        String act = "select pzptcabperjact_actividad, PTVJACT_DESC, pzptcabperjact_periodo, pzptcabperjact_horas, pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ";
        return jdbcTemplate.query(act + actividades + q, new BeanPropertyRowMapper<>(actividadesVo.class));
    }

//lista de las subactividades 
    public List<SubActividadesDocenteVo> getSubAct(String q) throws SQLException {
        String subAct = "SELECT P.PERJACT_ASTY_CODE, S.STVASTY_DESC,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, S.STVASTY_ACTIVITY_DATE, P.PERJACT_SUFF AS SUFF, P.PERJACT_POSN AS POSN, P.PERJACT_PIDM AS PIDM, P.PERJACT_EFFECTIVE_DATE AS FECHAE, P.PERJACT_JACT_CODE AS ACTIVIDAD,  P.PERJACT_DICD_CODE AS CODPROVINCIA";

        return jdbcTemplate.query(subAct + subactividades + q, new BeanPropertyRowMapper<>(SubActividadesDocenteVo.class));
    }
//lista de las subactividades 2
    public List<SubActividadesDocenteVo2> getSubAct2(String q) throws SQLException {
        String subAct = "SELECT P.PERJACT_ASTY_CODE, S.STVASTY_DESC,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, S.STVASTY_ACTIVITY_DATE, P.PERJACT_SUFF AS SUFF, P.PERJACT_POSN AS POSN, P.PERJACT_PIDM AS PIDM, P.PERJACT_EFFECTIVE_DATE AS FECHAE, P.PERJACT_JACT_CODE AS ACTIVIDAD,  P.PERJACT_DICD_CODE AS CODPROVINCIA";

        return jdbcTemplate.query(subAct + subactividades + q, new BeanPropertyRowMapper<>(SubActividadesDocenteVo2.class));
    }
    
    public List<subactividadesVo> getSubActByCodeAct(String q) throws SQLException {
        String subAct = "SELECT stvasty_code, stvasty_desc";
        return jdbcTemplate.query(subAct + subActiListCode + q, new BeanPropertyRowMapper<>(subactividadesVo.class));
    }

    public List<cabeceraVo> getCabecera(String q) throws SQLException {
        String cabe = "SELECT pzptcabperjact_actividad, pzptcabperjact_periodo, pzptcabperjact_horas, pzptcabperjact_fecha_inicio, pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm";
        return jdbcTemplate.query(cabe + cabecera + q, new BeanPropertyRowMapper<>(cabeceraVo.class));

    }

    public List<cabeceraVo> deleteCabecera(String q) throws SQLException {
        String cabe = "DELETE ";
        return jdbcTemplate.query(cabe + cabecera + q, new BeanPropertyRowMapper<>(cabeceraVo.class));

    }
//lista de las subactividades 

    public List<SubActividadesDocenteVo> getSubByCode(String q) throws SQLException {
        //String subAct = "SELECT P.PERJACT_ASTY_CODE, S.STVASTY_DESC,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, S.STVASTY_ACTIVITY_DATE";
        String subAct = "SELECT P.PERJACT_ASTY_CODE, S.STVASTY_DESC,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, S.STVASTY_ACTIVITY_DATE, P.PERJACT_SUFF AS SUFF, P.PERJACT_POSN AS POSN, P.PERJACT_PIDM AS PIDM, P.PERJACT_EFFECTIVE_DATE AS FECHAE, P.PERJACT_JACT_CODE AS ACTIVIDAD,  P.PERJACT_DICD_CODE AS CODPROVINCIA";

        return jdbcTemplate.query(subAct + subactividadesCode + q, new BeanPropertyRowMapper<>(SubActividadesDocenteVo.class));
    }

    //codigo posicion de un docente
    public List<PosicionDocVo> getPosn(String q) throws SQLException {
        String pos = "SELECT DISTINCT NBRJOBS_POSN, NBRJOBS_ECLS_CODE";
        return jdbcTemplate.query(pos + posn + q, new BeanPropertyRowMapper<>(PosicionDocVo.class));
    }
    
     public List<seccionVo> getSection(String q) throws SQLException {
        String sec = "SELECT DISTINCT F.FTVORGN_TITLE AS SECCION";
        return jdbcTemplate.query(sec + seccion + q, new BeanPropertyRowMapper<>(seccionVo.class));
    }
}
