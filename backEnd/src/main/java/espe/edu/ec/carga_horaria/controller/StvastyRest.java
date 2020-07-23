/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import com.google.gson.Gson;
import espe.edu.ec.carga_horaria.dao.ActividadesRepository;
import espe.edu.ec.carga_horaria.dao.EmpleadoRepository;
import espe.edu.ec.carga_horaria.model.Empleado;
import espe.edu.ec.carga_horaria.model.Subactividades;
import espe.edu.ec.carga_horaria.model.SubactividadesPK;
import espe.edu.ec.carga_horaria.model.Stvasty;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.SubActividadesDocenteVo;
import espe.edu.ec.carga_horaria.vo.SubActividadesDocenteVo2;
import espe.edu.ec.carga_horaria.vo.subactividadesVo;
import espe.edu.ec.util.mensajes;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import javax.validation.Valid;
import static jdk.nashorn.internal.runtime.Debug.id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import espe.edu.ec.carga_horaria.dao.StvastyRepository;
import espe.edu.ec.carga_horaria.dao.SubactividadesRepository;
import espe.edu.ec.carga_horaria.model.Actividades;
import espe.edu.ec.carga_horaria.model.ActividadesPK;
import espe.edu.ec.carga_horaria.vo.actividadesVo;

/**
 *
 * @author Vanessa
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sch")
public class StvastyRest {

    @Autowired
    private StvastyRepository subactividadesRep;

    @Autowired
    private apiVo subactivRep;

    @Autowired
    private SubactividadesRepository perRep;

    private final mensajes msg = new mensajes();

    @Autowired
    private EmpleadoRepository emp;

    @Autowired
    private SubactividadesRest perSub;
    
        @Autowired
    private ActividadesRepository activRep;
        
        

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //Lista todas las subactividades que hay en la tabla de SATURN.STVASTY FUNCIONANDO
    @RequestMapping(value = "/allSubactividades", method = RequestMethod.GET)
    public ResponseEntity<Stvasty> listaSubActividades() {
        List<Stvasty> subactividades = subactividadesRep.findAllSubActividades();
        if (subactividades.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(subactividades, HttpStatus.OK);
        }
    }

//    //lista las subactividades del docente dependiendo de actividad FUNCIONANDO
//    @RequestMapping(value = "/subA/{pidm}/{codP}/{codA}", method = RequestMethod.GET)
//    public ResponseEntity subactividadesDoc(@PathVariable String pidm, @PathVariable String codP, @PathVariable String codA) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codP + "' AND P.perjact_jact_code= '" + codA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//        return new ResponseEntity(subact, HttpStatus.OK);
//    }
//
//    //horas totales subactividades dependiendo actividad FUNCIONANDO
//    @RequestMapping(value = "/sumaH/{pidm}/{codeP}/{codeA}", method = RequestMethod.GET)
//    public ResponseEntity horasSubactividades(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' AND P.perjact_jact_code= '" + codeA + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT,  P.PERJACT_SUFF,P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//        int horas = 0;
//        for (int i = 0; i < subact.size(); i++) {
//            horas += subact.get(i).getPerjact_std_hrs_per_pay();
//        }
//        return new ResponseEntity(horas, HttpStatus.OK);
//    }
//
//    //suma horas totales de subactividades FUNCIONANDO
//    @RequestMapping(value = "/sumaHT/{pidm}/{codeP}", method = RequestMethod.GET)
//    public ResponseEntity horasSubact(@PathVariable int pidm, @PathVariable String codeP) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//        int horas = 0;
//        for (int i = 0; i < subact.size(); i++) {
//            horas += subact.get(i).getPerjact_std_hrs_per_pay();
//        }
//        return new ResponseEntity(horas, HttpStatus.OK);
//    }
//
//    //Lista subactividades de acuerdo a cod actividad FUNCIONANDO
//    @RequestMapping(value = "/sub/{codAct}", method = RequestMethod.GET)
//    public ResponseEntity subactCodeActividad(@PathVariable String codAct) throws SQLException {
//        String dato = " where stvasty_Code LIKE '" + codAct + "%'";
//        List<subactividadesVo> subact = subactivRep.getSubActByCodeAct(dato);
//
//        return new ResponseEntity(subact, HttpStatus.OK);
//    }
//
//    //verifica horas para eliminar
//    @RequestMapping(value = "/getSub/{pidm}/{periodo}/{actividad}", method = RequestMethod.GET)
//    public ResponseEntity<SubActividadesDocenteVo> searchByPidm(@PathVariable long pidm, @PathVariable String periodo, @PathVariable String actividad) throws SQLException {
//        String suba = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + periodo + "' AND P.perjact_jact_code= '" + actividad + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subAc = subactivRep.getSubAct(suba);
//        int horas = 0;
//
//        for (int i = 0; i < subAc.size(); i++) {
//
//            horas += subAc.get(i).getPerjact_std_hrs_per_pay();
//        }
//        if (horas == 0) {
//            return new ResponseEntity(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.OK);
//        }
//    }
//
//    //verifica si una subactividad ya existe
//    @RequestMapping(value = "/su/{pidm}/{codeP}/{codeS}", method = RequestMethod.GET)
//    public ResponseEntity verifsubactividadesDoc(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeS) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' AND P.perjact_asty_code= '" + codeS + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//        if (subact.isEmpty()) {
//            return new ResponseEntity(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.OK);
//        }
//        //return new ResponseEntity(subact, HttpStatus.OK);
//    }

    //lista las subactividades del docente dependiendo de actividad
//    @RequestMapping(value = "/subActividad/{pidm}/{codeP}/{codeA}/{horas}", method = RequestMethod.GET)
//    public ResponseEntity subactividades(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA, @PathVariable int horas) throws SQLException {
//        
//        Empleado gh = emp.findByPebemplPidm(pidm);
//        String codDed = gh.getCodeDedicacion();
//        List<Cabecera> resp = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, codeP);
//        int horaSb = 0;
//        for (int i = 0; i < resp.size(); i++) {
//            horaSb += resp.get(i).getPzptcabperjactHoras();
//        }
//        horaSb = horaSb + horas;
//
//        int tiempo = 0;
//        codDed.trim();
//        if ("EC".equals(codDed)) {
//            tiempo = 40;
//        } else if ("EP".equals(codDed)) {
//            tiempo = 19;
//        } else if ("EX".equals(codDed)) {
//            tiempo = 20;
//        }
//
//        if (horaSb <= tiempo && horas > 0) {
//            return new ResponseEntity(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.OK);
//        }
//    }
    //para editar una subactividad, horasTotales se restan las horas de la subac y se suma la nueva hora
//    @RequestMapping(value = "/horasEditar/{pidm}/{codeP}/{codeA}/{horaS}/{horaN}", method = RequestMethod.GET)
//    public ResponseEntity horasEditar(@PathVariable int pidm, @PathVariable String codeP, @PathVariable String codeA, @PathVariable int horaS, @PathVariable int horaN) throws SQLException {
//        Empleado gh = emp.findByPebemplPidm(pidm);
//        String codDed = gh.getCodeDedicacion();
//        List<Cabecera> resp = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, codeP);
//
//        int horaT = 0;
//        for (int i = 0; i < resp.size(); i++) {
//
//            horaT += resp.get(i).getPzptcabperjactHoras();
//        }
//        horaT = horaT - horaS + horaN;
//
//        int tiempo = 0;
//        codDed.trim();
//        if ("EC".equals(codDed)) {
//            tiempo = 40;
//        } else if ("EP".equals(codDed)) {
//            tiempo = 19;
//        } else if ("EX".equals(codDed)) {
//            tiempo = 20;
//        }
//
//        if (horaT <= tiempo && horaN > 0) {
//            return new ResponseEntity(true, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.OK);
//        }
//    }
    
        
//    @RequestMapping(value = "/getSubact", method = RequestMethod.PUT)
//    public ResponseEntity buscarSubact(@RequestBody Perjact cur) throws SQLException {
//        SubactividadesPK git = new SubactividadesPK();
//
//        git.setPidm(cur.getPerjactPidm());
//        git.setPerjactSuff(cur.getPerjactSuff());
//        git.setPerjactPosn(cur.getPerjactPosn());
//        git.setEfectiveDate(cur.getPerjactEffectiveDate());
//        git.setCodSubact(cur.getPerjactAstyCode());
//        git.setCodActividad(cur.getPerjactJactCode());
//        git.setCodProvincia(cur.getPerjactDicdCode());
//        Optional<PerjactSA> ejek = perRep.findById(git);
//        if (ejek != null) {
//            return new ResponseEntity(ejek, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
//        }
//    }
//    @Transactional
//    @RequestMapping(value = "/editSSAA", method = RequestMethod.PUT)
//    public ResponseEntity<PerjactSA> editaSubctivi(@Valid @RequestBody Subactividades corre) throws SQLException {
//        //Optional<PerjactSA> employee = perRep.findById(new SubactividadesPK());
//        Subactividades employee = new Subactividades();
//        //employee.setId(new SubactividadesPK());
//        employee.getId();
//        corre.getId().getPidm(); ya sigue haciendo el autowired
//        corre.getId().getPerjactPosn();
//        corre.getId().getPerjactSuff();
//        corre.getId().getEfectiveDate();
//        corre.getId().getCodProvincia();
//        corre.getId().getCodActividad();
//        corre.getId().getCodSubact();
//        // employee.getClass();
//        // employee.setId(corre.getId());
//        employee.setHoras(corre.getHoras());
//        employee.setPorcentaje(corre.getPorcentaje());
//        employee.setPerjactFte(corre.getPerjactFte());
//        employee.setFechaActividad(corre.getFechaActividad());
//        perRep.save(employee);
//        perRep.save(corre);
//        System.out.println(corre.getId());
//        return new ResponseEntity(corre, HttpStatus.OK);
//    }
    @Transactional
    @RequestMapping(value = "/editSSAA", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> editaSubctivi(@Valid @RequestBody Subactividades corre) throws SQLException {
        //Optional<PerjactSA> employee = perRep.findById(new SubactividadesPK());
        Subactividades employee = new Subactividades();
        //employee.setId(new SubactividadesPK());
        employee.getId().getPidm();
        employee.getId().getPerjactPosn();
        employee.getId().getPerjactSuff();
        employee.getId().getEfectiveDate();
        employee.getId().getCodProvincia();
        employee.getId().getCodActividad();
        employee.getId().getCodSubact();

        employee.setHoras(corre.getHoras());
        employee.setPorcentaje(corre.getPorcentaje());
        employee.setPerjactFte(corre.getPerjactFte());
        employee.setFechaActividad(corre.getFechaActividad());
        //employee.setId(corre.getId());

//        corre.getId();
//        corre.setHoras(employee.getHoras());
//        corre.setPorcentaje(employee.getPorcentaje());
//        corre.setPerjactFte(employee.getPerjactFte());
//        corre.setFechaActividad(employee.getFechaActividad());
        perRep.save(employee);
        perRep.save(corre);
        System.out.println(corre.getHoras());
        return new ResponseEntity(corre, HttpStatus.OK);
    }

    @RequestMapping(value = "/dele/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Subactividades> deleteS(@PathVariable SubactividadesPK id) throws SQLException {
        SubactividadesPK delete = new SubactividadesPK();
        delete.setPidm(id.pidm);
        delete.setPerjactPosn(id.perjactPosn);
        delete.setPerjactSuff(id.perjactSuff);
        delete.setEfectiveDate(id.efectiveDate);
        delete.setCodProvincia(id.codProvincia);
        delete.setCodActividad(id.codActividad);
        delete.setCodSubact(id.codSubact);

        Subactividades azul = new Subactividades();
        azul.setId(id);

        //azul.getHoras();
        //azul.setPorcentaje(BigDecimal.ONE);
        //azul.setPerjactFte(BigDecimal.ZERO);
        //azul.getFechaActividad();
        perRep.deleteById(id);
        //perRep.delete(azul);
        return new ResponseEntity(msg.delete(), HttpStatus.OK);
    }

    //ELIMINAR SUBACTIVIDAD
//    @RequestMapping(value = "/deletSub/{pidm}/{posn}/{suff}/{Edate}/{codPro}/{codA}/{codS}", method = RequestMethod.DELETE)
//    public ResponseEntity<PerjactSA> deleteSAct(@PathVariable int pidm, @PathVariable String posn, @PathVariable String suff, @PathVariable String Edate,
//            @PathVariable String codPro, @PathVariable String codA, @PathVariable String codS) throws SQLException {
//        System.out.println(pidm + "," + posn + "," + suff + "," + Edate + "," + codPro + "," + codA + "," + codS);
//
//        SubactividadesPK delete = new SubactividadesPK();
//        delete.setPidm(pidm);
//        delete.setPerjactPosn(posn);
//        delete.setPerjactSuff(suff);
//        delete.setEfectiveDate(Edate);
//        delete.setCodProvincia(codPro);
//        delete.setCodActividad(codA);
//        delete.setCodSubact(codS);
//        perRep.deleteById(delete);
//        System.out.println(pidm + "," + posn + "," + suff + "," + Edate + "," + codPro + "," + codA + "," + codS);
//        return new ResponseEntity(msg.delete(), HttpStatus.OK);
//    }
//    @RequestMapping(value = "/editSub/{pidm}/{posn}/{suff}/{Edate}/{codPro}/{codA}/{codS}", method = RequestMethod.GET)
//    public ResponseEntity<PerjactSA> EditAct(@PathVariable int pidm, @PathVariable String posn, @PathVariable String suff, @PathVariable String Edate,
//            @PathVariable String codPro, @PathVariable String codA, @PathVariable String codS) throws SQLException {
//        //System.out.println(pidm + "," + posn + "," + suff + "," + Edate + "," + codPro + "," + codA + "," + codS);
//
//        SubactividadesPK editar = new SubactividadesPK();
//        editar.setPidm(pidm);
//        editar.setPerjactPosn(posn);
//        editar.setPerjactSuff(suff);
//        editar.setEfectiveDate(Edate);
//        editar.setCodProvincia(codPro);
//        editar.setCodActividad(codA);
//        editar.setCodSubact(codS);
//
//        Subactividades objeto = perRep.findByid(editar);
//        //Optional<PerjactSA> objeto1 = perRep.findById(editar);
//        System.out.println(pidm + "," + posn + "," + suff + "," + Edate + "," + codPro + "," + codA + "," + codS);
//        return new ResponseEntity(objeto, HttpStatus.OK);
//    }
    //Editar SUBACTIVIDAD
    @Transactional
    @RequestMapping(value = "/updaSub", method = RequestMethod.PUT)
    public ResponseEntity<Subactividades> upSAct(@Valid @RequestBody Subactividades corre) throws SQLException {

        //PerjactSAPK edit = new SubactividadesPK();
        Subactividades sa = new Subactividades();
        sa.getId();
        sa.setHoras(sa.getHoras());
        sa.setPorcentaje(sa.getPorcentaje());
        sa.setPerjactFte(sa.getPerjactFte());
        sa.setFechaActividad(sa.getFechaActividad());
        //corre.getId();
        //corre.getHoras();
        //perRep.save(corre);
        perRep.save(sa);
        return new ResponseEntity(corre, HttpStatus.OK);
    }

    //conteo de horas de actividades
//    @RequestMapping(value = "/trueFalse/{pidm}/{codeP}", method = RequestMethod.GET)
//    public ResponseEntity TrueFalse(@PathVariable int pidm, @PathVariable String codeP) throws SQLException {
//        Empleado gh = emp.findByPebemplPidm(pidm);
//        String codDed = gh.getCodeDedicacion();
//        List<Cabecera> resp = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, codeP);
//        int gu = 0;
//        for (int i = 0; i < resp.size(); i++) {
//            gu += resp.get(i).getPzptcabperjactHoras();
//        }
//
//        int tiempo = 0;
//        codDed.trim();
//        if ("EC".equals(codDed)) {
//            tiempo = 40;
//        } else if ("EP".equals(codDed)) {
//            tiempo = 19;
//        } else if ("EX".equals(codDed)) {
//            tiempo = 20;
//        }
//        if (gu == tiempo) {
//            return new ResponseEntity(1, HttpStatus.OK);
//        } else if (gu >= (tiempo * 0.5)) {
//            return new ResponseEntity(2, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(3, HttpStatus.OK);
//
//        }
//
//    }
 
//conteo de horas de actividades
    @RequestMapping(value = "/porc/{periodo}/{pidm}", method = RequestMethod.GET)
    public ResponseEntity porc(@PathVariable String periodo, @PathVariable int pidm) throws SQLException {
        String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidm + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ORDER BY pzptcabperjact_Actividad";
        Empleado gh = emp.findByPebemplPidm(pidm);
        String codDed = gh.getCodeDedicacion();
        List<actividadesVo> actv = subactivRep.getActDoc(dato);

       // ActividadesPK id = new ActividadesPK(codeA, fechaIni, pidm);
        //id.getPidm();
        //List<Actividades> resp = activRep.findByidAndPeriodo(id, codeP);
        //List<Actividades> resp = activRep.findAllById(id);

        int gu = 0;

        int porcen = 0;
        for (int i = 0; i < actv.size(); i++) {
            gu += actv.get(i).getPzptcabperjact_horas();
        }
        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;
        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }

        porcen = ((gu * 100) / tiempo);

        return new ResponseEntity(porcen, HttpStatus.OK);

    }
    
//    @RequestMapping(value = "/subPercent/{pidm/{fecha}/{horaNP}", method = RequestMethod.GET)
//    public ResponseEntity porcentajeTRY1(@PathVariable int pidm, @PathVariable @DateTimeFormat(iso = ISO.DATE) Date fecha, @PathVariable int horaNP) throws SQLException {
//        Empleado gh = emp.findByPebemplPidm(pidm);
//        List<Perjact> resp = perjactRep.findByPerjactPidmAndPerjactEffectiveDateAndPerjactStdHrsPerPay(pidm, fecha, horaNP);
//
//        int dataHoras = resp.get(pidm).getPerjactStdHrsPerPay();
//        // String codDed = gh.getCodeDedicacion();
//        int horaT = 0;
//        for (int i = 0; i < resp.size(); i++) {
//            horaT += resp.get(i).getPerjactStdHrsPerPay();
//        }
//        float porcent = ((horaNP * 100) / horaT);
//        System.out.println("percent:" + porcent);
//        //List<Cabecera> resp = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, codeP);
//
////        for (int i = 0; i < resp.size(); i++) {
////            horaT += resp.get(i).getPerjactStdHrsPerPay();
////        }
//        //horaT = horaSb + horas;
////        int tiempo = 0;
////        codDed.trim();
////        if ("EC".equals(codDed)) {
////            tiempo = 40;
////        } else if ("EP".equals(codDed)) {
////            tiempo = 19;
////        } else if ("EX".equals(codDed)) {
////            tiempo = 20;
////        }
////        if (horaSb <= tiempo && horas > 0) {
////            return new ResponseEntity(true, HttpStatus.OK);
////        } else {
////            return new ResponseEntity(false, HttpStatus.OK);
////        }
//        //}
//        System.out.println("Total horas:" + dataHoras);
//        return new ResponseEntity(resp, HttpStatus.OK);
//
//        //String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + codeP + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        //List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
//    }
    //lista las subactividades del docente en un periodo
//    @RequestMapping(value = "/sub/{pidm}/{periodo}", method = RequestMethod.GET)
//    public ResponseEntity sDoc(@PathVariable int pidm, @PathVariable String periodo) throws SQLException {
//        String dato = " where P.perjact_effective_date >= ST.stvterm_start_date AND P.perjact_effective_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + periodo + "'  GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT, P.PERJACT_SUFF, P.PERJACT_POSN, P.PERJACT_PIDM, P.PERJACT_EFFECTIVE_DATE,  P.PERJACT_JACT_CODE, P.PERJACT_DICD_CODE ORDER BY PERJACT_ASTY_CODE";
//        List<SubActividadesDocenteVo2> subact = subactivRep.getSubAct2(dato);
//
//        if (subact.isEmpty()) {
//            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
//        } else {
//
//            BigDecimal sh = new BigDecimal("0.0");
//            BigDecimal sm = new BigDecimal("0.0");
//            BigDecimal cien = new BigDecimal("100.0");
//            BigDecimal cero = new BigDecimal("0.0");
//
//            for (int i = 0; i < subact.size(); i++) {
//                sh = sh.add(subact.get(i).getPerjact_std_hrs_per_pay());
//            }
//            System.out.println("horas: " + sh);
//            Subactividades modeloSubAct = new Subactividades();
//
//            List<PerjactSA> LIKO = new ArrayList<PerjactSA>();
//            for (int i = 0; i < subact.size(); i++) {
//                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//                if (subact.get(i).getPerjact_fte() == null) {
//                    subact.get(i).setPerjact_fte(cero);
//                }
//                subact.get(i).setPerjact_percent((subact.get(i).getPerjact_std_hrs_per_pay().multiply(cien).divide(sh, 2, RoundingMode.HALF_UP)));
//                System.out.println(sm);
//                modeloSubAct.getId().setPidm(subact.get(i).getPidm());
//                modeloSubAct.getId().setCodActividad(subact.get(i).getActividad());
//                modeloSubAct.getId().setCodSubact(subact.get(i).getPerjact_asty_code());
//                modeloSubAct.getId().setEfectiveDate(formatter.format(subact.get(i).getFechae()));
//                modeloSubAct.getId().setCodProvincia(subact.get(i).getCodprovincia());
//                modeloSubAct.getId().setPerjactPosn(subact.get(i).getPosn());
//                modeloSubAct.getId().setPerjactSuff(subact.get(i).getSuff());
//                modeloSubAct.setFechaActividad(formatter.format(subact.get(i).getStvasty_activity_date()));
//                modeloSubAct.setPerjactFte(subact.get(i).getPerjact_fte());
//                modeloSubAct.setPorcentaje(subact.get(i).getPerjact_percent());
//                modeloSubAct.setHoras(subact.get(i).getPerjact_std_hrs_per_pay());
//                LIKO.add(i, modeloSubAct);
//            }
//             this.perSub.updSACLista(LIKO);
//            
//            return new ResponseEntity(subact, HttpStatus.OK);
//        }
//    }
}
