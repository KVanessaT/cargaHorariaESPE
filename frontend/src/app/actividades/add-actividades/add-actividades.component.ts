import { Component, OnInit, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-add-actividades',
  templateUrl: './add-actividades.component.html',
  styleUrls: ['./add-actividades.component.scss']
})
export class AddActividadesComponent implements OnInit {
  div1: boolean;
  div2: boolean;
  activar: boolean;
  error: boolean;
  guardar: any;
  activ: any;
  codeAct: any;
  descripAct: string;
  fechaInicio: string;
  period: any;
  hora: number;
  unidadG: string;
  responsable: any;
  maxHora: boolean
  setHoras: any
  modelo1: {};
  seccion: any;
  titleSeccion: any
  departa: any
  departamentos: any;
  departamentoUnidad: boolean;
  seccionUnidad: boolean;
  cedula: string;
  persResponsable: string
  ResponUnidad: boolean;
  mensaje: boolean;
  pipe = new DatePipe('en-US');
  myFormattedDate: any
  verifAct: any;
  respExiste: any
  constructor(public dialogRef: MatDialogRef<AddActividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService,
    private spinner: NgxSpinnerService, private toastr: ToastrService) {
    this.div1 = false;
    this.div2 = false;
    this.activar = false;
    this.error = false;
    this.maxHora = false;
    this.departamentoUnidad = false;
    this.seccionUnidad = false;
    this.ResponUnidad = false;
    this.mensaje = false;
    this.myFormattedDate = this.pipe.transform(Date.now(), 'dd-MM-yyyy');

  }

  ngOnInit() {
    console.log(this.data);
    this.cargarActividades();
    //this.getSeccion();
    this.getDepart();
    //this.getResponsable();
  }

  //Método para listar todas las actividades que existen
  cargarActividades() {
    this.rest.getData('allactividades').subscribe(
      data => {
        this.activ = data;
        if (data == true) {
        } else {
        }
      }
    )
  }

  //Captura el código de una actividad elegida
  getActividad(codeActi: number, descripcionAct: string) {
    this.spinner.show();

    this.codeAct = codeActi;
    this.descripAct = descripcionAct;
    this.fechaInicio = this.data.FechaIni;
    this.period = this.data.periodo;
    this.comprobarAct();
    this.spinner.hide();

  }

  //cerrar mat-dialog
  onNoClick(): void {
    this.dialogRef.close();

  }

  //Agrega una actividad
  guardarM() {

    this.guardar = {
      "id": {
        "actividad": this.codeAct,
        "fechaInicio": this.fechaInicio,
        "pidm": this.data.pidm,
      },
      "periodo": this.period,
      "horasA": 0,
      "unidadGestion": this.titleSeccion,
      "responsable": this.persResponsable,
      "userCrear": 9999,
      "fechaCrear": Date.now(),
      "userEditar": 0,
      "fechaEditar": ''

    }
    console.log(this.guardar)

    this.rest.addData(this.guardar, "cabs1").subscribe(
      data => {
          this.toastr.success(data.message, 'La actividad');
          this.cargarActividades();
          //this.actualizaHorasCabecera();
          this.onNoClick();
        
      },
      error => {
      }
    )
  }

  //Comprueba si una actividad que se va a ingresar ya existe
  comprobarAct() {
    this.spinner.show();
    this.rest.getData('cb/' + this.codeAct + '/' + this.fechaInicio + '/' + this.data.pidm).subscribe(
      data => {
        this.spinner.hide();
        this.verifAct = data;
        console.log(this.verifAct);
        //this.addUnidad();
        //if (data == true) {
         //this.error = false
         // if (this.codeAct === "02" || this.codeAct === "03" || this.codeAct === "04") {
            //this.activar = false;
           // this.div1 = false;
            //this.div2 = true;
            //this.responsable = "";
            //this.unidadG = "";
            // this.activar = true;

          //} else {
            //this.div1 = false;
            //this.div2 = false;
            //this.responsable = "R";
            //this.unidadG = "U"
            //this.activar = true;
           //}
         //} else {
          //this.div1 = false;
          //this.div2 = false;
          //this.activar = false;
          //this.error = true;
        //}
      }
    )
  }

  actividadesHoras() {
    this.rest.getData('sumaHoraActividades' + '/' + this.data.pidm + '/' + this.data.periodo + '/' + this.hora + '/' + this.data.code).subscribe(
      data => {
        if (data == true) {
          this.guardarM();
        } else {
          this.maxHora = true;
        }
      }
    )
  }




  actualizaHorasCabecera() {
    this.rest.getData('cabsUPDATE/' + this.data.pidm + '/' + this.period + '/' + this.codeAct).subscribe(
      data => {
        this.setHoras = data;
        console.log(data)
        this.modelo1 = {
          "id": {
            "pzptcabperjactActividad": this.setHoras.pzptcabperjactActividad,
            "pzptcabperjactFechaInicio": this.setHoras.pzptcabperjactFechaInicio,
            "pzptcabperjactPidm": this.setHoras.pzptcabperjactPidm,
          },
          "pzptcabperjactPeriodo": this.setHoras.pzptcabperjactPeriodo,
          "pzptcabperjactHoras": this.setHoras.pzptcabperjactHoras,
          "pzptcabperjactUnidadGestion": this.setHoras.pzptcabperjactUnidadGestion,
          "pzptcabperjactResponsable": this.setHoras.pzptcabperjactResponsable,
          "pzptcabperjactUserCrear": this.setHoras.pzptcabperjactUserCrear,
          "pzptcabperjactFechaCrear": this.setHoras.pzptcabperjactFechaCrear

        }
        this.editCabecera();
      }
    )
  }

  editCabecera() {
    console.log(this.modelo1)
    this.rest.updateData('editarSubact', this.modelo1).subscribe(
      data => {
        this.onNoClick();

      }
    )
  }

  // getSeccion() {
  //   this.rest.getData('section').subscribe(
  //     data => {
  //       this.seccion = data;
  //       console.log("seccion: ", data);
  //     })
  // }


  // guardarSeccion(valor: string) {
  //   this.titleSeccion = valor;
  // }

  //Lista de departamentos
  getDepart() {
    this.rest.getData('alldepts').subscribe(
      data => {
        this.departamentos = data
        console.log("deps:", data)
      }
    );
  }

  // unidad de gestion dependiendo tipo empleado, 
  addUnidad() {
    if (this.data.tipoE == "DO" || this.data.tipoE == "MI") {
      this.departamentoUnidad = true;
      //this.departamentos =false
    } else if (this.data.tipoE === "SP") {
      this.seccionUnidad = true;
    }
  }

  getResponsable() {
    this.spinner.show();
    this.rest.getData('responsable/' + this.cedula).subscribe(
      data => {
        this.responsable = data;
        this.respExiste = data.message;
        if (this.responsable.message != null) {//no existe el docente
          this.spinner.hide();
          this.mensaje = true;
          this.ResponUnidad = false;
          this.activar = false;
        } else {
          // this.responsable = data;
          this.persResponsable = this.responsable.pebemplPidm;
          this.spinner.hide();
          if (this.responsable.seccion == null) {
            this.titleSeccion = this.responsable.departamento
          }
          else {
            this.titleSeccion = this.responsable.seccion
          }
          this.ResponUnidad = true;
          this.activar = true;
          this.mensaje = false;
        }
      }
    )
  }

}


