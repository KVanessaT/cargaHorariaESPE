import { Component, OnInit, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { RestService } from 'app/service/rest.service';


@Component({
  selector: 'app-add-subactividades',
  templateUrl: './add-subactividades.component.html',
  styleUrls: ['./add-subactividades.component.scss']
})
export class AddSubactividadesComponent implements OnInit {
  subactividades: any;
  // period: any;
  // pidm: any;
  code_act: any
  constructor(public dialogRef: MatDialogRef<AddSubactividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService, private spinner: NgxSpinnerService) { }

  ngOnInit() {
    this.getSubactividades();
  }

  // guardarSubact(sub: string) {
  //   this.subactividad = sub;
  //   //console.log("la subactividad es :" + this.subactividad);
  // }
  // getSubActiv(): void {
  //   this.rest.getData('subA/' + this.pidm + '/' + this.period + '/' + this.subactividad).subscribe(
  //     data => {
  //       this.subactividad = data;
  //       console.log('subactividades de la actividad:', data);
  //     }
  //   )

  // }

  subact(codAct: string) {
    this.code_act = codAct;
  }

  getSubactividades(): void {
    this.rest.getData('sub/' + this.data.code_act).subscribe(
      data => {
        this.subactividades = data;
        console.log(data);
      }
    )
  }

}
