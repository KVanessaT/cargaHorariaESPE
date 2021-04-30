import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, NavigationEnd } from '@angular/router';
import { RestService } from 'app/service/rest.service';
import { Location } from '@angular/common';
import { NgxSpinnerService } from 'ngx-spinner';
@Component({
  selector: 'app-verificar-actividad',
  templateUrl: './verificar-actividad.component.html',
  styleUrls: ['./verificar-actividad.component.scss']
})
export class VerificarActividadComponent implements OnInit {
  navigationSubscription
  pidm: any;
  verData: any;

  constructor(private route: ActivatedRoute, private router: Router, private rest: RestService, private _location: Location, private spinner: NgxSpinnerService) {
    this.navigationSubscription = this.router.events.subscribe((e: any) => {
      if (e instanceof NavigationEnd) {
        this.initializar();
      }
    });
  }

  ngOnInit() {
  }
  initializar() {
    if (this.route.snapshot.params.pidm) {
      let pidmD = atob(this.route.snapshot.params.pidm);
      console.log(pidmD);
      this.rest.getData('dataCarga/' + pidmD + '/pendiente').subscribe(
        data => {
          this.verData = data;
          console.log(this.verData);
        }
      );
    }
    setTimeout(() => {
    }, 500);
  }

  atras() {
    this._location.back();
  }
  guardarCarga() {
    console.log(this.verData);
    this.spinner.show();
    
    this.rest.updateData('updCarga', this.verData).subscribe(
      data => {
        console.log(this.verData)
        this.spinner.hide();
        this.initializar()
      }
    );
  }

}
