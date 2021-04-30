import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { AutoScaleAxis } from 'chartist';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import { ReporteHorarioComponent } from '../reporte-horario/reporte-horario.component';

@Component({
  selector: 'app-periodos-asignados',
  templateUrl: './periodos-asignados.component.html',
  styleUrls: ['./periodos-asignados.component.scss']
})
export class PeriodosAsignadosComponent implements OnInit {
  periodos: any;
  periodosData: any;
valores: any;
  constructor(public dialogRef: MatDialogRef<PeriodosAsignadosComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService, private spinner: NgxSpinnerService, private toastr: ToastrService, public dialog: MatDialog) { 
      console.log(this.data);
      this.valores = this.data;

    }
  ngOnInit() {
   
    this.getPeriodos();

  }

  getPeriodos() {
    this.rest.getData('perDocente/' + this.data.docente.id_banner).subscribe(
      data => {
        this.periodos = data;
        console.log(this.periodos);
      }
    )
  }

  saveCodPeriodo(valor) {
    console.log(valor);
    this.periodosData = valor;
    const dialogRef = this.dialog.open(ReporteHorarioComponent, {
      closeOnNavigation: true,
      disableClose: true,
    height: '700px',
    width:'2300px',
      data: { periodo: this.periodosData, info:this.valores }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}


