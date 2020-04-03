/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package espe.edu.ec.carga_horaria.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Vanessa
 */
@Entity
@Table(name = "BZSVEMPLEADO", schema ="BANINST1")

public class Empleado implements Serializable {

    private static final long serialVersionUID = 1L;
   //@SequenceGenerator(name = "UZFTPERSONP_SEQ", sequenceName = "UZFTPERSONP_SEQ", allocationSize = 1)
   @Id
   //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UZFTPERSONP_SEQ")

    @Basic(optional = false)
    @Column(name = "PEBEMPL_PIDM")
    private Long pebemplPidm;
    @Column(name = "ID_BANNER")
    private String idBanner;
    @Column(name = "NUMERO_DOCUMENTO")
    private String numeroDocumento;
    @Column(name = "APELLIDO")
    private String apellido;
    @Column(name = "NOMBRES")
    private String nombres;
    @Column(name = "PREFIJO")
    private String prefijo;
    @Column(name = "FECHA_NACIMIENTO")
    private String fechaNacimiento;
    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
    @Column(name = "SEXO")
    private String sexo;
    @Column(name = "TIPO_SANGRE")
    private String tipoSangre;
    @Column(name = "ESTADO_CIVIL_SITH")
    private String estadoCivilSith;
    @Column(name = "DISCAPACITADO")
    private String discapacitado;
    @Column(name = "PORC_DISC")
    private String porcDisc;
    @Column(name = "NUM_CARNET")
    private String numCarnet;
    @Column(name = "TIPO_DISC")
    private String tipoDisc;
    @Column(name = "IDENTIFICACION_ETNIA")
    private String identificacionEtnia;
    @Column(name = "DIRECCION_CALLE_PRINCIPAL")
    private String direccionCallePrincipal;
    @Column(name = "DIRECCION_CALLE_SECUNDARIA")
    private String direccionCalleSecundaria;
    @Column(name = "DIRECCION_REFERENCIA")
    private String direccionReferencia;
    @Column(name = "TELEFONO_DOMICILIO")
    private String telefonoDomicilio;
    @Column(name = "TELEFONO_CELULAR")
    private String telefonoCelular;
    @Column(name = "TELEFONO_TRABAJO")
    private String telefonoTrabajo;
    @Column(name = "TELEFONO_EXTENSION")
    private String telefonoExtension;
    @Column(name = "CORREO_INSTITUCIONAL")
    private String correoInstitucional;
    @Column(name = "CORREO_PERSONAL")
    private String correoPersonal;
    @Column(name = "PROVINCIA")
    private String provincia;
    @Column(name = "CANTON")
    private String canton;
    @Column(name = "PARROQUIA")
    private String parroquia;
    @Column(name = "CONTACTO_APELLIDO")
    private String contactoApellido;
    @Column(name = "CONTACTO_NOMBRE")
    private String contactoNombre;
    @Column(name = "CONTACTO_TELEFONO")
    private String contactoTelefono;
    @Column(name = "NUMERO_NOTARIA_BIENES")
    private String numeroNotariaBienes;
    @Column(name = "LUGAR_NOTARIA_BIENES")
    private String lugarNotariaBienes;
    @Column(name = "FECHA_NOTARIA")
    private String fechaNotaria;
    @Column(name = "BANCO")
    private String banco;
    @Column(name = "NUM_CTA")
    private String numCta;
    @Column(name = "TIPO_CTA")
    private String tipoCta;
    @Column(name = "DESC_TIT")
    private String descTit;
    @Column(name = "FECHA_TRABAJO")
    private String fechaTrabajo;
    @Column(name = "TIPO_CONTRATO")
    private String tipoContrato;
    @Column(name = "TITULARIDAD")
    private String titularidad;
    @Column(name = "DEDICACION")
    private String dedicacion;
    @Column(name = "TIPO_EMPLEADO")
    private String tipoEmpleado;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "SECCION")
    private String seccion;
    @Column(name = "UBICACION")
    private String ubicacion;
    @Column(name = "DEPARTAMENTO")
    private String departamento;
    @Column(name = "CAMPUS")
    private String campus;
    @Column(name = "DESC_PUESTO")
    private String descPuesto;
    @Column(name = "SW_DOCENTE")
    private String swDocente;
    @Column(name = "CODE_DEDICACION")
    private String codeDedicacion;
    @Column(name = "CODE_PUESTO")
    private String codePuesto;

    public Long getPebemplPidm() {
        return pebemplPidm;
    }

    public void setPebemplPidm(Long pebemplPidm) {
        this.pebemplPidm = pebemplPidm;
    }

    public String getIdBanner() {
        return idBanner;
    }

    public void setIdBanner(String idBanner) {
        this.idBanner = idBanner;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getEstadoCivilSith() {
        return estadoCivilSith;
    }

    public void setEstadoCivilSith(String estadoCivilSith) {
        this.estadoCivilSith = estadoCivilSith;
    }

    public String getDiscapacitado() {
        return discapacitado;
    }

    public void setDiscapacitado(String discapacitado) {
        this.discapacitado = discapacitado;
    }

    public String getPorcDisc() {
        return porcDisc;
    }

    public void setPorcDisc(String porcDisc) {
        this.porcDisc = porcDisc;
    }

    public String getNumCarnet() {
        return numCarnet;
    }

    public void setNumCarnet(String numCarnet) {
        this.numCarnet = numCarnet;
    }

    public String getTipoDisc() {
        return tipoDisc;
    }

    public void setTipoDisc(String tipoDisc) {
        this.tipoDisc = tipoDisc;
    }

    public String getIdentificacionEtnia() {
        return identificacionEtnia;
    }

    public void setIdentificacionEtnia(String identificacionEtnia) {
        this.identificacionEtnia = identificacionEtnia;
    }

    public String getDireccionCallePrincipal() {
        return direccionCallePrincipal;
    }

    public void setDireccionCallePrincipal(String direccionCallePrincipal) {
        this.direccionCallePrincipal = direccionCallePrincipal;
    }

    public String getDireccionCalleSecundaria() {
        return direccionCalleSecundaria;
    }

    public void setDireccionCalleSecundaria(String direccionCalleSecundaria) {
        this.direccionCalleSecundaria = direccionCalleSecundaria;
    }

    public String getDireccionReferencia() {
        return direccionReferencia;
    }

    public void setDireccionReferencia(String direccionReferencia) {
        this.direccionReferencia = direccionReferencia;
    }

    public String getTelefonoDomicilio() {
        return telefonoDomicilio;
    }

    public void setTelefonoDomicilio(String telefonoDomicilio) {
        this.telefonoDomicilio = telefonoDomicilio;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public String getTelefonoExtension() {
        return telefonoExtension;
    }

    public void setTelefonoExtension(String telefonoExtension) {
        this.telefonoExtension = telefonoExtension;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getParroquia() {
        return parroquia;
    }

    public void setParroquia(String parroquia) {
        this.parroquia = parroquia;
    }

    public String getContactoApellido() {
        return contactoApellido;
    }

    public void setContactoApellido(String contactoApellido) {
        this.contactoApellido = contactoApellido;
    }

    public String getContactoNombre() {
        return contactoNombre;
    }

    public void setContactoNombre(String contactoNombre) {
        this.contactoNombre = contactoNombre;
    }

    public String getContactoTelefono() {
        return contactoTelefono;
    }

    public void setContactoTelefono(String contactoTelefono) {
        this.contactoTelefono = contactoTelefono;
    }

    public String getNumeroNotariaBienes() {
        return numeroNotariaBienes;
    }

    public void setNumeroNotariaBienes(String numeroNotariaBienes) {
        this.numeroNotariaBienes = numeroNotariaBienes;
    }

    public String getLugarNotariaBienes() {
        return lugarNotariaBienes;
    }

    public void setLugarNotariaBienes(String lugarNotariaBienes) {
        this.lugarNotariaBienes = lugarNotariaBienes;
    }

    public String getFechaNotaria() {
        return fechaNotaria;
    }

    public void setFechaNotaria(String fechaNotaria) {
        this.fechaNotaria = fechaNotaria;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNumCta() {
        return numCta;
    }

    public void setNumCta(String numCta) {
        this.numCta = numCta;
    }

    public String getTipoCta() {
        return tipoCta;
    }

    public void setTipoCta(String tipoCta) {
        this.tipoCta = tipoCta;
    }

    public String getDescTit() {
        return descTit;
    }

    public void setDescTit(String descTit) {
        this.descTit = descTit;
    }

    public String getFechaTrabajo() {
        return fechaTrabajo;
    }

    public void setFechaTrabajo(String fechaTrabajo) {
        this.fechaTrabajo = fechaTrabajo;
    }

    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public String getTitularidad() {
        return titularidad;
    }

    public void setTitularidad(String titularidad) {
        this.titularidad = titularidad;
    }

    public String getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(String dedicacion) {
        this.dedicacion = dedicacion;
    }

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDescPuesto() {
        return descPuesto;
    }

    public void setDescPuesto(String descPuesto) {
        this.descPuesto = descPuesto;
    }

    public String getSwDocente() {
        return swDocente;
    }

    public void setSwDocente(String swDocente) {
        this.swDocente = swDocente;
    }

    public String getCodeDedicacion() {
        return codeDedicacion;
    }

    public void setCodeDedicacion(String codeDedicacion) {
        this.codeDedicacion = codeDedicacion;
    }

    public String getCodePuesto() {
        return codePuesto;
    }

    public void setCodePuesto(String codePuesto) {
        this.codePuesto = codePuesto;
    }
@Override
    public int hashCode()
    {
        int hash = 0;
        hash += (pebemplPidm != null ? pebemplPidm.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if(!(object instanceof Empleado))
        {
            return false;
        }
        Empleado other = (Empleado)object;
        if((this.pebemplPidm == null && other.pebemplPidm != null) || (this.pebemplPidm != null && !this.pebemplPidm.equals(other.pebemplPidm)))
        {
            return false;
        }
        return true;
    }

    
}
