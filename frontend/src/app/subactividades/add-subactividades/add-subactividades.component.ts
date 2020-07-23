import { Component, OnInit, Inject } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { DatePipe } from '@angular/common';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-add-subactividades',
  templateUrl: './add-subactividades.component.html',
  styleUrls: ['./add-subactividades.component.scss']
})
export class AddSubactividadesComponent implements OnInit {
  subactividades: any;
  guardar: any;
  code_act: any
  codS: string;
  descS: string;
  period: any;
  error: boolean;
  //activar: boolean;
  hora: number;
  horaActivar: boolean;
  periodo: any
  pipe = new DatePipe('en-US');
  now = Date.now();
  myFormattedDate = this.pipe.transform(this.now, 'yyyy-MM-dd');
  efec = this.data.FechaIni;
  efecDate = this.pipe.transform(this.efec, 'yyyy-MM-dd');
  efecTD = this.data.FechaIni;
  efecDat = this.pipe.transform(this.efecTD, 'dd-MM-yyyy');
  posicion: any;
  posn: any;
  objectoeditar: any;
  maxHora: boolean;
  mensaje: any;
  noZeroValue: boolean;
  horasMax: number;
  code: any
  dedicacion: any
  activo: string;
  dataAgregar: any;
  sumH: any;
  siHora: any;
  constructor(public dialogRef: MatDialogRef<AddSubactividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService,
    private spinner: NgxSpinnerService, private toastr: ToastrService) {
  //  this.activar = false;
    this.error = false;
    this.horaActivar = false;
    this.maxHora = false;
    this.noZeroValue = false;
  }

  ngOnInit() {
    console.log(this.data)
    this.code = this.data.code
    this.getSubactividades();
    this.getPosn();
  }

  subact(codAct: string) {
    this.code_act = codAct;
  }

  getSubactividades() {
    this.rest.getData('sub/' + this.data.code_act).subscribe(
      data => {
        this.subactividades = data;
      }
    )
  }

  getPosn() {
    this.rest.getData('posn/' + this.data.pidm).subscribe(
      data => {
        this.posicion = data[0].nbrjobsPosn;

      }
    )
  }

  catchSub(codeSub: string, descripcionSub: string) {
    this.codS = codeSub;
    this.descS = descripcionSub;
    this.period = this.data.periodo;
  }

  comprobarSub() {
    this.spinner.show();
    this.horaActivar = false;
    // this.rest.getData('su/' + this.data.pidm + '/' + this.data.periodo + '/' + this.codS).subscribe(

    this.rest.getData('sact/' + this.data.pidm + '/' + this.posicion + '/' + '00/' + this.efecDate + '/' + '17/' + this.data.code_act + '/' + this.codS).subscribe(
      data => {
        this.spinner.hide();
        this.dataAgregar = data;
        if (this.dataAgregar == true) {//el objeto NO existe
          console.log("puede agregar", data)
          this.error = false
          this.horaActivar = true;
       //   this.activar = true;
          this.noZeroValue = false;
        }
        else {
          console.log("NO PUEDE AGREGAR", data)

         // this.activar = false;
          this.error = true;

        }
      }
    )
  }
  onSearchChange(searchValue: number): void {
    //this. sumaHorasSub()
    //this.noZeroValue = false;
    //if (searchValue == 0) {
     // this.activo = undefined;
      //this.noZeroValue = true
      //this.maxHora = false
    //} else {
     // this.activo = undefined;
      this.rest.getData('horaAdd/' + this.period + '/' + this.data.pidm + '/' + this.hora).subscribe(
        data => {
          this.siHora = data
          if (this.siHora == true) { // PUEDE AGREGAR CON EL NUMERO DE HORAS INGRESADO
           // this.maxHora = false;
            //this.noZeroValue = false;
            //this.activo = "activo"
            console.log(this.hora)
            console.log(this.siHora)
          } else {
            //this.maxHora = true;
            //this.activo = undefined;
            //this.noZeroValue = false;
           // this.activar = true
           console.log("no false false")
           console.log(this.siHora)

            if (this.code == 'EP') {
              this.dedicacion = 'Tiempo parcial'
              this.horasMax = 19
              if (this.code == 'EC') {
                this.dedicacion = 'Tiempo completo'
                this.horasMax = 40

              }
              if (this.code == 'EX') {
                this.dedicacion = 'Medio tiempo'
                this.horasMax = 20
              }
            }
          }
        }
      )
    }

  //}

  

  //cerrar mat-dialog
  onNoClick(): void {
    this.dialogRef.close();
  }



  sumaHorasSub() {


    if (this.hora == 0) {
      this.noZeroValue = true;
    } else {
      this.noZeroValue = false;

      this.rest.getData('subActividad/' + this.data.pidm + '/' + this.period + '/' + this.data.code_act + '/' + this.hora).subscribe(
        data => {
          this.sumH = data;
          if (data == true) {
            // this.guardarM();

          } else {
            this.maxHora = true;
            if (this.code == 'EP') {
              this.dedicacion = 'Tiempo parcial'
              this.horasMax = 19
              if (this.code == 'EC') {
                this.dedicacion = 'Tiempo completo'
                this.horasMax = 40
              }
              if (this.code == 'EX') {
                this.dedicacion = 'Medio tiempo'
                this.horasMax = 20
              }
            }
          }
        }
      )
    }
  }

  guardarM() {
    this.guardar = {
      "id": {
        "pidm": this.data.pidm,
        "perjactPosn": this.posicion,
        "perjactSuff": "00",
        "efectiveDate": this.efec,
        "codProvincia": "17",
        "codActividad": this.data.code_act,
        "codSubact": this.codS,

      },
      "horas": this.hora,
      "porcentaje": 99,
      "perjactFte": "1",
      "fechaActividad": this.myFormattedDate
    }
    console.log(this.guardar)

    this.rest.addData(this.guardar, "addSub").subscribe(
      data => {
        this.toastr.success(data.message, 'La Subactividad');
        this.getSubactividades();
        this.updCab();
        
      },
      error => {
        console.log("error al guardar", error);
      }
    )
  }
  //objectoeditar :any;
  updCab() {
    this.spinner.show();
    this.rest.updateData('cabsUPDATE/' + this.data.code_act + '/' + this.efecDat + '/' + this.data.pidm,this.objectoeditar).subscribe(
      data => {
        this.spinner.hide();
        this.dialogRef.close(data);
      },
      error => {
      }
    )
  }


 
}


