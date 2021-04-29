import { Component, OnInit, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-actualizar-actividades',
  templateUrl: './actualizar-actividades.component.html',
  styleUrls: ['./actualizar-actividades.component.scss']
})
export class ActualizarActividadesComponent implements OnInit {
  cedula: string
  responsible: any;//nuevo docente responsable
  responsable: any;
  mensaje: boolean;
  ResponUnidad: boolean;
  activar: boolean;
  persResponsable: any;
  titleSeccion: any;
  activarCedula: boolean;
  nuevoResp: boolean;
  guardar: any;
  respExiste: any;
  constructor(public dialogRef: MatDialogRef<ActualizarActividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService,
    private spinner: NgxSpinnerService, private toastr: ToastrService) {
    this.ResponUnidad = false;
    this.nuevoResp = false;
  }

  ngOnInit() {
    this.actividad();
  }

  actividad() {
    if (this.data.codeA == '02' || this.data.codeA == '03' || this.data.codeA == '04') {
      this.ResponUnidad = true;
      this.nuevoResp = false;
    }
    else {
      this.ResponUnidad = false;
      this.nuevoResp = false;
    }
  }

  getDocentes() {
    this.spinner.show();
    this.rest.getData('responsable/' + this.cedula).subscribe(
      data => {
        this.responsible = data;
        this.spinner.hide();
      }
    )
  }

  cambiarRespons() {
    this.activarCedula = true
    this.ResponUnidad = true;

  }

  onSearchChange(searchValue: string): void {
    this.cedula = searchValue;
    this.getResponsable(this.cedula);
  }

  getResponsable(cedula: string) {
    this.cedula = cedula;
    this.rest.getData('responsable/' + this.cedula).subscribe(
      data => {
        this.responsable = data;
        this.respExiste = data.message;
        if (this.responsable.message != null) {
        } else {
          this.persResponsable = this.responsable.pebemplPidm;
          this.spinner.hide();
          if (this.responsable.seccion == null) {
            this.titleSeccion = this.responsable.departamento
          }
          else {
            this.titleSeccion = this.responsable.seccion
          }
          this.ResponUnidad = true;
          this.nuevoResp = true
          this.activar = true;
          this.mensaje = false;
        }
      }
    )
  }

  guardarCabecera() {
    this.guardar = {
      "id": {
        "actividad": this.data.codeA,
        "fechaInicio": this.data.FechaIni,
        "pidm": this.data.pidm,
      },
      "periodo": this.data.periodo,
      "horasA": this.data.hourAct,
      "unidadGestion": this.titleSeccion,
      "responsable": this.persResponsable,
      "userCrear": this.data.userC,
      "fechaCrear": this.data.dateC,
      "userEditar": 9999,
      "fechaEditar": Date.now(),
    }
    this.rest.updateData('updateActividad', this.guardar).subscribe(
      data => {
        this.toastr.success(data.message, 'La actividad');
        this.onNoClick();
      },
      error => {
      }
    )
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
