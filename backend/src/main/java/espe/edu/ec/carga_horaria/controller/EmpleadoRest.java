/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.EmpleadoRepository;
import espe.edu.ec.carga_horaria.model.Empleado;
import espe.edu.ec.carga_horaria.vo.apiVo;
import espe.edu.ec.carga_horaria.vo.empleadoVo;
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
    

    @RequestMapping(value = "/allempleados", method = RequestMethod.GET)
    public ResponseEntity<Empleado> listaempleados() {
        List<Empleado> empleados = empleadosRep.findallEmpleados();
        if (empleados.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(empleados, HttpStatus.OK);
        }
    }

//    @RequestMapping(value = "/allempleadosCRUD", method = RequestMethod.GET)
//    public ResponseEntity<Empleado> listaempleadoCRUD() {
//        Iterable<Empleado> empleados = empleadosRep.findAll();
//        if (empleados.equals(null)) {
//            return new ResponseEntity("No encontrado", HttpStatus.OK);
//        } else {
//            return new ResponseEntity(empleados, HttpStatus.OK);
//        }
//    }
    @RequestMapping(value = "/idm/{data1}/{data2}", method = RequestMethod.GET)
    public ResponseEntity empleadom(@PathVariable String data1, @PathVariable String data2) throws SQLException {
        String dat = " where departamento = '" + data1 + "' AND sw_Docente = 'A' AND status = 'A' AND campus = '" + data2 + "'";
        List<empleadoVo> employee2 = empleadoRep.getEmpleado(dat);
        System.out.println("datos:" +data1 +","+data2);
        return new ResponseEntity(employee2, HttpStatus.OK);
    }

}
