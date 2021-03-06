import { DatePipe } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { SafeResourceUrl } from '@angular/platform-browser';
import { RestService } from 'app/service/rest.service';
import { NgxSpinnerService } from 'ngx-spinner';
import * as jsPDF from 'jspdf';
import { ImageConstants } from 'app/constantes/Pdf';

@Component({
  selector: 'app-view-report-by-docente',
  templateUrl: './view-report-by-docente.component.html',
  styleUrls: ['./view-report-by-docente.component.scss']
})
export class ViewReportByDocenteComponent implements OnInit {
  dataReport: any;
  noDatos: boolean;
  urlSafe: SafeResourceUrl;
  doc: any;
  test: Date = new Date();
  version: string
  datos: boolean;
  pipe = new DatePipe('en-US');
  now = Date.now();
  formatoFecha = this.pipe.transform(this.now, 'dd-MM-yyyy h:mm a ');
  constructor(private rest: RestService, public dialogRef: MatDialogRef<ViewReportByDocenteComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private spinner: NgxSpinnerService) {

    this.noDatos = false;
    this.datos = false
  }

  ngOnInit() {
    this.getReport();
  }
  getReport() {
    this.rest.getData('reportPidmPer/' + this.data.periodoC + '/' + this.data.datosBanner.pebemplPidm).subscribe(
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

  onNoClick(): void {
    this.dialogRef.close();
  }

  objectKey(obj) {
    return Object.keys(obj);
  }

  formatedReports() {
    return this.dataReport.reduce((prev, now) => {
      if (!prev[now.nombres]) {
        prev[now.nombres] = [];
      }
      prev[now.nombres].push(now);
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
      { title: "%", dataKey: "perjact_percent" }
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
    this.doc.line(450, finalY + 10, 500, finalY + 10);
    this.doc.setFontSize(10);
    this.doc.setTextColor(204, 0, 0);
    this.doc.setFontStyle('bold');
    // this.doc.text("TOTAL: " + "     " + this.data.horasT + " horas", 400, finalY + 30);
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
      
      this.doc.setFontSize(14);
  
      this.doc.setFontStyle('bold');
      this.doc.text("PERIODO: " + this.data.periodoC, 60, 185);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('normal');
     
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

    for (const actty of this.objectKey(this.formatedReports())) {
      for (const subacty of this.formatedReports()[actty]) {
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
        this.doc.text("DOCENTE: " + "  " + actty, 40, 218);
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
        this.doc.text("DOCENTE: " + "  " + actty, 40, first.finalY + 30);
        this.doc.setFontSize(8);
        this.doc.autoTable({
          columns: this.getColumns(),
          body: rows,
          startY: first.finalY + 40,
          margin: { top: 210, right: 50, bottom: 90 },
          didDrawPage: pageContent,
          headStyles: headStyles,
          bodyStyles: bodyStyles,
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


      let finalY = this.doc.previousAutoTable.finalY;
      rows = [];
      first = this.doc.autoTable.previous
      //rows = [];
      //posy = finalY + 100;

    }

    //this.urlSafe = this.doc.output('datauristring');
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
    //this.doc.save('REPORTE_ACTIVIDADES' + this.data.docente.numeroDocumento + '_' + this.formatoFecha + '.pdf');

  }

  PrintPdf() {
    this.doc.autoPrint();
    this.doc.output("dataurlnewwindow");

  }



}
