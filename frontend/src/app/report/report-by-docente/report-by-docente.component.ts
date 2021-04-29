import { Component, OnInit } from '@angular/core';
import { RestService } from '../../service/rest.service';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { startWith, map } from 'rxjs/operators';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatDialog } from '@angular/material';
import { ViewReportByDocenteComponent } from './view-report-by-docente/view-report-by-docente.component';
@Component({
  selector: 'app-report-by-docente',
  templateUrl: './report-by-docente.component.html',
  styleUrls: ['./report-by-docente.component.scss']
})
export class ReportByDocenteComponent implements OnInit {
  filteredList1: any;
  periodos: any;
  periodoCode: any;
  periodoDesc: any;


  control = new FormControl();
  streets: string[] = ['Champs-Élysées', 'Lombard Street', 'Abbey Road', 'Fifth Avenue'];
  filteredStreets: Observable<string[]>;
  datosBanner: any;
  banner: string;
  noExist: boolean;
  user: boolean;
  constructor(private rest: RestService, private spinner: NgxSpinnerService, public dialog: MatDialog) {
    this.noExist = false;
    this.user = false;
  }

  ngOnInit() {
    this.getPer();

  }

  getPer(): void {
    this.rest.getData('per').subscribe(
      data => {
        this.periodos = data;
        this.filteredList1 = this.periodos;
      }
    )
  }
  savePeriodo(periodo) {
    this.periodoCode = periodo.stvtermCode;
    this.periodoDesc = periodo.stvtermDesc;
  }

  onSearchChange(searchValue: string) {
    this.banner = searchValue.toUpperCase();
   
    if (this.banner != '' || this.banner.length == 10) {
      this.rest.getData('ban/' + this.banner).subscribe(
        data => {
          this.datosBanner = data;
          if (this.datosBanner != null) {
            this.noExist = false
            this.user = true
          } else {
            this.noExist = true
            this.user = false

          }
        }
      )

      this.noExist = false
    } else {
      this.noExist = true
    }
  }

  getReport() {
    const dialogRef = this.dialog.open(ViewReportByDocenteComponent, {
      closeOnNavigation: true,
      disableClose: true,
      width: '1000px',
      height: '1000px',
      data: { periodoC: this.periodoCode, periodoD: this.periodoDesc, datosBanner: this.datosBanner }
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  uppercasse() {
    return this.banner.toUpperCase();
  }
}
