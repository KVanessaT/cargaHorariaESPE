import { Component, OnInit, Inject } from '@angular/core';
import { RestService } from 'app/service/rest.service';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { NgxSpinnerService } from 'ngx-spinner';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-edit-actividades',
  templateUrl: './edit-actividades.component.html',
  styleUrls: ['./edit-actividades.component.scss']
})
export class EditActividadesComponent implements OnInit {
  navigationSubscription
  doCDP: any;
  departamento: string;
  campus: string;
  code_periodo: string;
  actividades: any
  code: string
  fechFin: any;
  inicioFecha: any;
  pidm: any;
  pidmD: any;
  filteredList1: any;
  info: string

  constructor(private rest: RestService, public dialogRef: MatDialogRef<EditActividadesComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any, private route: ActivatedRoute, private spinner: NgxSpinnerService, private router: Router) {
  }

  ngOnInit() {
    this.filteredList1 = this.data.docente
    // if (this.data.docente.estado = 'true') {
    //   this.info = "Contiene carga"
    // }
    // else {
    //   this.info = "No tiene carga"

    // }
  }


  initializar() {
    if (this.route.snapshot.params.pidm) {
      this.pidm = this.route.snapshot.params.pidm;
    }
    setTimeout(() => {
    }, 500);
  }

  guardarDoc(pidm: number) {
    this.pidmD = pidm;
  }

  getActiv() {
    this.actividades = this.route.snapshot.params.data;
    // this.router.navigate(['/actividades', this.code_periodo, this.pidm, this.code, this.inicioFecha, this.fechFin], { skipLocationChange: true});
    this.router.navigate(['/actividades', this.data.periodo, this.pidmD]);
  }
}
