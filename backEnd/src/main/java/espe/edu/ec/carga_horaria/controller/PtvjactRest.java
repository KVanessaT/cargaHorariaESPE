/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.controller;

import espe.edu.ec.carga_horaria.model.Ptvjact;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import espe.edu.ec.carga_horaria.dao.PtvjactRepository;

/**
 *
 * @author Vanessa
 */
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/sch")
public class PtvjactRest {

    @Autowired
    private PtvjactRepository actividadesRep;

    @RequestMapping(value = "/allactividades", method = RequestMethod.GET)
    public ResponseEntity<Ptvjact> listaActividades() {
        List<Ptvjact> actividadess = actividadesRep.findAllActividades();
        if (actividadess.isEmpty()) {
            return new ResponseEntity("No encontrado", HttpStatus.OK);
        } else {
            return new ResponseEntity(actividadess, HttpStatus.OK);
        }
    }    
 
}




