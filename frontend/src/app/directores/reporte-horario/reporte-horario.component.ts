import { Inject } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { RestService } from 'app/service/rest.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ToastrService } from 'ngx-toastr';
import * as jsPDF from 'jspdf'
import 'jspdf-autotable';
import { ImageConstants } from 'app/constantes/Pdf';
import { SafeResourceUrl } from '@angular/platform-browser';
import { Docentes } from 'app/models/docentes.model';
import { DatePipe } from '@angular/common';
@Component({
  selector: 'app-reporte-horario',
  templateUrl: './reporte-horario.component.html',
  styleUrls: ['./reporte-horario.component.scss']
})
export class ReporteHorarioComponent implements OnInit {
  test: Date = new Date();
  verData: any;
  infoByPerido: any;
  doc: any;
  urlSafe: SafeResourceUrl;
  infoDocente: any;
  dataDocente: Docentes =
    {
      pebemplPidm: 0,
      nombres: '',
      apellido: '',
      idBanner: '',
      numeroDocumento: '',
      dedicacion: '',
      campus: '',
      departamento: '',
      codeDedicacion: ''
    }
  datosCarga: any;
  horasDed: number;
  pipe = new DatePipe('en-US');

  now = Date.now();
  formatoFecha = this.pipe.transform(this.now, 'dd-MM-yyyy h:mm a ');
  constructor(public dialogRef: MatDialogRef<ReporteHorarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private rest: RestService, private spinner: NgxSpinnerService, private toastr: ToastrService) {
    console.log(this.data);

  }

  ngOnInit() {
    //this.dataDocente();
    console.log(this.data);
    this.informacionDocente();
  }
  informacionDocente() {
    this.spinner.show()
    console.log(this.data.info.docente.id_banner);

    this.rest.getData('ban/' + this.data.info.docente.id_banner).subscribe(
      data => {
        this.dataDocente = data;
        console.log(this.dataDocente);
        console.log(this.dataDocente.nombres);
        console.log(this.dataDocente.apellido);
        console.log(this.dataDocente.idBanner);
        if (this.dataDocente.codeDedicacion == 'EC') {
          this.horasDed = 40
        } else if (this.dataDocente.codeDedicacion == 'EP') {
          this.horasDed = 19
        } else if (this.dataDocente.codeDedicacion == 'EX') {
          this.horasDed = 20
        }
        this.spinner.hide();
        this.cargarData();
      }
    )
    //   this.dataDocente();
    //this.visualizarPdf();
  }

