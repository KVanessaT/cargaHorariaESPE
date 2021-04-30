import { Component, OnInit } from '@angular/core';
export type menu = 'general' | 'individual';


@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.scss']
})
export class ReportComponent implements OnInit {

  Menu: menu

  constructor() { }

  ngOnInit() {
  }

  get generalReport() {
    return this.Menu === 'general'
  }

  get individualReport() {
    return this.Menu === 'individual'
  }

  //toma el valor para dirigirse al componente respectivo
  change(valor) {
    this.Menu = valor
  }
}
