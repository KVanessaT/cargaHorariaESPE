import { Component, OnInit, Inject} from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { ToastrService } from 'ngx-toastr';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-edit-subactividades',
  templateUrl: './edit-subactividades.component.html',
  styleUrls: ['./edit-subactividades.component.scss']
})


export class EditSubactividadesComponent implements OnInit {
  datosSubactividades: any
  info: any;
  pipe = new DatePipe('en-US');
  now = Date.now();
  f1:any;
  f2:any;
  mensaje: any;
  maxHora: boolean;
  activar: boolean;
  objectoeditar: any;
  code: any
  dedicacion: any
  horasMax: number;
  valor: any;
  constructor(public dialogRef: MatDialogRef<EditSubactividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService,
    private spinner: NgxSpinnerService, private toastr: ToastrService) {
      this.maxHora = false;
      this.activar = false;
  }

  ngOnInit() {
    this.codeDed();
  }

  codeDed(){
    if (this.data.code == 'EP') {
      this.dedicacion = 'Tiempo parcial'
      this.horasMax = 19
      if (this.data.code == 'EC') {
        this.dedicacion = 'Tiempo completo'
        this.horasMax = 40
      }
      if (this.data.code == 'EX') {
        this.dedicacion = 'Medio tiempo'
        this.horasMax = 20
      }
    }
  }
  
  // sumaHorasSub() {
  //   this.rest.getData('horasEdit/' + this.data.objeto.id.codActividad + '/' + this.data.objeto.id.efectiveDate + '/' + this.data.objeto.id.pidm + '/' + this.data.descripcion.perjact_std_hrs_per_pay + '/'+this.data.objeto.horas).subscribe(
  //     data => {
  //       if (data == true) {
  //        // this.activar = true
         
  //         this.updObjeto();
  //       } else {
  //         this.maxHora = true;
          
  //       }
  //     }
  //   )
  // }
  onSearchChange(searchValue: number) {
    this.rest.getData('hEdit/' + this.data.codP + '/' + this.data.objeto.id.pidm + '/' + this.data.descripcion.perjact_std_hrs_per_pay + '/'+this.data.objeto.horas).subscribe(
      data => {
        this.valor = data
        if (data == true) {
         // this.activar = true
          //this.updObjeto();
        } else {
         // this.maxHora = true;
          
        }
      }
    )
  }

  //edita una subactividad
  updObjeto() {
    this.rest.updateData('editarSAc' ,this.data.objeto).subscribe(
      data => {
        this.toastr.success(data.message, 'La subactividad');
        this.updCab();
      }
    )
  }

  //actualiza horas en las actividades
  updCab() {
    this.spinner.show();
    this.rest.updateData('cabsUPDATE/' + this.data.objeto.id.codActividad + '/' + this.data.objeto.id.efectiveDate + '/' + this.data.objeto.id.pidm,this.objectoeditar).subscribe(
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
