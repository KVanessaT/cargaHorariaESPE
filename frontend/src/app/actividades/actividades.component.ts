import { Component, OnInit, Inject, ViewContainerRef } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { RestService } from 'app/service/rest.service';
import { MatDialog } from '@angular/material';
import { AddActividadesComponent } from './add-actividades/add-actividades.component';
import { EditActividadesComponent } from './edit-actividades/edit-actividades.component';
import { DeleteActividadesComponent } from './delete-actividades/delete-actividades.component';
import { EditSubactividadesComponent } from 'app/subactividades/edit-subactividades/edit-subactividades.component';
import { DeleteSubactividadesComponent } from 'app/subactividades/delete-subactividades/delete-subactividades.component';
import { AddSubactividadesComponent } from 'app/subactividades/add-subactividades/add-subactividades.component';
import { NgxSpinnerService } from "ngx-spinner";
import { Docentes } from 'app/models/docentes.model';
import { PeriodoComponent } from 'app/periodo/periodo.component';
import { Location } from '@angular/common';
import { DatePipe } from '@angular/common';
import { ToastrService } from 'ngx-toastr';
import { ActualizarActividadesComponent } from './actualizar-actividades/actualizar-actividades.component';
import { SafeResourceUrl } from '@angular/platform-browser';
import { ReportPdfComponent } from 'app/report-pdf/report-pdf.component';

@Component({
  selector: 'app-actividades',
  templateUrl: './actividades.component.html',
  styleUrls: ['./actividades.component.scss']
})
export class ActividadesComponent implements OnInit {


  navigationSubscription

  public menssaje: any;
  period: any;
  pidm: any;
  actividades: any;
  cols: any[];
  horasActividad: any;
  subactividad: any;
  code_act: any;
  userCrear: any;
  dateCrear: any;
  userEdit: any;
  dateEdit: any;
  error: boolean;
  options: any = {
    toastLife: 3000,
    dismiss: "auto",
    showCloseButton: true
  };
  des: any;
  docente: Docentes = {
    nombres: '',
    apellido: '',
    pebemplPidm: 0,
    idBanner: '',
    numeroDocumento: '',
    prefijo: '',
    fechaNacimiento: '',
    nacionalidad: '',
    sexo: '',
    tipoSangre: '',
    estadoCivilSith: '',
    campus: '',
    departamento: '',
    codeDedicacion: '',
    tipoEmpleado: '',
    estado: '',//true si tiene actividades en el periodo seleccionado o false sino (true letras verdes y false rojas)
    status: '',//activo o inactivo
    dedicacion: ''
  };
  dataFeIni: any
  horSub: any;
  horAct: any;
  guardar: any;
  modificarDataSub: any
  datosEditSub: any
  pipe = new DatePipe('en-US');
  myFormattedDate: any
  alertR: boolean;
  alertA: boolean;
  alertM: boolean
  porcentaje: number;
  value: number = 0;
  dona: any
  hourD: number[]
  descripcionD: string[];
  docentes: any;
  doCDP: any;
  dataFeFin: any;
  fechas: any;
  statusDoc: string;
  uni: any;
  responsable: any
  docRes: any;
  mensaje: any;
  desPeriodo: any;
  doc: any;
  urlSafe: SafeResourceUrl;
  dataReport: any;

