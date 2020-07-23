import { Component, OnInit, Inject, ɵConsole } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { ToastrService } from 'ngx-toastr';
@Component({
  selector: 'app-delete-subactividades',
  templateUrl: './delete-subactividades.component.html',
  styleUrls: ['./delete-subactividades.component.scss']
})
export class DeleteSubactividadesComponent implements OnInit {

  info: any
  cabEditar: any;
  editarObj: {};
  mensaje: any
  objectoeditar: any;

  constructor(public dialogRef: MatDialogRef<DeleteSubactividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService,
    private spinner: NgxSpinnerService, private toastr: ToastrService) { }

  ngOnInit() {
  }

  //eliminar subactividad
  eliminarSub() {
    this.info = + this.data.objeto.id.pidm + '/' + this.data.objeto.id.perjactPosn + '/' + this.data.objeto.id.perjactSuff + '/' + this.data.objeto.id.efectiveDate + '/' + this.data.objeto.id.codProvincia + '/' + this.data.objeto.id.codActividad + '/' + this.data.objeto.id.codSubact;
    this.rest.deleteData("delSub/" + this.info).subscribe(data => {
      this.toastr.success(data.message, 'La subactividad');
      this.updCab();

    });
  }

  //actualiza horas en las actividades
  updCab() {
    this.spinner.show();
    this.rest.updateData('cabsUPDATE/' + this.data.objeto.id.codActividad + '/' + this.data.objeto.id.efectiveDate + '/' + this.data.objeto.id.pidm, this.objectoeditar).subscribe(
      data => {
        this.spinner.hide();
        this.objectoeditar = data;
        this.mensaje = data;
        this.dialogRef.close(data);
      },
      error => {
      }
    )
  }
}
