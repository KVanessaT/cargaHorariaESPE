/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.dao;

import espe.edu.ec.carga_horaria.model.Cabecera;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Vanessa
 */
public interface CabeceraRepository extends CrudRepository<Cabecera, Long> {

    @Query(value = "select * from payroll.pzptcabperjact", nativeQuery = true)
    List<Cabecera> findAllCabecera();

    List<Cabecera> findBypzptcabperjactPidm(Long pidm);

    List<Cabecera> findBypzptcabperjactPeriodo(String periodo);

    List<Cabecera> findBypzptcabperjactActividad(String actividad);

    List<Cabecera> findByPzptcabperjactPidmAndPzptcabperjactPeriodo(Long pidm, String periodo);

    //Cabecera findBypzptcabperjactPidmAnAndpzptcabperjactPeriodoAndpzptcabperjactActividad(Long pidm, String periodo, String actividad);
  // deleteBypzptcabperjactPidm(@Param("pzptcabperjactPidm")Long pidm);

    Long deleteByPzptcabperjactPeriodo(@Param("pzptcabperjactPeriodo") String periodo);

    Long deleteByPzptcabperjactActividad(@Param("pzptcabperjactActividad") String actividad);

    //Cabecera deleteByPzptcabperjactPidmAndPzptcabperjactPeriodoAndPzptcabperjactActividad(Long pidm, String periodo, String actividad);


//@Modifying
//@Query("delete from payroll.pzptcabperjact p where p.pzptcabperjact_pidm = : pidm and p.pzptcabperjact.periodo =: periodo and p.pzptcabperjact_actividad=: actividad ")
//int deleteActs(@Param("pzptcabperjactPidm")  Long pidm, @Param("pzptcabperjact.periodo") String periodo, @Param("pzptcabperjactActividad") String actividad);

//void deleteBypzptcabperjactPidmAndpzptcabperjactPeriodoAndpzptcabperjactActividad1(Long pidm, String periodo, String actividad);
//void deleteBypzptcabperjactPidmAndpzptcabperjactPeriodoAndpzptcabperjactActividad(
//@Param("pzptcabperjactPidm") Long pidm,
//@Param("pzptcabperjactPeriodo") String periodo,
//@Param("pzptcabperjactActividad") String actividad);
//
}