  constructor(private route: ActivatedRoute, private router: Router, private location: Location, private spinner: NgxSpinnerService,
    private rest: RestService, public dialog: MatDialog, private toastr: ToastrService) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initializar();
      }
    });
  }

  ngOnInit() {
    this.initializar();
    this.getFecPer();
    this.getActiv();
    this.cols = [
      { field: 'pzptcabperjact_actividad', header: 'COD. Actividad' },
      { field: 'pzptcabperjact_actividad', header: 'Descripción' },
      { field: 'pzptcabperjact_horas', header: 'Horas' },
      { field: 'pzptcabperjact_unidad_gestion', header: 'Unidad' },
      { field: 'pzptcabperjact_responsable', header: 'Responsable' },
    ];
    this.getDatosDoc();
  }

  initializar() {
    if ((this.route.snapshot.params.code_periodo) && (this.route.snapshot.params.pidm)) {
      this.period = this.route.snapshot.params.code_periodo;
      this.pidm = this.route.snapshot.params.pidm;
    }
    setTimeout(() => {
    }, 500);
  }

  getSubActiv() {
    this.spinner.show();
    this.rest.getData('subA/' + this.pidm + '/' + this.period + '/' + this.code_act).subscribe(
      data => {
        this.subactividad = data;
        this.getHoraAct();
        this.spinner.hide();
        this.donut();
        this.prueb1a();
      }, err => {
        console.log(err);
      }
    )
  }

  getActiv(): void {
    this.subactividad = []
    this.horSub = 0;
    this.horAct = 0;
    this.getFecPer();//again to change the period CHANGEEEE
    this.spinner.show();

    this.rest.getData('act/' + this.period + '/' + this.pidm).subscribe(
      data => {
        this.actividades = data;
        this.getHorasSub();//pidm+periodo
        this.progresBar();
        this.spinner.hide();
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
        this.spinner.hide();
      }
    )


  }

  getFecPer() {
    this.subactividad = []
    this.rest.getData('codePeriodo/' + this.period).subscribe(
      data => {
        this.fechas = data
        this.desPeriodo = this.fechas.stvtermDesc;
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
        setTimeout(() => {
        }, 500);
      }
    )
  }



  agregarAct(): void {
    const dialogRef = this.dialog.open(AddActividadesComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '350px',
      data: { pidm: this.pidm, FechaIni: this.fechas.stvtermStartDate, FechaFin: this.fechas.stvtermEndDate, periodo: this.period, code: this.docente.codeDedicacion, tipoE: this.docente.tipoEmpleado, actividadess: this.actividades }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getActiv();
      this.subactividad = [];
    });
  }

  guardarCodAct(codeA: string, hora: number, desAct: string, unid: string, respon: string, userCrea: number, dateCrea: string, userEdita: number, dateEdita: string) {
    this.code_act = codeA;
    this.horasActividad = hora;
    this.des = desAct;
    this.uni = unid;
    this.responsable = respon;
    this.userCrear = userCrea;
    this.dateCrear = dateCrea;
    this.userEdit = userEdita;
    this.dateEdit = dateEdita;
  }


  updateActividad(): void {
    this.rest.getData('doc/' + this.responsable).subscribe(
      data => {
        this.docRes = data;
        const dialogRef = this.dialog.open(ActualizarActividadesComponent, {
          closeOnNavigation: true,
          disableClose: true,
          width: '400px',
          data: { pidm: this.pidm, codeA: this.code_act, descr: this.des, hourAct: this.horasActividad, unidad: this.uni, FechaIni: this.fechas.stvtermStartDate, periodo: this.period, respons: this.docRes, userC: this.userCrear, dateC: this.dateCrear, userE: this.userEdit, dateE: this.dateEdit }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.getActiv();
          this.subactividad = [];
        });
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');

      }
    )
  }



  eliminarAct(codeA: string, deAct: string, horas: number) {
    this.code_act = codeA;
    this.horasActividad = horas;
    this.des = deAct;
    const dialogRef = this.dialog.open(DeleteActividadesComponent, {
      data: { pidm: this.pidm, periodo: this.period, actividad: this.code_act, descr: this.des, code: this.docente.codeDedicacion, horasAct: this.horasActividad, FechaIni: this.fechas.stvtermStartDate }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.menssaje = result;
      this.getActiv();
    });
  }

  getSubActCodeAct(): void {
    this.dialog.afterOpen.closed;
    this.rest.getData('sub/' + this.code_act).subscribe(
      data => {
        this.actividades = data;
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }

  mensajeActualizar: any
  agregarSubAct(code_act: string): void {
    this.code_act = code_act;
    const dialogRef = this.dialog.open(AddSubactividadesComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '400px',
      data: { pidm: this.pidm, FechaIni: this.fechas.stvtermStartDate, FechaFin: this.fechas.stvtermEndDate, periodo: this.period, code: this.docente.codeDedicacion, code_act: this.code_act, horas: this.actividades.horasActividad, horaTo: this.horSub, un: this.uni, responsable: this.responsable }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.mensajeActualizar = result;
      if (this.mensajeActualizar) {
        this.getSubActiv();
        this.getActiv();
      } else {
        console.log('no')
      }
    });
  }

  info: any
  editarSubAct(subacti: any): void {
    this.info = + subacti.pidm + '/' + subacti.posn + '/' + subacti.suff + '/' + subacti.fechae + '/' + subacti.codprovincia + '/' + subacti.actividad + '/' + subacti.perjact_asty_code;
    this.rest.getData('subAct/' + this.info).subscribe(
      (data: {}) => {
        this.datosEditSub = data
        const dialogRef = this.dialog.open(EditSubactividadesComponent, {
          closeOnNavigation: true,
          disableClose: true,

          data: { objeto: this.datosEditSub, descripcion: subacti, actividades: this.actividades, codP: this.period, code: this.docente.codeDedicacion, code_act: this.code_act, horas: this.actividades.horasActividad, horaTo: this.horSub }    //{ objeto: this.datosEditSub, descripcion: subacti, actividades: this.actividades }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.mensaje = result;
          if (this.mensaje) {
            this.getActiv();
            this.getHorasSub();
            console.log('si')
          } else {
            console.log('no')
          }

        });

      }
    )

  }



  eliminarSubAct(subacti: any): void {
    this.info = + subacti.pidm + '/' + subacti.posn + '/' + subacti.suff + '/' + subacti.fechae + '/' + subacti.codprovincia + '/' + subacti.actividad + '/' + subacti.perjact_asty_code;
    this.rest.getData('subAct/' + this.info).subscribe(
      (data: {}) => {
        this.datosEditSub = data
        const dialogRef = this.dialog.open(DeleteSubactividadesComponent, {
          closeOnNavigation: true,
          disableClose: true,
          data: { objeto: this.datosEditSub, descripcion: subacti, codP: this.period }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.mensaje = result
          if (this.mensaje) {
            this.getActiv();
            this.getSubActiv();
            console.log('si')
          } else {
            console.log('no')
          }
        });

      }
    )
  }
  getDocentes(valor: string) {
    this.spinner.show();
    if (this.docente.status == 'T') {
      valor = 'idmDCP'
    }
    else {
      valor = 'idmPer'
    }
    this.rest.getData(valor + '/' + this.docente.departamento + '/' + this.docente.campus + '/' + this.period).subscribe(
      data => {
        this.doCDP = data;
        this.getActiv();
        this.spinner.hide();
        const dialogRef = this.dialog.open(EditActividadesComponent, {
          closeOnNavigation: true,
          disableClose: true,
          width: '700px',
          data: { docente: this.doCDP, periodo: this.period, actividades: this.actividades }
        });
        dialogRef.afterClosed().subscribe(result => {
          this.getDatosDoc();
          this.getActiv();
          this.getSubActiv();
        });
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
        this.spinner.hide();
      }
    )
  }

  getDatosDoc(): void {
    this.rest.getData('doc/' + this.pidm).subscribe(
      data => {
        this.docente = data;
        this.statusDoc = data.status;
        if (data.status == "A") {
          this.statusDoc = "ACTIVO"
        } else {
          this.statusDoc = "INACTIVO"
        }
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');

      }
    )
  }

  //Envía a componente de periodos
  getPer() {
    const dialogRef = this.dialog.open(PeriodoComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '400px',
      data: { pidm: this.pidm, code: this.docente.codeDedicacion }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getActiv();
    });
  }

  cancel() {
    this.location.back(); // <-- go back to previous location on cancel
  }


  getHoraAct() {
    this.dialog.afterOpen.closed;
    this.rest.getData('sumaH/' + this.pidm + '/' + this.period + '/' + this.code_act).subscribe(
      data => {
        this.horAct = data;
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }



  getHorasSub() {
    this.dialog.afterOpen.closed;
    this.rest.getData('sumaHT/' + this.pidm + '/' + this.period).subscribe(
      data => {
        this.horSub = data;
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }

  trueFal() {
    this.spinner.show();
    this.rest.getData('trueFalse/' + this.pidm + '/' + this.period).subscribe(
      data => {
        this.spinner.hide();
        if (data == 1) {
          this.alertA = true;
        }
        else if (data == 2) {
          this.alertM = true;
        }
        else {
          this.alertR = true
        }
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
        this.spinner.hide();

      }
    )
  }

  progresBar() {

    this.rest.getData('porc/' + this.period + '/' + this.pidm).subscribe(
      data => {
        this.value = data;
      }
    )
  }

  pp: any[] = [];
  desDona: any[] = []
  horasS: any = 0;
  horasSubDona: any;


  donut() {
    for (let numero of this.subactividad) {
      this.pp.push(numero.perjact_std_hrs_per_pay);
      this.desDona.push(numero.stvasty_desc);
    }
    this.dona = {
      labels: this.desDona,
      datasets: [
        {
          data: this.pp,
          backgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#1ddb23",
            "#1d20db",
            "#991ddb",
            "#8cdb1d"
          ],
          hoverBackgroundColor: [
            "#FF6384",
            "#36A2EB",
            "#FFCE56",
            "#1ddb23",
            "#1d20db",
            "#991ddb",
            "#8cdb1d"
          ]
        }]
    };

  }

  prueb1a() {
    this.pp = []
    this.desDona = []
  }
  getReport(): void {
    const dialogRef = this.dialog.open(ReportPdfComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '1000px',
      height: '1000px',
      data: { pidm: this.pidm, periodo: this.period, docente: this.docente, fechas: this.fechas, horasT: this.horSub }
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }
}


