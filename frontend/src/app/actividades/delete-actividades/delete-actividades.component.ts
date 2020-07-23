import { Component, OnInit, Inject, ViewContainerRef } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-delete-actividades',
  templateUrl: './delete-actividades.component.html',
  styleUrls: ['./delete-actividades.component.scss']
})
export class DeleteActividadesComponent implements OnInit {
  error: boolean;
  activar: boolean;
  pipe = new DatePipe('en-US');
  fechaInicio: any
  horasA: any;
  info: any;
  menssaje: any
  options: any = {
    toastLife: 3000,
    dismiss: "auto",
    showCloseButton: true
  };
  periodos: any;
  codActividad: any
  constructor(public dialogRef: MatDialogRef<DeleteActividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private toastr: ToastrService,
    private rest: RestService) {
    this.error = false;
    this.activar = false;

  }

  ngOnInit() {
    console.log(this.data)
    this.fechaInicio = this.data.FechaIni;
    //this.codActividad = this.data.actividad;
    console.log(this.data.FechaIni);
    this.compSubActividad();
    this.getDatosPeriodo();
  }

  eliminarActividad(): void {
    this.fechaInicio = this.pipe.transform(this.data.FechaIni, 'yyyy-MM-dd');
    console.log(this.fechaInicio)
    this.info = this.data.actividad + '/' + this.fechaInicio + '/' + this.data.pidm;
    console.log(this.info);
    this.rest.deleteData("dela/" + this.info).subscribe(data => {

      console.log(data);
      // this.menssaje = data;
      this.toastr.success(data.message, 'La actividad');
      this.dialogRef.close(this.data);
      console.log(this.data);
      console.log("eliminado");
    });
  }

  getDatosPeriodo() {
    this.rest.getData('codePeriodo/' + this.data.periodo).subscribe(
      data => {
        this.periodos = data;
        console.log(this.periodos.stvtermStartDate);
      }
    )
  }

  compSubActividad() {
    this.rest.getData('getSub/' + this.data.pidm + '/' + this.data.periodo + '/' + this.data.actividad).subscribe(data => {
      if (data == true) {
        this.activar = true;
        //this.eliminarActividad();
      } else {
        this.error = true
      }


      // this.dialogRef.close(this.data);
      console.log(this.data);
      console.log(this.data.pidm, this.data.periodo, this.data.actividad, this.data.descr);
    });
  }

  cerrarModal() {
    this.dialogRef.close();
  }

}
