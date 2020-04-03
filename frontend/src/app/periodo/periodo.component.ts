import { Component, OnInit, Inject } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { RestService } from 'app/service/rest.service';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-periodo',
  templateUrl: './periodo.component.html',
  styleUrls: ['./periodo.component.scss']
})
export class PeriodoComponent implements OnInit {
  navigationSubscription
  pidm: any;
  code_periodo: any;
  fechIn:any;
  fechFin:any;
  periodos: any;
  actividades: {code_periodo: string, pidm: number};
  code: any;
  inicioFecha: any;
  constructor(private route: ActivatedRoute, private router: Router, private Restservice: RestService, public dialogRef: MatDialogRef<PeriodoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { 
  }

  ngOnInit() {
    this.getPer();
  }

  initializar() {
    if (this.route.snapshot.params.pidm ) {
      this.pidm = this.route.snapshot.params.pidm;
    }

    setTimeout(() => {
      // this.getUserbyid(this.idForm);
      // this.spinner.hide();
    }, 500);
  }

 

getPer():void{
  this.Restservice.getData('per/'+this.data.pidm).subscribe(
    data=>{
      this.periodos = data;
      console.log(data);
    }
  )
}

  guardarPer(periodo: string, end: string, inicio: string) {
    this.code_periodo = periodo;
    this.pidm = this.data.pidm;
    this.code = this.data.code;
    //this.fechIn = inicio;
    this.fechFin = end;
    this.inicioFecha = inicio;
    console.log("Code periodo: "+this.code_periodo, "Pidm:  "+this.pidm, "Code dedicación: "+this.data.code, "FechaFin: "+this.fechFin, "fechaInicio"+this.inicioFecha)
  }

  //trae los periodos de un docente
  getPeriodosDocente(pidm: number) {
    this.Restservice.getData('per/' + pidm).subscribe(
      data => {
        //this.periodos = data;
        //this.code_periodo = data.code_periodo;
        this.pidm = pidm
        //this.code= this.data.code;
      } 
    )
  }

  getActividades() {
    this.actividades = this.route.snapshot.params.data;
    this.router.navigate(['/actividades', this.code_periodo, this.pidm, this.code]);
   // console.log('get actividades', this.actividades);
  }
}
