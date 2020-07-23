/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.PeriodoRepository;
import espe.edu.ec.carga_horaria.model.Actividades;
import espe.edu.ec.carga_horaria.model.ActividadesPK;

import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.cabeceraVo;
import espe.edu.ec.util.mensajes;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import espe.edu.ec.carga_horaria.dao.ActividadesRepository;
import espe.edu.ec.carga_horaria.dao.EmpleadoRepository;
import espe.edu.ec.carga_horaria.model.Empleado;
import espe.edu.ec.carga_horaria.vo.SubActividadesDocenteVo;
import espe.edu.ec.carga_horaria.vo.actividadesVo;
import espe.edu.ec.carga_horaria.vo.empleadoVo;
import java.text.SimpleDateFormat;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Vanessa
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sch")
public class ActividadesRest {

    @Autowired
    private ActividadesRepository cabRep;

    @Autowired
    private apiVo cabeceraApiVo;

    @Autowired
    private PeriodoRepository periodoRep;


    @Autowired
    private EmpleadoRepository emp;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final mensajes msg = new mensajes();

    //Devuelve registro de todas las cabeceras FUNCIONANDO
    @RequestMapping(value = "/cabsAll", method = RequestMethod.GET)
    public ResponseEntity<Actividades> getCab() throws SQLException {
        List<Actividades> res = cabRep.findAllActividades();
        return new ResponseEntity(res, HttpStatus.OK);
    }

    //verificar si una actividad ya existe
    @RequestMapping(value = "/cb/{actividad}/{fechaIni}/{pidm}", method = RequestMethod.GET)
    public ResponseEntity verificarAct(@PathVariable String actividad, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm) throws SQLException {
        ActividadesPK id = new ActividadesPK(actividad, fechaIni, pidm);
        Actividades verificar = cabRep.findByid(id);
        if (verificar != null) {
            return new ResponseEntity(false, HttpStatus.OK);
        } else {
            return new ResponseEntity(true, HttpStatus.OK);
        }
    }

    //trae las cabeceras de una persona
//    @RequestMapping(value = "/getCa/{actividad}/{periodo}/{pidm}", method = RequestMethod.GET)
//    public ResponseEntity getCabeceras(@PathVariable String actividad, @PathVariable String periodo, @PathVariable int pidm) throws SQLException {
//        List<Cabecera> resp = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodoAndPzptcabperjactActividad(pidm, periodo, actividad);
//
//       
//            return new ResponseEntity(resp, HttpStatus.OK);
//        
//    }
    //actividades con un determinado periodo y pidm FUNCIONANDO
    @RequestMapping(value = "/act/{periodo}/{pidm}", method = RequestMethod.GET)
    public ResponseEntity actividadesDocentem(@PathVariable String periodo, @PathVariable long pidm) throws SQLException {
        String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidm + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ORDER BY pzptcabperjact_Actividad";
        List<actividadesVo> actv = cabeceraApiVo.getActDoc(dato);
        return new ResponseEntity(actv, HttpStatus.OK);
    }

    //intento funciona sume horas y permita agregar una subactividad dependiendo dedicacion EN USO
    @RequestMapping(value = "/horaAdd/{periodo}/{pidm}/{horas}", method = RequestMethod.GET)
    public ResponseEntity horasActivd(@PathVariable String periodo, @PathVariable int pidm, @PathVariable int horas) throws SQLException {
        String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidm + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ORDER BY pzptcabperjact_Actividad";
        List<actividadesVo> actv = cabeceraApiVo.getActDoc(dato);
        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();

        int horaSb = 0;
        for (int i = 0; i < actv.size(); i++) {
            horaSb += actv.get(i).getPzptcabperjact_horas();
        }
        System.out.println("horaSb" + horaSb);
        System.out.println("hora" + horas);
        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;

        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }
        System.out.println("code" + codDed);

