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
//import { ToastsManager } from "ng6-toastr";
//import { ToastrService } from 'ngx-toastr';

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
  fechaIni: any;
  fechaFin: any;
  actividades: any;
  cols: any[];
  horasActividad: any;
  subactividad: any;
  code_act: any;
  code: any;
  error: boolean;
  options: any = {
    toastLife: 3000,
    dismiss: "auto",
    showCloseButton: true
  };
  constructor(private route: ActivatedRoute, private router: Router, private spinner: NgxSpinnerService,
    private rest: RestService, public dialog: MatDialog,
  ) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initializar();
      }
    });
  }

  ngOnInit() {
    this.getActiv();
    this.cols = [
      { field: 'pzptcabperjact_actividad', header: 'COD. Actividad' },
      { field: 'pzptcabperjact_actividad', header: 'Descripción' },
      { field: 'pzptcabperjact_horas', header: 'Horas' },
      { field: 'pzptcabperjact_unidad_gestion', header: 'Unidad' },
      { field: 'pzptcabperjact_responsable', header: 'Responsable' },
    ];

  }


  initializar() {
    if ((this.route.snapshot.params.code_periodo) && (this.route.snapshot.params.pidm) && (this.route.snapshot.params.code)) {
      this.period = this.route.snapshot.params.code_periodo;
      this.pidm = this.route.snapshot.params.pidm;
      this.code = this.route.snapshot.params.code;

      console.log(this.period, this.pidm, this.code)
    }
    setTimeout(() => {
      // this.spinner.hide();
    }, 500);
  }

  getActiv(): void {
    this.dialog.afterOpen.closed;

    this.rest.getData('act/' + this.period + '/' + this.pidm).subscribe(
      data => {
        this.actividades = data;
        console.log(this.period, this.pidm)
      }
    )
  }

  agregarAct(): void {
    const dialogRef = this.dialog.open(AddActividadesComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '350px',
      data: { pidm: this.pidm, FechaIni: this.fechaIni, FechaFin: this.fechaFin, periodo: this.period, code: this.code }
    });
    dialogRef.afterClosed().subscribe(result => {
      this.getActiv();
    });
  }

  editarAct(): void {
    const dialogRef = this.dialog.open(EditActividadesComponent, {
      data: this.actividades
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }


  guardarCodAct(codeA: string, hora: number) {
    this.code_act = codeA;
    this.horasActividad = hora;
    console.log("la actividad elegida es :" + this.code_act + ", Horas de la actividad: " + this.horasActividad);
  }


  eliminarAct(codeA: string, horas: number) {
    this.code_act = codeA;
    this.horasActividad = horas;
    console.log(this.code_act, this.horasActividad, this.pidm, this.period, this.code)
    const dialogRef = this.dialog.open(DeleteActividadesComponent, {
      data: { pidm: this.pidm, periodo: this.period, actividad: this.code_act, code: this.code, horasAct: this.horasActividad }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.menssaje = result;
     
      this.getActiv();
      console.log('The dialog was closed');
    });
  }


  getSubActiv() {
    this.spinner.show();
    this.rest.getData('subA/' + this.pidm + '/' + this.period + '/' + this.code_act).subscribe(
      data => {
        this.subactividad = data;
        this.spinner.hide();
        console.log(data);
        console.log('subactividades de la actividad:', data);
      }
    )
  }

  getSubActCodeAct(): void {
    this.dialog.afterOpen.closed;

    this.rest.getData('sub/' + this.code_act).subscribe(
      data => {
        this.actividades = data;
        console.log(this.period, this.pidm, this.code_act)
      }
    )
  }


  agregarSubAct(code_act: string): void {
    this.code_act = code_act;
    const dialogRef = this.dialog.open(AddSubactividadesComponent, {
      width: '350px',
      data: { code_act: this.code_act }
    }
    )
    console.log(code_act);
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  editarSubAct(): void {
    const dialogRef = this.dialog.open(EditSubactividadesComponent, {
      data: this.subactividad
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  eliminarSubAct(): void {
    const dialogRef = this.dialog.open(DeleteSubactividadesComponent, {
      data: this.subactividad
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
