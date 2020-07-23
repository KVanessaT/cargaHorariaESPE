/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.dao;

import espe.edu.ec.carga_horaria.model.Subactividades;
import espe.edu.ec.carga_horaria.model.SubactividadesPK;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Vanessa
 */
public interface SubactividadesRepository extends CrudRepository<Subactividades, SubactividadesPK> {

    @Query(value = "select * from payroll.perjact", nativeQuery = true)
    List<Subactividades> findAllPeriodos();

    Subactividades findByid(SubactividadesPK id);
    // Subactividades findByPerjactSAPKPidmAndPerjactPosnAndPerjactSuffAndEfectiveDateAndCodProvinciaAndCodActividadAndcodSubact(int pidm);

  // Subactividades findByEfectiveDate(String edate);
    
        Long deleteByid(SubactividadesPK id);

}
