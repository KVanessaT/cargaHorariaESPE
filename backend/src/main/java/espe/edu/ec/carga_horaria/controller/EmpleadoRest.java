/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.EmpleadoRepository;
import espe.edu.ec.carga_horaria.model.Empleado;
import espe.edu.ec.carga_horaria.vo.actividadesVo;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.empleadoVo;
import espe.edu.ec.carga_horaria.vo.seccionVo;
import espe.edu.ec.util.mensajes;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
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
public class EmpleadoRest {

    @Autowired
    private EmpleadoRepository empleadosRep;

    @Autowired
    private apiVo empleadoRep;

    @Autowired
    private apiVo activRep;

    private final mensajes msg = new mensajes();

    @RequestMapping(value = "/allempleados", method = RequestMethod.GET)
    public ResponseEntity<Empleado> listaempleados() {
        List<Empleado> empleados = empleadosRep.findallEmpleados();
        if (empleados.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(empleados, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/section", method = RequestMethod.GET)
    public ResponseEntity empleadosBySeccion() throws SQLException {
        String dat = " ORDER BY FTVORGN_TITLE ASC";

        List<seccionVo> section = empleadoRep.getSection(dat);
        if (section.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(section, HttpStatus.OK);
        }
    }

//    lista docentes seleccionando campus y depart
    @RequestMapping(value = "/idm/{data1}/{data2}", method = RequestMethod.GET)
    public ResponseEntity empleadom(@PathVariable String data1, @PathVariable String data2) throws SQLException {
        String dat = " where departamento = '" + data1 + "' AND sw_Docente = 'A' AND status = 'A' AND campus = '" + data2 + "' ORDER BY APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

    //    lista docentes seleccionando campus y depart
    @RequestMapping(value = "/cedula/{cedula}", method = RequestMethod.GET)
    public ResponseEntity empleaCedula(@PathVariable String cedula) throws SQLException {
        String dat = " where numero_documento = '" + cedula + "' ORDER BY APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

    //lista docentes seleccionando campus, depart, periodo
    @RequestMapping(value = "/idmPer/{data1}/{data2}/{periodo}", method = RequestMethod.GET)
    public ResponseEntity empleadom(@PathVariable String data1, @PathVariable String data2, @PathVariable String periodo) throws SQLException {
        String dat = " where departamento = '" + data1 + "' AND sw_Docente = 'A' AND status = 'A' AND campus = '" + data2 + "' ORDER BY APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);

        int pidms;
        for (int i = 0; i < employee2.size(); i++) {
            pidms = employee2.get(i).getPebempl_pidm();
            String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidms + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm, pzptcabperjact_user_create, pzptcabperjact_date_create, pzptcabperjact_user_edit, pzptcabperjact_date_edit ORDER BY pzptcabperjact_Actividad";
            List<actividadesVo> actv = activRep.getActDoc(dato);
            if (actv.isEmpty()) {
                employee2.get(i).setEstado("false");
            } else {
                employee2.get(i).setEstado("true");
            }
        }

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

    //lista docentes seleccionando campus y depart
    @RequestMapping(value = "/idmDCP/{data1}/{data2}/{periodo}", method = RequestMethod.GET)
    public ResponseEntity empleaPer(@PathVariable String data1, @PathVariable String data2, @PathVariable String periodo) throws SQLException {
        String dat = " where departamento = '" + data1 + "' AND sw_Docente = 'A' AND status = 'T' AND campus = '" + data2 + "' ORDER BY APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);

        int pidms;
        for (int i = 0; i < employee2.size(); i++) {
            pidms = employee2.get(i).getPebempl_pidm();
            String dato = " where pzptcabperjact_periodo = '" + periodo + "' AND pzptcabperjact_Pidm = '" + pidms + "' GROUP BY pzptcabperjact_actividad,ptvjact_desc, pzptcabperjact_periodo,pzptcabperjact_horas,pzptcabperjact_unidad_gestion, pzptcabperjact_responsable, pzptcabperjact_pidm ORDER BY pzptcabperjact_Actividad";
            List<actividadesVo> actv = activRep.getActDoc(dato);
            if (actv.isEmpty()) {
                employee2.get(i).setEstado("false");
            } else {
                employee2.get(i).setEstado("true");
            }
        }

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

    //lista docentes seleccionando campus y depart
    @RequestMapping(value = "/idmInac/{departamento}/{campus}", method = RequestMethod.GET)
    public ResponseEntity empleadoInactivo(@PathVariable String departamento, @PathVariable String campus) throws SQLException {
        String dat = " where departamento = '" + departamento + "' AND sw_Docente = 'A' AND status = 'T' AND campus = '" + campus + "' ORDER BY APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/docDCP/{departamento}/{campus}/{periodo}", method = RequestMethod.GET)
    public ResponseEntity docentesDCP(@PathVariable String departamento, @PathVariable String campus, @PathVariable String periodo) throws SQLException {
        String dat = " where BZ.departamento = '" + departamento + "' AND BZ.sw_Docente = 'A' AND BZ.status = 'A' AND BZ.campus = '" + campus + "'  AND PZ.PZPTCABPERJACT_PERIODO = '" + periodo + "' ORDER BY BZ.APELLIDO";
        List<empleadoVo> employee2 = empleadoRep.getEmpCDP(dat);

        if (employee2.isEmpty()) {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        } else {
            return new ResponseEntity(employee2, HttpStatus.OK);
        }
    }

//datos de la persona 
    @RequestMapping(value = "/ban/{banner}", method = RequestMethod.GET)
    public ResponseEntity datosByIdBanner(@PathVariable String banner) throws SQLException {
        Empleado empleados = empleadosRep.findByIdBanner(banner);
        return new ResponseEntity(empleados, HttpStatus.OK);
    }

//datos de la persona con rango de director by IDbanner
    @RequestMapping(value = "/doc/{pidm}", method = RequestMethod.GET)
    public ResponseEntity datosByPidm(@PathVariable int pidm) throws SQLException {
        Empleado empleado = empleadosRep.findByPebemplPidm(pidm);
        return new ResponseEntity(empleado, HttpStatus.OK);
    }

    @RequestMapping(value = "/responsable/{cedula}", method = RequestMethod.GET)
    public ResponseEntity datosByCedula(@PathVariable String cedula) throws SQLException {
        Empleado empleado = empleadosRep.findByNumeroDocumento(cedula);
        if (empleado != null) {
            return new ResponseEntity(empleado, HttpStatus.OK);

        } else {
            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
        }
    }

//     @RequestMapping(value = "/responsable", method = RequestMethod.GET)
//    public ResponseEntity <Empleado> datosByCedu(){
//        List<Empleado> empleado = empleadosRep.findByNumeroDocumento(); 
//        if (empleado.isEmpty()) {
//            return new ResponseEntity(msg.notfound(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity(empleado, HttpStatus.OK);
//        }   
//    }
}
