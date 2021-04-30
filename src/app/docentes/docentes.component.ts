import { Component, OnInit } from '@angular/core';
import { RestService } from '../service/rest.service';
import { NgxSpinnerService } from "ngx-spinner";
import { Router, ActivatedRoute } from "@angular/router";
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PeriodoComponent } from 'app/periodo/periodo.component';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-docentes',
  templateUrl: './docentes.component.html',
  styleUrls: ['./docentes.component.scss']
})
export class DocentesComponent implements OnInit {
  campus: any;
  departamentos: any;
  nombreCamp: any;
  nombreDep: any;
  personas: any;
  pidm: any;
  code_periodo: any;
  periodos: { pidm: number, code: string };
  code: any;
  cols: any[];
  banner: string;
  opcion: any;
  datosDir: any;
  crear: any;
  editar: any;
  eliminar: any;
  datos: boolean;
 
  constructor(private rest: RestService, private spinner: NgxSpinnerService,
    private router: Router, public dialog: MatDialog,
    private toastr: ToastrService) {
    this.banner = atob(localStorage.getItem('iduser'));
    this.datos = false;
  }

  ngOnInit() {
    this.getmenu1();
    this.cols = [
      { field: 'id_banner', header: 'ID Docente' },
      { field: 'apellido', header: 'Apellidos' },
      { field: 'nombres', header: 'Nombres' },
      { field: 'dedicacion', header: 'Dedicación' },
    ];
  }

  //Lista de campus
  getCampus() {
    this.rest.getData('allcampus').subscribe(
      data => {
        this.campus = data
      }
    );
  }

  //Lista de departamentos
  getDepart() {
    this.rest.getData('alldepts').subscribe(
      data => {
        this.departamentos = data  
      }
    );
  }

  //Lista de periodos
  getPer() {
    this.rest.getData('allperiodos').subscribe(
      data => {
        this.periodos = data
      }
    );
  }

  //Guarda campus seleccionado
  guardarcampus(nameCampus: string) {
    this.nombreCamp = nameCampus;
  }

  //Guarda departamento seleccionado
  guardarDept(nameDep: string) {
    this.nombreDep = nameDep;
  }

  //consulta campus y departamento mediante un pidm
  getCampDep(valor: string) {
    this.spinner.show();
    this.rest.getData(valor + '/' + this.nombreDep + '/' + this.nombreCamp).subscribe(
      data => {
        if (data.message) {
          this.toastr.warning(data.message, 'Error:')
          this.personas = []
        } else {
          this.personas = data;
        }
        this.spinner.hide();
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }

  //Envía a componente de periodos
  periodoComponent(pidm: number, code: string): void {
    this.pidm = pidm;
    this.code = code;
    const dialogRef = this.dialog.open(PeriodoComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '400px',
      data: { pidm: this.pidm, code: this.code, doc: this.personas }
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  //evita ingresar por ruta al sistema si un usuario no tiene permisos
  getmenu1() {
    this.rest.getUsuario(this.banner + "/" + 41).subscribe(
      data => {
        if (data.message == "No se encontraron resultados") {
          this.router.navigate(["/forbbiden"]);
        }
        if (data.opciones) {
          this.opcion = data.opciones.filter(item => item.opcion == 'Carga Horaria General');
          if (this.opcion.length == 0 || this.opcion == undefined) {
            this.router.navigateByUrl('/forbbiden');
          } else {
            this.datos = true
            this.getCampus();
            this.getDepart();
            this.crear = this.opcion.map(x => x.crear);
            this.crear = this.crear.find(x => x == 1);
            this.editar = this.opcion.map(x => x.modificar);
            this.editar = this.editar.find(x => x == 1);
            this.eliminar = this.opcion.map(x => x.eliminar);
            this.eliminar = this.eliminar.find(x => x == 1);

          }
        }
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }
}
