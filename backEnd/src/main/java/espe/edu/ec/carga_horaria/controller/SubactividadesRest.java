/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.ActividadesRepository;
import espe.edu.ec.carga_horaria.dao.EmpleadoRepository;
import espe.edu.ec.carga_horaria.dao.PeriodoRepository;
import espe.edu.ec.carga_horaria.model.Periodo;
import espe.edu.ec.carga_horaria.model.Subactividades;
import espe.edu.ec.util.mensajes;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import espe.edu.ec.carga_horaria.dao.SubactividadesRepository;
import espe.edu.ec.carga_horaria.model.Actividades;
import espe.edu.ec.carga_horaria.model.ActividadesPK;
import espe.edu.ec.carga_horaria.model.Empleado;
import espe.edu.ec.carga_horaria.model.SubactividadesPK;
import espe.edu.ec.carga_horaria.vo.SubActividadesDocenteVo;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.subactividadesVo;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Vanessa
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sch")
public class SubactividadesRest {

    @Autowired
    private SubactividadesRepository subacRep;

    @Autowired
    private EmpleadoRepository emp;

    @Autowired
    private PeriodoRepository periodoRep;

    private final mensajes msg = new mensajes();

    @Autowired
    private StvastyRest subPerjact;

    @Autowired
    private apiVo subactivRep;

    @Autowired
    private ActividadesRepository activRep;

    //lista las subactividades del docente dependiendo de actividad FUNCIONANDO
    @RequestMapping(value = "/subA/{pidm}/{codP}/{codA}", method = RequestMethod.GET)
    public ResponseEntity subactividadesDoc(@PathVariable String pidm, @PathVariable String codP, @PathVariable String codA) throws SQLException {
        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codP + "' AND P.perjact_jact_code= '" + codA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        return new ResponseEntity(subact, HttpStatus.OK);
    }

    //horas totales subactividades dependiendo actividad FUNCIONANDO
    @RequestMapping(value = "/sumaH/{pidm}/{codeP}/{codeA}", method = RequestMethod.GET)
    public ResponseEntity horasSubactividades(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA) throws SQLException {
        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' AND P.perjact_jact_code= '" + codeA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT,  P.PERJACT_SUFF,P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        int horas = 0;
        for (int i = 0; i < subact.size(); i++) {
            horas += subact.get(i).getPerjact_std_hrs_per_pay();
        }
        return new ResponseEntity(horas, HttpStatus.OK);
    }

    //suma horas totales de subactividades FUNCIONANDO
    @RequestMapping(value = "/sumaHT/{pidm}/{codeP}", method = RequestMethod.GET)
    public ResponseEntity horasSubact(@PathVariable int pidm, @PathVariable String codeP) throws SQLException {
        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        int horas = 0;
        for (int i = 0; i < subact.size(); i++) {
            horas += subact.get(i).getPerjact_std_hrs_per_pay();
        }
        return new ResponseEntity(horas, HttpStatus.OK);
    }

    //Lista subactividades de acuerdo a cod actividad FUNCIONANDO
    @RequestMapping(value = "/sub/{codAct}", method = RequestMethod.GET)
    public ResponseEntity subactCodeActividad(@PathVariable String codAct) throws SQLException {
        String dato = " where stvasty_Code LIKE '" + codAct + "%'";
        List<subactividadesVo> subact = subactivRep.getSubActByCodeAct(dato);

        return new ResponseEntity(subact, HttpStatus.OK);
    }

    //verifica horas para eliminar
    @RequestMapping(value = "/getSub/{pidm}/{periodo}/{actividad}", method = RequestMethod.GET)
    public ResponseEntity<SubActividadesDocenteVo> searchByPidm(@PathVariable long pidm, @PathVariable String periodo, @PathVariable String actividad) throws SQLException {
        String suba = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + periodo + "' AND P.perjact_jact_code= '" + actividad + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subAc = subactivRep.getSubAct(suba);
        int horas = 0;

        for (int i = 0; i < subAc.size(); i++) {

            horas += subAc.get(i).getPerjact_std_hrs_per_pay();
        }
        if (horas == 0) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }

