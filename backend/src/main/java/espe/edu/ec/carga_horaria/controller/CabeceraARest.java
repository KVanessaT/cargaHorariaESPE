/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.CabeceraARepository;
import espe.edu.ec.carga_horaria.dao.CabeceraRepository;
import espe.edu.ec.carga_horaria.model.Cabecera;
import espe.edu.ec.carga_horaria.model.CabeceraA;
import espe.edu.ec.carga_horaria.model.CabeceraPK;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.cabeceraVo;
import espe.edu.ec.util.mensajes;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import static org.hibernate.annotations.common.util.impl.LoggerFactory.logger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Vanessa
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sch")
public class CabeceraARest {

    @Autowired
    private CabeceraARepository cabRep;

    @Autowired
    private CabeceraRepository cabecera;

    @Autowired
    private apiVo cabeceraRep;

    private final mensajes msg = new mensajes();

    //Devuelve registro cabeceras
    @RequestMapping(value = "/cabsAll", method = RequestMethod.GET)
    public ResponseEntity<CabeceraA> getCab() throws SQLException {
        List<CabeceraA> res = cabRep.findAllCabecera();
        return new ResponseEntity(res, HttpStatus.OK);
    }

    //verificar si una actividad ya existe
    @RequestMapping(value = "/cb/{actividad}/{periodo}/{pidm}", method = RequestMethod.GET)
    public ResponseEntity verficarActividad(@PathVariable String actividad, @PathVariable String periodo, @PathVariable long pidm) throws SQLException {
        List<Cabecera> pidm1 = cabecera.findBypzptcabperjactPidm(pidm);
        List<Cabecera> activ1 = cabecera.findBypzptcabperjactActividad(actividad);
        List<Cabecera> per1 = cabecera.findBypzptcabperjactPeriodo(periodo);
       
        String act = " where pzptcabperjact_pidm = '" + pidm + "' and pzptcabperjact_periodo = '" + periodo + "' and pzptcabperjact_actividad = '" + actividad + "'";
        List<cabeceraVo> caab = cabeceraRep.getCabecera(act);

                 return new ResponseEntity(caab, HttpStatus.OK);

        //System.out.println(per1);
//        if (pidm1.isEmpty() || activ1.isEmpty() || per1.isEmpty()) {
//            return new ResponseEntity(caab, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(false, HttpStatus.OK);
//        }
    }

    //Funcion Eliminar 
    @RequestMapping(value = "/dela/{pidm}/{FechaInicio}/{actividad}", method = RequestMethod.DELETE)
    public ResponseEntity<CabeceraA> deleteById(@PathVariable long pidm, @PathVariable Date FechaInicio, @PathVariable String actividad) throws SQLException {
        CabeceraPK deletepk = new CabeceraPK();
        deletepk.setPzptcabperjactPidm(pidm);
        // deletepk.setPzptcabperjactPeriodo(periodo);
        deletepk.setPzptcabperjactFechaInicio(FechaInicio);
        deletepk.setPzptcabperjactActividad(actividad);
        cabRep.deleteById(deletepk);

        return new ResponseEntity(msg.delete(), HttpStatus.OK);

    }

    //Agregar una actividad.... sin uso... no probada
    @RequestMapping(value = "/cabs1", method = RequestMethod.POST)
    public ResponseEntity<CabeceraA> agregarCab(@RequestBody CabeceraA cabecer) throws SQLException {
        CabeceraA cabExist = new CabeceraA();
        cabExist = cabRep.findByid(cabecer.id);
        if (cabExist != null) {
            return new ResponseEntity(msg.ifexiste(), HttpStatus.CREATED);
        } else {
            CabeceraA cab = new CabeceraA();
            cab.setId(cabecer.getId());
            cab = cabRep.save(cab);
            return new ResponseEntity(cab, HttpStatus.BAD_GATEWAY);
        }
    }

    @RequestMapping(value = "/cabs", method = RequestMethod.POST)
    public ResponseEntity<CabeceraA> agregarCab1(@RequestBody CabeceraA cabecer) throws SQLException {
        CabeceraA pidm = new CabeceraA();
        CabeceraA actividad = new CabeceraA();
        CabeceraA periodo = new CabeceraA();

        cabRep.save(cabecer);
        return new ResponseEntity(cabecer, HttpStatus.CREATED);
    }

    //    Funcion Actualizar 
    //@RequestMapping(value = "/cabsUPDATE", method = RequestMethod.PUT)
    //public ResponseEntity<Cabecera> actualizarActividad(@Valid @RequestBody Cabecera cb) throws SQLException {
    //  cabecera.save(cb);
    //return new ResponseEntity(cb, HttpStatus.OK);
    //}
    //    Funcion Actualizar 
    @RequestMapping(value = "/cabsUPDATE", method = RequestMethod.PUT)
    public ResponseEntity<CabeceraA> actualizarActividad2(@Valid @RequestBody CabeceraPK cb) throws SQLException {

        CabeceraA ca = new CabeceraA();
        ca.getId();
        ca.getPzptcabperjactPeriodo();
        ca.getPzptcabperjactHoras();
        ca.getPzptcabperjactResponsable();
        ca.getPzptcabperjactUnidadGestion();

        ca = cabRep.save(ca);
        return new ResponseEntity(cb, HttpStatus.OK);
    }

    //Depende del tipo de contrato que tenga el docente permite el ingreso de horas
    @RequestMapping(value = ("sumaHoraActividades/{pidm}/{periodo}/{horaSub}/{codDed}"), method = RequestMethod.GET)
    public ResponseEntity sumarHorasAct(@PathVariable Long pidm,
            @PathVariable String periodo,
            @PathVariable int horaSub,
            @PathVariable String codDed) throws SQLException {
        List<Cabecera> ca = cabecera.findByPzptcabperjactPidmAndPzptcabperjactPeriodo(pidm, periodo);
        int fact = 0;
        int tiempo = 0;
        codDed.trim();
        if ("EC".equals(codDed)) {
            tiempo = 40;
        } else if ("EP".equals(codDed)) {
            tiempo = 19;
        } else if ("EX".equals(codDed)) {
            tiempo = 20;
        }
        for (int i = 0; i < ca.size(); i++) {
            fact += ca.get(i).getPzptcabperjactHoras();
        }
        fact = fact + horaSub;
        System.out.println(tiempo);
        System.out.println(codDed);
        if (fact <= tiempo && horaSub > 0) {
            return new ResponseEntity(true, HttpStatus.OK);
        } else {
            return new ResponseEntity(false, HttpStatus.OK);
        }
    }
}
