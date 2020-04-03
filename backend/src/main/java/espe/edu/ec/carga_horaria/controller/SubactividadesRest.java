/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.SubactividadesRepository;
import espe.edu.ec.carga_horaria.model.subActividades;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.SubActividadesDocenteVo;
import espe.edu.ec.carga_horaria.vo.subactividadesVo;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
public class SubactividadesRest {

    @Autowired
    private SubactividadesRepository subactividadesRep;

    @Autowired
    private apiVo subactivRep;

    //Lista todas las subactividades
    @RequestMapping(value = "/allSubactividades", method = RequestMethod.GET)
    public ResponseEntity<subActividades> listaSubActividades() {
        List<subActividades> subactividades = subactividadesRep.findAllSubActividades();
        if (subactividades.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(subactividades, HttpStatus.OK);
        }
    }

    //lista las subactividades del docente dependiendo de actividad
    @RequestMapping(value = "/subA/{data1}/{data2}/{data3}", method = RequestMethod.GET)
    public ResponseEntity subactividadesDoc(@PathVariable String data1, @PathVariable String data2, @PathVariable String data3) throws SQLException {
        String dato = " where P.perjact_activity_date >= ST.stvterm_start_date AND P.perjact_activity_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + data1 + "' AND ST.STVTERM_CODE = '" + data2 + "' AND P.perjact_jact_code= '" + data3 + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT ORDER BY PERJACT_ASTY_CODE";
        List<SubActividadesDocenteVo> subact = subactivRep.getSubAct(dato);
        System.out.println("datos capturados :" + data1 + "," + data2 + "," + data3);
        return new ResponseEntity(subact, HttpStatus.OK);
    }

    //Lista subactividades de acuerdo a cod actividad
    @RequestMapping(value = "/sub/{codAct}", method = RequestMethod.GET)
    public ResponseEntity subactCodeActividad(@PathVariable String codAct) throws SQLException {
        String dato = " where stvasty_Code LIKE '" + codAct + "%'";
        List<subactividadesVo> subact = subactivRep.getSubActByCodeAct(dato);
        System.out.println("datos capturados :" + codAct);
        return new ResponseEntity(subact, HttpStatus.OK);
    }

     @RequestMapping(value = "/getSub/{pidm}/{periodo}/{actividad}", method = RequestMethod.GET)
    public ResponseEntity<SubActividadesDocenteVo> searchByPidm(@PathVariable long pidm, @PathVariable String periodo, @PathVariable String actividad) throws SQLException {
        String suba = " where P.perjact_activity_date >= ST.stvterm_start_date AND P.perjact_activity_date <= ST.stvterm_end_date AND P.PERJACT_PIDM = '" + pidm + "' AND ST.STVTERM_CODE = '" + periodo + "' AND P.perjact_jact_code= '" + actividad + "' GROUP BY P.PERJACT_ASTY_CODE, S.STVASTY_DESC, S.STVASTY_ACTIVITY_DATE,P.PERJACT_STD_HRS_PER_PAY, P.PERJACT_PERCENT ORDER BY PERJACT_ASTY_CODE";
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
}
