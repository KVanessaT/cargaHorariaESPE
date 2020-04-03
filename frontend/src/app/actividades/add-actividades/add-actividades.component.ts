import { Component, OnInit, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';

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
  responsable: string;
  maxHora: boolean
  constructor(public dialogRef: MatDialogRef<AddActividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService, private spinner: NgxSpinnerService) {
    this.div1 = false;
    this.div2 = false;
    this.activar = false;
    this.error = false;
    this.maxHora = false;
  }

  ngOnInit() {
    this.cargarActividades();
    console.log(this.data);
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
  getActividad(codeActi: number, descripcionAct: string, fecIn: string) {
    this.codeAct = codeActi;
    this.descripAct = descripcionAct;
    this.fechaInicio = fecIn;
    this.period = this.data.periodo;
  }

  //cerrar mat-dialog
  onNoClick(): void {
    this.dialogRef.close();
  }

  //Agrega una actividad
  guardarM() {
    this.guardar = {
      "id": {
        "pzptcabperjactActividad": this.codeAct,
        "pzptcabperjactPeriodo": this.period,
        "pzptcabperjactPidm": this.data.pidm,
      },
      "pzptcabperjactFechaInicio": Date.now(),
      "pzptcabperjactHoras": this.hora,
      "pzptcabperjactUnidadGestion": this.unidadG,
      "pzptcabperjactResponsable": this.responsable
    }
    this.rest.addData(this.guardar, "cabs").subscribe(
      data => {
        this.onNoClick();
      },
      error => {
        console.log("error al guardar", error);
      }
    )
  }

  //Comprueba si una actividad que se va a ingresar ya existe
  comprobarAct() {
    this.spinner.show();
    this.rest.getData('cb/' + this.codeAct + '/' + this.data.periodo + '/' + this.data.pidm).subscribe(
      data => {
        this.spinner.hide();
        if (data == true) {
          this.error = false
          if (this.codeAct === "02") {
            this.div1 = false;
            this.div2 = true;
            this.responsable = "";
            this.unidadG = "";
            this.activar = true;

          } else {
            this.div1 = true;
            this.div2 = false;
            this.responsable = "R";
            this.unidadG = "U"
            this.activar = true;
          }
        } else {
          this.div1 = false;
          this.div2 = false;
          this.activar = false;
          this.error = true;
        }
      }
    )
  }

  actividadesHoras() {
    this.rest.getData('sumaHoraActividades' + '/' + this.data.pidm + '/' +this.data.periodo + '/' + this.hora + '/'  + this.data.code).subscribe(
      data => {
        console.log(this.data.pidm, this.data.periodo, this.hora, this.data.code )
        if (data == true) {
          this.guardarM();
        
        } else {
          this.maxHora = true;
        }
      }
    )
  }
}
