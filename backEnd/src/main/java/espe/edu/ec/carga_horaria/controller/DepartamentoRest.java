/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.dao.DepartamentoRepository;
import espe.edu.ec.carga_horaria.model.Departamento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class DepartamentoRest {

    @Autowired
    private DepartamentoRepository departamentoRep;

    @RequestMapping(value = "/alldepts", method = RequestMethod.GET)
    public ResponseEntity<Departamento> listadepartamentos() {
        List<Departamento> departamentos = departamentoRep.findalldepartamentos();
        if (departamentos.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(departamentos, HttpStatus.OK);
        }
    }
}