    //verifica si una subactividad ya existe
    @RequestMapping(value = "/su/{pidm}/{codeP}/{codeS}", method = RequestMethod.GET)
    public ResponseEntity verifsubactividadesDoc(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeS) throws SQLException {
        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' AND P.perjact_asty_code= '" + codeS + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        if (subact.isEmpty()) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
        //return new ResponseEntity(subact, HttpStatus.OK);
    }

    //verificar si una subactividad ya existe
    @RequestMapping(value = "/sact/{pidm}/{perjactPosn}/{perjactSuff}/{efectiveDate}/{codProvincia}/{codActividad}/{codSubact}", method = RequestMethod.GET)
    public ResponseEntity verificarAct(@PathVariable int pidm, @PathVariable String perjactPosn, @PathVariable String perjactSuff, @PathVariable("efectiveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date efectiveDate, @PathVariable String codProvincia, @PathVariable String codActividad, @PathVariable String codSubact) throws SQLException {
        SubactividadesPK id = new SubactividadesPK(pidm, perjactPosn, perjactSuff, efectiveDate, codProvincia, codActividad, codSubact);
        Subactividades verificar = subacRep.findByid(id);
        if (verificar != null) {
            return new ResponseEntity(false, HttpStatus.OK);
        } else {
            return new ResponseEntity(true, HttpStatus.OK);
        }
    }

    //
    @RequestMapping(value = "/subAct/{pidm}/{perjactPosn}/{perjactSuff}/{efectiveDate}/{codProvincia}/{codActividad}/{codSubact}", method = RequestMethod.GET)
    public ResponseEntity verSAct(@PathVariable int pidm, @PathVariable String perjactPosn, @PathVariable String perjactSuff, @PathVariable("efectiveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date efectiveDate, @PathVariable String codProvincia, @PathVariable String codActividad, @PathVariable String codSubact) throws SQLException {
        SubactividadesPK id = new SubactividadesPK(pidm, perjactPosn, perjactSuff, efectiveDate, codProvincia, codActividad, codSubact);
        Subactividades verificar = subacRep.findByid(id);

        return new ResponseEntity(verificar, HttpStatus.OK);

    }

    //add subactividad con llave embebida
    @RequestMapping(value = "/addSub", method = RequestMethod.POST)
    public ResponseEntity<Subactividades> agregarSub(@RequestBody Subactividades subactividades) throws SQLException {
        Subactividades subactExist = new Subactividades();
        subactExist = subacRep.findByid(subactividades.id);
        if (subactExist != null) {
            return new ResponseEntity(msg.ifexiste(), HttpStatus.OK);
        } else {
            subacRep.save(subactividades);
            return new ResponseEntity(msg.add(), HttpStatus.OK);
        }
    }

    //agregar una sub actividad
    @RequestMapping(value = "/perjact", method = RequestMethod.POST)
    public ResponseEntity<Subactividades> agregarPerjact(@RequestBody Subactividades cabecer) throws SQLException {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
//        formatter.format(cabecer.getId().getEfectiveDate());
//        formatter1.format(cabecer.getFechaActividad());
//        cabecer.setFechaActividad(formatter1.format(cabecer.getFechaActividad()));
//        cabecer.getId().setEfectiveDate( formatter.format(cabecer.getId().getEfectiveDate()));
//                
        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("**************************************************");
        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("pidm" + cabecer.getId().getPidm());
        System.out.println("posn" + cabecer.getId().getPerjactPosn());
        System.out.println("suff" + cabecer.getId().getPerjactSuff());
        System.out.println("efectiveDate" + cabecer.getId().getEfectiveDate());
        System.out.println("codProvincia" + cabecer.getId().getCodProvincia());
        System.out.println("actividad" + cabecer.getId().getCodActividad());
        System.out.println("subactividad" + cabecer.getId().getCodSubact());
        System.out.println("horas" + cabecer.getHoras());
        System.out.println("porcentaje" + cabecer.getPorcentaje());
        System.out.println("fte" + cabecer.getPerjactFte());
        System.out.println("fechaActividad" + cabecer.getFechaActividad());
        System.out.println("**************************************************");

        //cabecer.getId().setEfectiveDate("26-03-2019");
        //cabecer.setFechaActividad("06-07-2020");
        subacRep.save(cabecer);
        //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //Periodo fechaIni = periodoRep.findByStvtermCode(cabecer.getId().getEfectiveDate());
        //if(fechaIni != null){
        //  this.subPerjact.sDoc(cabecer.getId().getPidm(),fechaIni.getStvtermCode());
        //}
        //else{
        //  System.out.println("no encontro el periodo");
        //}

        return new ResponseEntity(msg.add(), HttpStatus.CREATED);
    }

    //agregar una sub actividad
    @RequestMapping(value = "/perjact1", method = RequestMethod.POST)
    public ResponseEntity<Subactividades> agregarPerjact1(@RequestBody Subactividades cabecer) throws SQLException {

        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("**************************************************");
        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("pidm" + cabecer.getId().getPidm());
        System.out.println("posn" + cabecer.getId().getPerjactPosn());
        System.out.println("suff" + cabecer.getId().getPerjactSuff());
        System.out.println("efectiveDate" + cabecer.getId().getEfectiveDate());
        System.out.println("codProvincia" + cabecer.getId().getCodProvincia());
        System.out.println("actividad" + cabecer.getId().getCodActividad());
        System.out.println("subactividad" + cabecer.getId().getCodSubact());
        System.out.println("horas" + cabecer.getHoras());
        System.out.println("porcentaje" + cabecer.getPorcentaje());
        System.out.println("fte" + cabecer.getPerjactFte());
        System.out.println("fechaActividad" + cabecer.getFechaActividad());
        System.out.println("**************************************************");

        // cabecer.getId().setEfectiveDate("12/12/2019");
        // cabecer.setFechaActividad("12/12/2020");
        subacRep.save(cabecer);
        return new ResponseEntity(msg.add(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/perjact2", method = RequestMethod.POST)
    public ResponseEntity<Subactividades> agregarPerjact2(@RequestBody Subactividades cabecer) throws SQLException {

        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("**************************************************");
        System.out.println("esto imprime al guardar subactividades" + cabecer);
        System.out.println("pidm" + cabecer.getId().getPidm());
        System.out.println("posn" + cabecer.getId().getPerjactPosn());
        System.out.println("suff" + cabecer.getId().getPerjactSuff());
        System.out.println("efectiveDate" + cabecer.getId().getEfectiveDate());
        System.out.println("codProvincia" + cabecer.getId().getCodProvincia());
        System.out.println("actividad" + cabecer.getId().getCodActividad());
        System.out.println("subactividad" + cabecer.getId().getCodSubact());
        System.out.println("horas" + cabecer.getHoras());
        System.out.println("porcentaje" + cabecer.getPorcentaje());
        System.out.println("fte" + cabecer.getPerjactFte());
        System.out.println("fechaActividad" + cabecer.getFechaActividad());
        System.out.println("**************************************************");

//        cabecer.getId().setEfectiveDate("03/26/2019");
        //       cabecer.setFechaActividad("07/06/2020");
        subacRep.save(cabecer);
        return new ResponseEntity(msg.add(), HttpStatus.CREATED);
    }

//editar una subactividad
    @Transactional
    @RequestMapping(value = "/editarSub", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> actualizarSistema(@RequestBody Subactividades cur) throws SQLException {
        subacRep.save(cur);
        return new ResponseEntity(cur, HttpStatus.OK);
    }

    @RequestMapping(value = "/editS", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> editaSubctivi(@Valid @RequestBody Subactividades corre) throws SQLException {
        Subactividades po = new Subactividades();

        subacRep.save(corre);
        return new ResponseEntity(corre, HttpStatus.OK);
    }

//    @Transactional
//    @RequestMapping(value = "/ed", method = RequestMethod.PUT)
//    public ResponseEntity<Perjact> actualizarSistema(@RequestBody Perjact cur) throws SQLException {
//        subjactRep.save(cur);
//        return new ResponseEntity(cur, HttpStatus.OK);
//    }
    @RequestMapping(value = "/editarSAc", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> updSAC(@RequestBody Subactividades cur) {
        subacRep.save(cur);
        return new ResponseEntity(msg.update(), HttpStatus.OK);
    }

    @RequestMapping(value = "/editarSAcLista", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> updSACLista(@RequestBody List<Subactividades> cur) {
        subacRep.saveAll(cur);
        System.out.println("se esta ejectando el servicio");
        return new ResponseEntity(msg.update(), HttpStatus.OK);
    }

    //lista las subactividades del docente dependiendo de actividad
    @RequestMapping(value = "/subActividad/{pidm}/{codeP}/{codeA}/{horas}", method = RequestMethod.GET)
    public ResponseEntity subactividades(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA, @PathVariable int horas) throws SQLException {

        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();
        Periodo per = periodoRep.findByStvtermCode(codeP);
        Date fInicio = per.getStvtermStartDate();
        System.out.println("fecha de inicio" + per.getStvtermStartDate());
        ActividadesPK id = new ActividadesPK(codeA, fInicio, pidm);
       
        //Actividades resp = activRep.findByid(id);
        //verificar.getId().getFechaInicio();
        System.out.println("id:" + id);
        //Date fechaIni = id.getFechaInicio();

        List<Actividades> resp = activRep.findByidAndPeriodo(id, codDed);
        
        System.out.println("horasAa:"+resp.get(pidm).getHorasA());
        //resp.get(horas);
        // List<Actividades> resp = activRep.findByidAndPeriodo(id, codeP);
        int horaSb = 0;
        for (int i = 0; i < resp.size(); i++) {
            horaSb += resp.get(i).getHorasA();
        }
        //horaSb = horaSb + horas;
        System.out.println("horasSB:"+horaSb);
        System.out.println("horas:"+horas);
        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;
        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }

        if (horaSb <= tiempo && horas > 0) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }

    //para editar una subactividad, horasTotales se restan las horas de la subac y se suma la nueva hora
    @RequestMapping(value = "/horasEdit/{codeA}/{fechaIni}/{pidm}/{horaS}/{horaN}", method = RequestMethod.GET)
    public ResponseEntity horasEdit(@PathVariable String codeA, @PathVariable("fechaIni") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaIni, @PathVariable int pidm, @PathVariable int horaS, @PathVariable int horaN) throws SQLException {
        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();
        ActividadesPK id = new ActividadesPK(codeA, fechaIni, pidm);
        List<Actividades> resp = activRep.findAllById(id);

        int horaT = 0;
        for (int i = 0; i < resp.size(); i++) {

            horaT += resp.get(i).getHorasA();
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

    //Eliminar subactividad  FUNCIONANDO
    @Transactional
    @RequestMapping(value = "/delSub/{pidm}/{perjactPosn}/{perjactSuff}/{efectiveDate}/{codProvincia}/{codActividad}/{codSubact}", method = RequestMethod.DELETE)

    public ResponseEntity eliminarSubA(@PathVariable int pidm, @PathVariable String perjactPosn, @PathVariable String perjactSuff, @PathVariable("efectiveDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date efectiveDate, @PathVariable String codProvincia, @PathVariable String codActividad, @PathVariable String codSubact) throws SQLException {

        SubactividadesPK id = new SubactividadesPK(pidm, perjactPosn, perjactSuff, efectiveDate, codProvincia, codActividad, codSubact);
        subacRep.findByid(id);
        subacRep.deleteByid(id);
        return new ResponseEntity(msg.delete(), HttpStatus.OK);
    }
}
