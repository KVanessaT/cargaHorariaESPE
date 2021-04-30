import { Component, OnInit, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { NgxSpinnerService } from "ngx-spinner";
import { RestService } from 'app/service/rest.service';
import * as jsPDF from 'jspdf'
import 'jspdf-autotable';
import { ImageConstants } from 'app/constantes/Pdf'
import { version } from '../../../package.json';
import { DatePipe } from '@angular/common';
import { SafeResourceUrl } from '@angular/platform-browser';
import { prependOnceListener } from 'process';
import { ActiveDescendantKeyManager } from '@angular/cdk/a11y';
import { findIndex, last } from 'rxjs/operators';

@Component({
  selector: 'app-report-pdf',
  templateUrl: './report-pdf.component.html',
  styleUrls: ['./report-pdf.component.scss']
})
export class ReportPdfComponent implements OnInit {
  doc: any
  dataReport: any
  test: Date = new Date();
  public version: string = version;
  pipe = new DatePipe('en-US');
  now = Date.now();
  formatoFecha = this.pipe.transform(this.now, 'dd-MM-yyyy h:mm a ');
  urlSafe: SafeResourceUrl;
  datos: boolean;
  noDatos: boolean;
  constructor(public dialogRef: MatDialogRef<ReportPdfComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService, private spinner: NgxSpinnerService) {
    this.datos = false
    this.noDatos = false
  }

  ngOnInit() {
    this.getDataReport();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
  getDataReport() {
    this.rest.getData('dataReport/' + this.data.pidm + '/' + this.data.periodo).subscribe(
      data => {
        this.dataReport = data;
        if (this.dataReport.length == 0) {
          this.noDatos = true;
        }
        else {
          this.visualizarPdf();

        }
      }
    )
  }

  objectKey(obj) {
    return Object.keys(obj);
  }

  formatedReports() {
    return this.dataReport.reduce((prev, now) => {
      if (!prev[now.actividad]) {
        prev[now.actividad] = [];
      }

      prev[now.actividad].push(now);
      return prev;
    }, {});
  }



  //footer PDF
  footer() {
    this.doc.text(500, 800, 'page ' + this.doc.page); //print number bottom right
    this.doc.page++;
  };

  getColumns() {
    const columns = [
      { title: "CODIGO", dataKey: "perjact_asty_code" },
      { title: "DESCRIPCION", dataKey: "stvasty_desc" },
      { title: "HORAS", dataKey: "perjact_std_hrs_per_pay" },
      { title: "PORCENTAJE", dataKey: "perjact_percent" }
    ];
    return columns;
  }

  getheaderStyles() {
    const headStyle = {
      fillColor: [200, 255, 255],
      textColor: 0,
      fontSize: 8
    };
    return headStyle;
  }

  getbodyStyles() {
    const bodyStyle = {
      fillColor: [255, 255, 255],
      textColor: 0,
      fontSize: 8
    }
    return bodyStyle;
  }

  getalternateRowStyles() {
    const alternateRowStyle = {
      fillColor: [255, 255, 255],
      textColor: 0,
      fontSize: 8
    }
    return alternateRowStyle;
  }

  getHt(finalY) {
    this.doc.setLineWidth(1);
    //this.doc.line(450, finalY + 10, 500, finalY + 10);
    this.doc.setFontSize(10);
    this.doc.setTextColor(204, 0, 0);
    this.doc.setFontStyle('bold');
    this.doc.text("TOTAL: " + "     " + this.data.horasT + " horas", 300, finalY + 30);
  }

  visualizarPdf() {
    const totalPagesExp = '{total_pages_count_string}';

    this.doc = new jsPDF('p', 'pt');
    let rows = [];
    const headStyles = this.getheaderStyles();
    const bodyStyles = this.getbodyStyles();
    const alternateRowStyles = this.getalternateRowStyles();
    //header PDF
    const pageContent = ((data) => {
      this.doc.addImage(ImageConstants.F11, 'JPG', 0, 0, 600, 842);
      this.doc.setFontSize(16);
      this.doc.setFontStyle('bold');
      this.doc.text("DISTRIBUCIÓN DE LA CARGA HORARIA", 146, 130);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('bold');
      this.doc.text("NOMBRES: ", 60, 155);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('normal');
      this.doc.text(this.data.docente.nombres + " " + this.data.docente.apellido, 130, 155);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('bold');
      this.doc.text("ID: ", 415, 155);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('normal');
      this.doc.text(this.data.docente.idBanner, 445, 155);
      this.doc.setFontStyle('bold');
      this.doc.text("DEDICACIÓN: ", 60, 170);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('normal');
      this.doc.text(this.data.docente.dedicacion, 130, 170);
      this.doc.setFontStyle('bold');
      this.doc.text("PERIODO: ", 60, 185);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('normal');
      this.doc.text(this.data.fechas.stvtermCode + " " + this.data.fechas.stvtermDesc, 130, 185);
      // FOOTER
      const pageHeight = this.doc.internal.pageSize.height || this.doc.internal.pageSize.getHeight();
      let str = "Pág." + this.doc.internal.getNumberOfPages();
      let sch = (" Universidad de las Fuerzas Armadas ESPE " + " " + this.test.getFullYear() + "; Todos los derechos reservados.");
      let Version = ("Carga Horaria V: " + this.version);
      if (typeof this.doc.putTotalPages === 'function') {
        str = str + " de " + totalPagesExp;
      }
      this.doc.setFontSize(9);
      this.doc.setFontStyle('bold');
      this.doc.text(this.formatoFecha, data.settings.margin.left + 420, pageHeight - 45);
      this.doc.setDrawColor(0, 0, 0);
      this.doc.setLineWidth(1);
      this.doc.line(40, pageHeight - 40, 550, pageHeight - 40);
      this.doc.setFontSize(8);
      this.doc.setFontStyle('normal');
      this.doc.text(str, data.settings.margin.left, pageHeight - 30);
      this.doc.text(sch, data.settings.margin.left + 80, pageHeight - 30);
      this.doc.text(Version, data.settings.margin.left + 425, pageHeight - 30);
    });
    let i = true;
    let first;
    rows = [];
    for (let actty of this.objectKey(this.formatedReports())) {
      for (let subacty of this.formatedReports()[actty]) {
        rows.push({
          perjact_asty_code: subacty.perjact_asty_code,
          stvasty_desc: subacty.stvasty_desc,
          perjact_std_hrs_per_pay: subacty.perjact_std_hrs_per_pay,
          perjact_percent: subacty.perjact_percent
        });
      }

      this.doc.setFontStyle('bold');
      this.doc.setFontSize(8);
      this.doc.setTextColor(0);
      if (i) {
        this.doc.setLineWidth(1);
        this.doc.line(40, 208, 550, 208);
        this.doc.setFontStyle('bold');
        this.doc.text("ACTIVIDAD" + "  " + actty, 40, 218);
        this.doc.setFontSize(8);
        this.doc.autoTable({
          columns: this.getColumns(),
          body: rows,
          startY: 225,
          margin: { top: 185, right: 50, bottom: 90 },
          didDrawPage: pageContent,
          bodyStyles: bodyStyles,
          headStyles: headStyles,
          alternateRowStyles: alternateRowStyles,
          styles: {
            cellPadding: 2,
            fontSize: 7,
            valign: 'middle',
            overflow: 'linebreak',
            tableWidth: '100%',
            lineWidth: 0,
          },
        });
        i = false;
      } else {
        this.doc.setLineWidth(1);
        this.doc.line(40, first.finalY + 20, 550, first.finalY + 20);
        this.doc.setFontStyle('bold');
        this.doc.text("ACTIVIDAD" + "  " + actty, 40, first.finalY + 30);
        this.doc.setFontSize(8);
        this.doc.autoTable({
          columns: this.getColumns(),
          body: rows,
          startY: first.finalY + 40,
          margin: { top: 210, right: 50, bottom: 90 },
          didDrawPage: pageContent,
          bodyStyles: bodyStyles,
          headStyles: headStyles,
          alternateRowStyles: alternateRowStyles,
          styles: {
            cellPadding: 2,
            fontSize: 7,
            valign: 'middle',
            overflow: 'linebreak',
            tableWidth: '100%',
            lineWidth: 0,
          },
        });
      }
      first = this.doc.autoTable.previous;
      //rows = [];
    }




    this.getHt(first.finalY);
    if (typeof this.doc.putTotalPages === 'function') {
      this.doc.putTotalPages(totalPagesExp);
    }
    this.urlSafe = this.doc.output('datauristring')

    const pageHeight = this.doc.internal.pageSize.height;

    // Before adding new content
    let y = 520 // Height position of new content
    if (y >= pageHeight) {
      this.doc.addPage();
      y = 0 // Restart height position
    }

    setTimeout(() => {
      this.spinner.hide();
    }, 3000);
    this.datos = true;
  }

  downloadPdf() {
    this.doc.save('REPORTE_ACTIVIDADES' + this.data.docente.numeroDocumento + '_' + this.formatoFecha + '.pdf');

  }

  PrintPdf() {
    this.doc.autoPrint();
    this.doc.output("dataurlnewwindow");

  }
}
