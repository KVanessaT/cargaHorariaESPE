import { Component, OnInit } from '@angular/core';
import { RestService } from 'app/service/rest.service';
import { AuthService } from 'app/services/auth.service';
import { PersonalDataService } from 'app/services/personal-data.service';
import { Router } from "@angular/router";
declare const $: any;
declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  { path: '/carga-general', title: 'Administrador', icon: 'dashboard', class: '' },//Ing Nelly L00012066
  { path: '/carga-director', title: 'Director', icon: 'library_books', class: '' },//Ing Jaime L000012050
  { path: '/report', title: 'Reportes', icon: 'assignment_ind', class: '' }
];

export const ROUTESADM: RouteInfo[] = [  //Administrador
  { path: '/carga-general', title: 'Administrador', icon: 'dashboard', class: '' },
  { path: '/carga-director', title: 'Director', icon: 'library_books', class: '' }
];

export const ROUTESD: RouteInfo[] = [  //Director
  { path: '/carga-director', title: 'Director', icon: 'library_books', class: '' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  // menuItems: any[];
  usuarios: any;
  datosUsuarios: any[];

  public isLoggedIn = false;
  public userName: string;
  menuItems = [];
  menuItems2: any[];
  menuItems3: any[];
  menuItems4: any[];
  usuarioData: any = [];
  pidm;
  id;
  uuser: any;
  idValue: any;
  pidmUser: any;

  constructor(private rest: RestService, private authService: AuthService, private personaldataService: PersonalDataService, private router: Router) { }

  ngOnInit() {
    // this.menuItems = ROUTES.filter(menuItem => menuItem);
    // this.menuUsuario();
    this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      this.userName = this.authService.getUserName();
      // this.menuUsuario()
      this.getUserbyid(this.userName);
      setTimeout(() => {
        //this.getmenu();
      }, 1000);
    }
    // this.menuItems2 = ROUTES.filter(menuItem => menuItem);
  }
  isMobileMenu() {
    if ($(window).width() > 991) {
      return false;
    }
    return true;
  };

  // admn: string = 'L00012066';
  // dir: string = 'L00012050';
  // superU: string = 'L00290697';
  menuUsuario() {
    this.rest.getUsuario(this.idValue + '/' + 41).subscribe(
      data => {
        this.menuItems = Array.from(
          new Set(data.opciones.map(x => x.opcion))
        ).map(datos => {
          return {
            opcion: data.opciones.find(s => s.opcion === datos).opcion,
            url: data.opciones.find(s => s.opcion === datos).url,
            icono: data.opciones.find(s => s.opcion === datos).icono,
            clase: data.opciones.find(s => s.opcion === datos).clase
          };
        });
      },
      error => {
        console.log(error);
      }
    )
  }


  getUserbyid(userName: string) {
    this.rest.getData1(userName).subscribe(
      data => {
        if (data) {
          this.uuser = data;
          this.idValue = this.uuser[0].id;
          this.pidmUser = this.uuser[0].pidm;
          localStorage.setItem('iduser', btoa(this.idValue));
          localStorage.setItem('pidm', btoa(this.pidmUser));
          this.menuUsuario();
        }
        //toaster que te diga no se pudo contactar con el servidor
      }, err => {
        console.log('no se pudo contactar con el servidor');
      }
    )
  }

  getmenu() {
    console.log(atob(localStorage.getItem('iduser')))
    if (localStorage.getItem('iduser')) {
      this.personaldataService.findDataUser(atob(localStorage.getItem('iduser'))).subscribe(
        data => {
          if (data.opciones) {
            this.menuItems = Array.from(
              new Set(data.opciones.map(x => x.opcion))
            ).map(datos => {
            
              return {
                opcion: data.opciones.find(s => s.opcion === datos).opcion,
                url: data.opciones.find(s => s.opcion === datos).url,
                icono: data.opciones.find(s => s.opcion === datos).icono,
                clase: data.opciones.find(s => s.opcion === datos).clase
              };
            });
            
          } this.router.navigate(['/carga-general']);
        },
        error => {
          console.log(error);
        }
      );
    }
  }
}


