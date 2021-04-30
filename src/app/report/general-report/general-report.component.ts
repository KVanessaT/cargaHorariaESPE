import { Component, OnInit } from '@angular/core';
import { RestService } from '../../service/rest.service';
import * as jsPDF from 'jspdf'
import 'jspdf-autotable';
import { ImageConstants } from 'app/constantes/Pdf'
import { DatePipe } from '@angular/common';
import { NgxSpinnerService } from "ngx-spinner";
import { SafeResourceUrl } from '@angular/platform-browser';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ViewGeneralReportComponent } from './view-general-report/view-general-report.component';
import { ViewGeneralReportPeriodoComponent } from './view-general-report-periodo/view-general-report-periodo.component';

@Component({
  selector: 'app-general-report',
  templateUrl: './general-report.component.html',
  styleUrls: ['./general-report.component.scss']
})
export class GeneralReportComponent implements OnInit {
  urlSafe: SafeResourceUrl;

  listCampus: any;
  listDepartamentos: any;
  listPeriodos: any;
  filteredList1: any;
  campusDescripcion: string;
  departDescripcion: string;
  periodoCode: string;
  noDatos: boolean;
  dataReportCDP: any;
  doc: any;
  dataReport: any
  test: Date = new Date();
  version: string
  pipe = new DatePipe('en-US');
  now = Date.now();
  formatoFecha = this.pipe.transform(this.now, 'dd-MM-yyyy h:mm a ');
  datos: boolean;
  periodoDesc: any;
  periodo: any;
  periodoDsc: any;
  constructor(private rest: RestService, private spinner: NgxSpinnerService, public dialog: MatDialog) { }

  ngOnInit() {
    this.getCampus();
    this.getDepart();
    this.getPer();
  }
  //Lista de campus
  getCampus() {
    this.rest.getData('allcampus').subscribe(
      data => {
        this.listCampus = data
      }
    );
  }

  //Lista de departamentos
  getDepart() {
    this.rest.getData('alldepts').subscribe(
      data => {
        this.listDepartamentos = data
      }
    );
  }

  getPer(): void {
    this.rest.getData('per').subscribe(
      data => {
        this.listPeriodos = data;
        this.filteredList1 = this.listPeriodos;
      }
    )
  }

  saveCampus(campus) {
    this.campusDescripcion = campus.stvcampDesc
  }

  saveDepartamento(departamento) {
    this.departDescripcion = departamento.stvcollDesc;
  }

  savePeriodo(periodo) {
    this.periodoCode = periodo.stvtermCode;
    this.periodoDesc = periodo.stvtermDesc;
  }

  getReportCDP() {
    const dialogRef = this.dialog.open(ViewGeneralReportComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '1000px',
      height: '1000px',
      data: { campus: this.campusDescripcion, departamento: this.departDescripcion, periodoC: this.periodoCode, periodoD: this.periodoDesc }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  savePer(periodo) {
    this.periodo = periodo.stvtermCode
    this.periodoDsc = periodo.stvterDesc;
  }
  getReportP() {
    const dialogRef = this.dialog.open(ViewGeneralReportPeriodoComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '1000px',
      height: '1000px',
      data: { periodoC: this.periodo, periodoD: this.periodoDsc }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}