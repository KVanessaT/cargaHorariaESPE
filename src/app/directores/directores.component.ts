import { Component, OnInit } from '@angular/core';
import { RestService } from 'app/service/rest.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog } from '@angular/material';
import { PeriodoComponent } from 'app/periodo/periodo.component';
import { ToastrService } from 'ngx-toastr';
import { VerificarActividadComponent } from 'app/directores/verificar-actividad/verificar-actividad.component';
import { ReporteHorarioComponent } from './reporte-horario/reporte-horario.component';
import { PeriodosAsignadosComponent } from './periodos-asignados/periodos-asignados.component';
import { AutoScaleAxis } from 'chartist';
@Component({
  selector: 'app-directores',
  templateUrl: './directores.component.html',
  styleUrls: ['./directores.component.scss']
})
export class DirectoresComponent implements OnInit {

  banner: string// = "L00012050";
  personas: any;
  datosDir: any;
  pidm: any;
  code: any;
  cols: any[];
  cols2: any[];
  menuItems = [];
  opcion: any;
  crear: any;
  editar: any;
  eliminar: any;
  datos: boolean = false;
  solicitud: any[];
  departamento: string;
  campus: string;
  solicitudesP: any;
  verifyCarga: any;
  infoDocente: any;
  imagen: boolean;

  constructor(private rest: RestService, private spinner: NgxSpinnerService, private rutaActiva: ActivatedRoute, private router: Router,
    private route: ActivatedRoute, public dialog: MatDialog, private toastr: ToastrService) {
    this.banner = atob(localStorage.getItem('iduser'));
    this.imagen = false;
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
    this.cols2 = [
      { field: 'id_banner', header: 'ID Docente' },
      { field: 'apellido', header: 'Apellidos' },
      { field: 'nombres', header: 'Nombres' },
      { field: 'dedicacion', header: 'Dedicación' },
      { field: 'solicitudes', header: 'Solicitudes' },

    ];
  }

  getUsuarioIdBanner() {
    this.rest.getData('ban/' + this.banner).subscribe(
      data => {
        this.datosDir = data;
        this.departamento = this.datosDir.departamento;
        this.campus = this.datosDir.campus;
      }
    )
    this.getSolicitudes();
  }

  comprobarDatos(ruta: string) {
    if (this.datosDir.campus == null || this.datosDir.departamento == null) {
      this.toastr.error("Error, el usuario no registra Campus o Departamento. IMPOSIBLE REALIZAR LA BUSQUEDA");
    } else {
      this.getDocentes(ruta);
    }
  }

  getDocentes(ruta) {
    this.spinner.show();
    this.rest.getData(ruta + '/' + this.datosDir.departamento + '/' + this.datosDir.campus).subscribe(
      data => {
        this.personas = data;
        this.spinner.hide();
      }, err => {
        console.log(err);
        this.router.navigateByUrl('/request-error');
      }
    )
  }

  getInactivos() {
    this.spinner.show();
    this.rest.getData('idmInac/' + this.datosDir.departamento + '/' + this.datosDir.campus).subscribe(
      data => {
        this.personas = data;
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
      data: { pidm: this.pidm, code: this.code, campus: this.datosDir.campus, departamento: this.datosDir.depar }
    });
    dialogRef.afterClosed().subscribe(result => {
    });
  }

  getmenu1() {
    this.rest.getUsuario(this.banner + "/" + 41).subscribe(
      data => {
        if (data.message == "No se encontraron resultados") {
          this.router.navigate(["/forbbiden"]);
        }
        if (data.opciones) {
          this.opcion = data.opciones.filter(item => item.opcion == 'Carga Horaria');
          if (this.opcion.length == 0 || this.opcion == undefined) {
            this.router.navigateByUrl('/forbbiden');
          } else {
            this.datos = true
            this.getUsuarioIdBanner();
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

  getSolicitudes() {
    //this.rest.getData('solicitudes/' + 'pendiente/' + this.departamento + '/' + this.campus).subscribe(
    this.rest.getData('solicitudes/' + 'pendiente/' + 'CIENCIAS DE LA COMPUTACION' + '/' + 'ESPE MATRIZ SANGOLQUI').subscribe(
      data => {
        this.solicitud = data;
        this.imagen = true

        console.log(this.solicitud);
      }
    )
  }

  verCarga(rowData) {
    this.verifyCarga = this.route.snapshot.params.data;
    //  this.router.navigate(['/verificarCarga', rowData.pebempl_pidm], { skipLocationChange: true});
    this.router.navigate(['/verificarCarga', btoa(rowData.pebempl_pidm)]);
  }

  guardarData(rowData) {
    console.log(rowData);
    this.infoDocente = rowData;
    console.log(this.infoDocente);
  }


  verReporte() {
    const dialogRef = this.dialog.open(ReporteHorarioComponent, {
      closeOnNavigation: true,
      // height: '700px',   
      disableClose: true,
      data: { docente: this.infoDocente }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  verPeriodos(rowData) {
    this.infoDocente = rowData;
    const dialogRef = this.dialog.open(PeriodosAsignadosComponent, {
      closeOnNavigation: true,
      width: '650px',
      disableClose: true,
      data: { docente: this.infoDocente }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  aprobarTodo(rowData) {
    console.log(rowData);

  }

  rechazarTodo(rowData) {
    console.log(rowData);

  }

}