        if (horaSb + horas <= tiempo && horas > 0) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }

    //consulta por actividad,fechaInicio y pidm FUNCIONANDO
    @RequestMapping(value = "/getCa/{actividad}/{fechaIni}/{pidm}", method = RequestMethod.GET)
    public ResponseEntity getActividadById(@PathVariable String actividad, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm) throws SQLException {
        ActividadesPK id = new ActividadesPK(actividad, fechaIni, pidm);
        Actividades p = cabRep.findByid(id);
        return new ResponseEntity(p, HttpStatus.OK);
    }

    //consulta todas las actividades de una sola persona FUNCIONANDO
    @RequestMapping(value = "/perCab/{pidm}", method = RequestMethod.GET)
    public ResponseEntity actByPidm(@PathVariable int pidm) throws SQLException {
        String dat = " where pzptcabperjact_pidm = '" + pidm + "'";
        List<cabeceraVo> resp = cabeceraApiVo.getCabecera(dat);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    //Eliminar actividad por código de actividad, fechaInicio y pidm  FUNCIONANDO
    @Transactional
    @RequestMapping(value = "/dela/{actividad}/{fechaIni}/{pidm}", method = RequestMethod.DELETE)
    public ResponseEntity periodoDocentePidmCodPer(@PathVariable String actividad, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm) throws SQLException {

        ActividadesPK id = new ActividadesPK(actividad, fechaIni, pidm);
        cabRep.findByid(id);
        cabRep.deleteByid(id);

        return new ResponseEntity(msg.delete(), HttpStatus.OK);
    }

//    //@Transactional
//    @RequestMapping(value = "/delCab/{actividad}/{fechaIni}/{pidm}", method = RequestMethod.DELETE)
//    public ResponseEntity periodoDocentePidmCodPer(@PathVariable String actividad, @PathVariable("fechaIni") @DateTimeFormat(pattern="yyyy-MM-dd") Date fechaIni, @PathVariable int pidm) throws SQLException {
//        String dat = " where pzptcabperjact_actividad = '" + actividad + "' AND pzptcabperjact_fecha_inicio = '" + fechaIni + "' AND pzptcabperjact_pidm = '" + pidm + "' ";
//        List<cabeceraVo> resp = cabeceraApiVo.deleteCabecera(dat);
//        return new ResponseEntity(msg.delete(), HttpStatus.OK);
//    }
//    @RequestMapping(value = "/delAct/{pidm}/{periodo}/{actividad}", method = RequestMethod.DELETE)
//    public ResponseEntity<Cabecera> deleteAct(@PathVariable int pidm, @PathVariable String periodo, @PathVariable String actividad) throws SQLException {
//        Cabecera deletepk = new Cabecera();
//        deletepk.setPzptcabperjactPidm(pidm);
//        deletepk.setPzptcabperjactPeriodo(periodo);
//        deletepk.setPzptcabperjactActividad(actividad);
//        cabecera.delete(deletepk);
//        return new ResponseEntity(deletepk, HttpStatus.OK);
//    }
    //Agregar una actividad SI COMPRUEBA QUE AÚN NO EXISTE.... sin uso... FUNCIONANDO
    @RequestMapping(value = "/cabs1", method = RequestMethod.POST)
    public ResponseEntity<Actividades> agregarCab(@RequestBody Actividades cabecer) throws SQLException {
        Actividades cabExist = new Actividades();
        cabExist = cabRep.findByid(cabecer.id);
        if (cabExist != null) {
            return new ResponseEntity(msg.ifexiste(), HttpStatus.OK);
        } else {
            if (cabecer.getId().getActividad() == "02" || cabecer.getId().getActividad() == "03" || cabecer.getId().getActividad() == "04") {
                return new ResponseEntity(false, HttpStatus.OK);
            }
            cabRep.save(cabecer);
            return new ResponseEntity(msg.add(), HttpStatus.OK);
        }
    }

    //Agregar una actividad a la tabla de cabecera(pzptcabperjact) FUNCIONANDO reemplazada sin uso
    @RequestMapping(value = "/cabs", method = RequestMethod.POST)
    public ResponseEntity<Actividades> agregarCab1(@RequestBody Actividades cabecer) throws SQLException {
        cabRep.save(cabecer);
        // return new ResponseEntity(msg.add(), HttpStatus.CREATED);
        return new ResponseEntity(cabecer, HttpStatus.CREATED);
    }

    //
    
    
    @RequestMapping(value = "/cabsUPDATE/{codeA}/{fechaIni}/{pidm}", method = RequestMethod.PUT)
    public ResponseEntity<Actividades> actualizarActividad(@PathVariable String codeA, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm) throws SQLException {
        ActividadesPK id = new ActividadesPK(codeA, fechaIni, pidm);
        Actividades cd = cabRep.findByid(id);

        //String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = " + pidm + " AND ST.STVTERM_START_DATE = '" + fechaIni + "' AND P.perjact_jact_code= '" + codeA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        //List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        System.out.println(fechaIni);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String fechaFormat = formatter.format(fechaIni);
        System.out.println(formatter.format(fechaIni));
        String subAct = "SELECT P.PERJACT_ASTY_CODE, S.STVASTY_DESC,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, S.STVASTY_ACTIVITY_DATE, P.PERJACT_SUFF AS SUFF, P.PERJACT_POSN AS POSN, P.PERJACT_PIDM AS PIDM, P.PERJACT_EFFECTIVE_DATE AS FECHAE, P.PERJACT_JACT_CODE AS ACTIVIDAD,  P.PERJACT_DICD_CODE AS CODPROVINCIA "
                + "from PAYROLL.PERJACT P "
                + "INNER JOIN payroll.pzptcabperjact CP ON p.perjact_pidm = CP.pzptcabperjact_pidm "
                + "INNER JOIN SATURN.STVASTY S ON p.perjact_asty_code = s.stvasty_code  "
                + "INNER JOIN SATURN.STVTERM ST ON cp.pzptcabperjact_fecha_inicio = st.stvterm_start_date  "
                + "INNER JOIN PAYROLL.PERJACT P ON cp.pzptcabperjact_actividad = P.PERJACT_JACT_CODE  "
                + "where P.perjact_effective_date >= ST.stvterm_start_date "
                + "AND P.perjact_effective_date <= ST.stvterm_end_date "
                + "AND P.PERJACT_PIDM =" + pidm + " "
                + "AND ST.STVTERM_START_DATE ='26-03-2019'"
                + "AND P.perjact_jact_code= " + codeA + " "
                + "GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE \n"
                + "ORDER BY PERJACT_ASTY_CODE";

        List<SubActividadesDocenteVo> listaSub = jdbcTemplate.query(subAct, new BeanPropertyRowMapper<>(SubActividadesDocenteVo.class));

        int suma = 0;
        for (int i = 0; i < listaSub.size(); i++) {
            suma += listaSub.get(i).getPerjact_std_hrs_per_pay();
        }
        cd.setHorasA(suma);

        cabRep.save(cd);
        return new ResponseEntity(cd, HttpStatus.OK);
    }

    //EDITAR UNA ACTIVIDAD FUNCIONANDO
    @RequestMapping(value = "/updateActividad", method = RequestMethod.PUT)
    public ResponseEntity<Actividades> actualizarSistema(@RequestBody Actividades cur) {
        cabRep.save(cur);
        return new ResponseEntity(msg.update(), HttpStatus.OK);
    }

    //    Funcion Actualizar 
//    @RequestMapping(value = "/cabsUPDATE/{pidm}/{fechaIni}/{codeA}", method = RequestMethod.PUT)
//    public ResponseEntity<Actividades> actualizarActividad(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' AND P.perjact_jact_code= '" + codeA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//        int suma=0;
//        for(int i =0;i<subact.size();i++){
//           suma += subact.get(i).getPerjact_std_hrs_per_pay();
//        }
//        return new ResponseEntity(suma, HttpStatus.OK);
//    }
    //Depende del tipo de contrato que tenga el docente permite el ingreso de horas
//        @RequestMapping(value = ("sumaHoraActividades/{pidm}/{periodo}/{horaSub}/{codDed}"), method = RequestMethod.GET)
//        public ResponseEntity sumarHorasAct
//        (@PathVariable
//        int pidm,
//                
//        @PathVariable String periodo,
//                
//        @PathVariable int horaSub,
//                
//        @PathVariable String codDed) throws SQLException {
//            List<Cabecera> ca = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, periodo);
//            int fact = 0;
//            int tiempo = 0;
//            codDed.trim();
//            if ("EC".equals(codDed)) {
//                tiempo = 40;
//            } else if ("EP".equals(codDed)) {
//                tiempo = 19;
//            } else if ("EX".equals(codDed)) {
//                tiempo = 20;
//            }
//            for ( int i = 0; i < ca.size(); i++) {
//                fact += ca.get(i).getPzptcabperjactHoras();
//            }
//            fact = fact + horaSub;
//
//            if (fact <= tiempo && horaSub > 0) {
//                return new ResponseEntity(true, HttpStatus.OK);
//            } else {
//                return new ResponseEntity(false, HttpStatus.OK);
//            }
//        }
//        @RequestMapping(value = "/deleteAct/{pidm}/{periodo}/{actividad}", method = RequestMethod.DELETE)
//        public ResponseEntity<CabPeriodo> deletePerAct
//        (@PathVariable int pidm, 
//        @PathVariable String periodo, 
//        @PathVariable String actividad) throws SQLException {
//
//            CabPeriodoPK delete = new CabPeriodoPK();
//            delete.setPzptcabperjactPidm(pidm);
//            delete.setPzptcabperjactPeriodo(periodo);
//            delete.setPzptcabperjactActividad(actividad);
//            cabPeriodoRep.deleteById(delete);
//            return new ResponseEntity(msg.delete(), HttpStatus.OK);
//        }
    //Para editar subactividad, suma las horas de las actividades con la nueva hora a ingresar, verifica max horas dependiendo dedicacion
    @RequestMapping(value = "/horasEditar/{codeA}/{fechaIni}/{pidm}/{horaS}/{horaN}", method = RequestMethod.GET)
    public ResponseEntity horasEditar(@PathVariable String codeA, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm, @PathVariable int horaS, @PathVariable int horaN) throws SQLException {
        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();
        ActividadesPK id = new ActividadesPK(codeA, fechaIni, pidm);
        List<Actividades> verificar = cabRep.findAllById(id);

        int horaT = 0;
        for (int i = 0; i < verificar.size(); i++) {

            horaT += verificar.get(i).getHorasA();
        }

        horaT = horaT - horaS + horaN;

        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;
        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }

        if (horaT <= tiempo) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }
//editarrr horas intento
    //intento funciona sume horas y permita agregar una subactividad dependiendo dedicacion EN USO

    @RequestMapping(value = "/hEdit/{periodo}/{pidm}/{horaS}/{horaN}", method = RequestMethod.GET)
    public ResponseEntity hor(@PathVariable String periodo, @PathVariable int pidm, @PathVariable int horaS, @PathVariable int horaN) throws SQLException {
        String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidm + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ORDER BY pzptcabperjact_Actividad";
        List<actividadesVo> actv = cabeceraApiVo.getActDoc(dato);
        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();

        int horaT = 0;
        for (int i = 0; i < actv.size(); i++) {
            horaT += actv.get(i).getPzptcabperjact_horas();
        }
                horaT = horaT - horaS + horaN;
        
        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;

        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }

        if (horaT <= tiempo && horaN > 0) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }

}
