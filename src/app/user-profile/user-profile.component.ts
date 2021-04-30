import { Component, OnInit } from '@angular/core';
import { RestService } from 'app/service/rest.service';
import { AuthService } from 'app/services/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  usuario: any;
  userName: any;
  id: any;
  cedula: any;
  nombres: any;
  correoInstitucional: any;
  correoPersonal: any;
  urlFoto: any;
  imagen: string = null;
  constructor(private rest: RestService, private authService: AuthService) {
  }

  ngOnInit() {
    this.userName = this.authService.getUserName();
    this.getUserbyid(this.userName);
  }
  
  getUserbyid(id: string) {
    this.rest.getData1(id).subscribe(
      data => {
        this.usuario = data;
        this.id = this.usuario[0].id;
        this.cedula = this.usuario[0].cedula;
        this.nombres = this.usuario[0].nombres;
        this.correoInstitucional = this.usuario[0].correoInstitucional;
        this.correoPersonal = this.usuario[0].correoPersonal;
        this.imagen = `https://servicios.espe.edu.ec:8443/userimages-0.0.1-SNAPSHOT/imagen/${this.id}`;
        this.urlFoto = this.imagen;

      }
    )
  }
}
