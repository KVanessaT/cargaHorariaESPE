/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.dao;

import espe.edu.ec.carga_horaria.model.Actividades;
import espe.edu.ec.carga_horaria.model.ActividadesPK;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Vanessa
 */
@Repository
public interface ActividadesRepository extends CrudRepository<Actividades, ActividadesPK> {

    @Query(value = "select * from payroll.pzptcabperjact", nativeQuery = true)
    List<Actividades> findAllActividades();
    // ActividadesPK findUserBypzptcabperjactActividad(long id);

    Actividades findByid(ActividadesPK id);
    
        List<Actividades> findAllById(ActividadesPK id);

        List<Actividades> findByidAndPeriodo(ActividadesPK id, String periodo);
//CabeceraA findBy
    // deleteby;
//    Actividades findByPzptcabperjactActividadAndPzptcabperjactPidmandPzptcabperjacPeriodo(String actividad,Long pidm, String periodo); //CabeceraA delete(ActividadesPK id);
//CabeceraA deleteByPzptcabperjactActividad(String actividad);
//CabeceraA deleteByPzptcabperjactPidm(Long pidm);
    //Long deleteBypzptcabperjactPeriodo(String periodo);

    //Long deleteByPzptcabperjactActividad(@Param("pzptcabperjactActividad") String actividad);
    //Long deleteByPzptcabperjactPidm(@Param("pzptcabperjactPidm") String pidm);
    Long deleteByid(ActividadesPK id);

    //@Modifying
    //@Query("delete from payroll.pzptcabperjact p where p.pzptcabperjactPidm=:pidm and p.pzptcabperjactPeriodo=:periodo and p.pzptcabperjactActividad=:actividad")
    //Long deleteFruits(@Param("pzptcabperjactPidm") String pidm, @Param("pzptcabperjactPeriodo") String periodo, @Param("pzptcabperjactActividad") String actividad);
}