  cargarData() {
    console.log(this.data.periodo.periodo_code);

    this.spinner.show();
    this.rest.getData('getHorario/' + '7777' + '/' + this.data.periodo.periodo_code + '/' + this.data.periodo.fecha_inicio + '/' + this.data.periodo.fecha_fin).subscribe(
      data => {
        console.log(this.data.periodo.periodo_code);

        this.datosCarga = data;
        console.log(this.data.periodo.periodo_code);

        this.spinner.hide();
        console.log(this.datosCarga);
        this.visualizarPdf();
      }
    );
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



  getColumns() {
    const columns = [
      { title: "Horario", dataKey: "horario" },
      { title: "Lunes", dataKey: "lunes" },
      { title: "Martes", dataKey: "martes" },
      { title: "Miércoles", dataKey: "miercoles" },
      { title: "Jueves", dataKey: "jueves" },
      { title: "Viernes", dataKey: "viernes" },
      { title: "Sábado", dataKey: "sabado" },
      { title: "Domingo", dataKey: "domingo" }
    ];
    return columns;
  }

  getHt(finalY) {
    //  this.doc.setLineWidth(1);
    //  this.doc.line(450, finalY + 10, 500, finalY + 10);
    this.doc.setFontSize(10);
    this.doc.setTextColor(204, 0, 0);
    this.doc.setFontStyle('bold');
    
  }

  visualizarPdf() {
    const totalPagesExp = '{total_pages_count_string}';
    // this.doc = new jsPDF('p', 'pt');
    this.doc = new jsPDF('l', 'pt', 'a4');

    let rows = [];
    const headStyles = this.getheaderStyles();
    const bodyStyles = this.getbodyStyles();
    const alternateRowStyles = this.getalternateRowStyles();
    //header PDF
    console.log(this.dataDocente.idBanner);
    const periodoData = this.data.periodo.periodo_code + ' ' + this.data.periodo.periodo_desc + '  Desde: ' + this.data.periodo.fecha_inicio + ' hasta: ' + this.data.periodo.fecha_fin;
    const pageContent = ((data) => {
      this.doc.addImage(ImageConstants.LOGO, 'JPG', 30, 25, 190, 60);
      this.doc.setFontSize(16);
      this.doc.setFontStyle('bold');
      this.doc.text("HORARIO DE TRABAJO Y CLASES", 300, 45);
      this.doc.text("DECLARACIÓN DE ACTIVIDADES SEMANALES", 250, 65);
      this.doc.setFontSize(10);
      this.doc.setFontStyle('bold');
      this.doc.text('PERIODO ACADÉMICO: ' + periodoData, 190, 100);
      this.doc.setFontSize(8);
      this.doc.text("DOCENTE: ", 30, 130);
      this.doc.text("ID: ", 30, 150);
      this.doc.text("CI: ", 140, 150);
      this.doc.text("TITULO: ", 30, 170);
      this.doc.text('TITULAR:  ', 300, 130);
      //  this.doc.text('DEDICACIÓN: tiempo completo ', 300, 140);
      this.doc.text('CATEGORIA:  ', 300, 150);
      this.doc.text('DEDICACIÓN: ', 570, 130);
      this.doc.text('HORAS SEMANALES:  ' + this.horasDed, 570, 150);
      // this.doc.setLineWidth(1);
      // this.doc.line(68, 114, 68, 160);
      // this.doc.setLineWidth(1);
      // this.doc.line(135, 134, 135, 141)
      this.doc.setFontSize(8);
      this.doc.setFontStyle('normal');
      this.doc.text(this.dataDocente.nombres + " " + this.dataDocente.apellido, 80, 130);
      this.doc.text(this.dataDocente.idBanner, 80, 150);
      this.doc.text(this.dataDocente.numeroDocumento, 160, 150);
      this.doc.text(this.dataDocente.dedicacion, 630, 130);






      // this.doc.text(this.infoDocente.descTit, 80, 160);



      /*  this.doc.setFontStyle('bold');
        this.doc.text("DEDICACIÓN: ", 70, 140);
        this.doc.setFontSize(10);
        this.doc.setFontStyle('normal');
       this.doc.text(this.dataDocente.dedicacion, 100, 140); */
      //   this.doc.text(this.data.fechas.stvtermCode + " " + this.data.fechas.stvtermDesc, 130, 185);
      // FOOTER
      const pageHeight = this.doc.internal.pageSize.height || this.doc.internal.pageSize.getHeight();
      let str = "Pág." + this.doc.internal.getNumberOfPages();
      let sch = (" Universidad de las Fuerzas Armadas ESPE " + " " + this.test.getFullYear() + "; Todos los derechos reservados.");
      //  let Version = ("Carga Horaria V: " + this.version);
      if (typeof this.doc.putTotalPages === 'function') {
        str = str + " de " + totalPagesExp;
      }
      this.doc.setLineWidth(0.75);
      this.doc.line(240, pageHeight - 115, 360, pageHeight - 115);
      this.doc.setFontStyle('normal');
     // this.doc.text(this.dataDocente.nombres + " " + this.dataDocente.apellido, 335, pageHeight - 100);
     this.doc.text("DOCENTE ", 270, pageHeight - 100);
     this.doc.setLineWidth(0.75);
      this.doc.line(480, pageHeight - 115, 600, pageHeight - 115);
      this.doc.setFontStyle('normal');
      this.doc.text("COORDINADOR ", 510, pageHeight - 100);
      this.doc.setFontSize(9);
      this.doc.setFontStyle('bold');
      this.doc.text(this.formatoFecha, data.settings.margin.left + 680, pageHeight - 30);
      this.doc.setDrawColor(0, 0, 0);
      this.doc.setLineWidth(1);
      this.doc.line(30, pageHeight - 40, 820, pageHeight - 40);
      this.doc.setFontSize(8);
      this.doc.setFontStyle('normal');
      this.doc.text(str, data.settings.margin.left + 20, pageHeight - 30);
      this.doc.text(sch, data.settings.margin.left + 230, pageHeight - 30);
      // this.doc.text(Version, data.settings.margin.left + 425, pageHeight - 30);
    });
    let i = true;
    let first;


    this.doc.setLineWidth(1);
    this.doc.line(30, 175, 810, 175);
    for (const item of this.datosCarga) {
      rows.push({
        horario: item.horario,
        lunes: item.lunes,
        martes: item.martes,
        miercoles: item.miercoles,
        jueves: item.jueves,
        viernes: item.viernes,
        sabado: item.sabado,
        domingo: item.domingo
      });
    }

    this.doc.setFontStyle('bold');
    this.doc.setFontSize(8);
    this.doc.setTextColor(0);

    if (i) {

      this.doc.setFontStyle('bold');
      //  this.doc.text("PERIODO:" + "  " + this.data.periodo, 20, 218);
      this.doc.setFontSize(8);
      this.doc.autoTable({
        columns: this.getColumns(),
        body: rows,
        startY: 200,
        margin: { top: 185, right: 30, bottom: 90, left: 30 },
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
          lineWidth: 1,
          bodyStyles: bodyStyles,
          headStyles: headStyles,
        },
      });
      i = false;
    }
    let finalY = this.doc.previousAutoTable.finalY;
    rows = [];
    first = this.doc.autoTable.previous
    this.doc.setFontStyle('bold');
    this.doc.text("RESUMEN Y COMPROBACIÓN DE LAS HORAS SEMANALES: ", 30, finalY + 30);
    this.doc.text("Actividades de Docencia ", 60, finalY + 50);
    this.doc.setFontStyle('normal');
    this.doc.text("Carga horaria", 30, finalY + 65);
    this.doc.text("Otras Act. Docencia", 90, finalY + 65);
    this.doc.setFontStyle('bold');
    this.doc.text("Actividades de Investigación", 175, finalY + 50);
    this.doc.text("Act. de Gestión y Dirección académica", 295, finalY + 50);
    this.doc.text("Act. de Vinculación con la sociedad", 450, finalY + 50);
    this.doc.setLineWidth(0.1);
    this.doc.line(30, finalY + 55, 590, finalY + 55);
    this.doc.line(30, finalY + 90, 590, finalY + 90);
    this.doc.line(85, finalY + 55, 85, finalY + 90)
    this.doc.line(170, finalY + 45, 170, finalY + 90)
    this.doc.line(290, finalY + 45, 290, finalY + 90)
    this.doc.line(445, finalY + 45, 445, finalY + 90)
    this.doc.setTextColor(255,0,0);
    this.doc.text("TOTAL: ", 30, finalY + 105);
    this.doc.text("CUMPLE CON LAS HORAS DE SU NOMBRAMIENTO: ", 100, finalY + 105);



    //rows = [];
    //posy = finalY + 100;

    // }

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
    // this.datos = true;
  }

  PrintPdf() {
    // this.doc.autoPrint();
    // this.doc.output("dataurlnewwindow");
    window.open(this.doc.output('bloburl'), '_blank');





  }

  enviar() {
    console.log('yes, it works')
  }
  downloadPdf() {
    this.doc.save('horario' + ' ' + this.dataDocente.numeroDocumento + '_' + this.formatoFecha + '.pdf');
  }
  onNoClick(): void {
    this.dialogRef.close();

  }

}
