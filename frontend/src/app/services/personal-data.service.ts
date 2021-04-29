import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpHeaderResponse } from '@angular/common/http';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import { map } from 'rxjs/operators';







//const httpOptions = {
  //headers: new HttpHeaders({ 'Content-Type': 'application/json' })
//};
const URL = 'https://miespemovil.espe.edu.ec/reportes/reporteWs';

const API_URL = URL + '/usuarioByIdCard/';
const API_URL_COM = URL + '/usuarioFacturaByIdCard/';
const CED_URL = URL + '/cedulaById/';
const USER = URL + '/username/';

const httpconf = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    Accept: 'application/json'
  })
};

const headers = new Headers({ 'Content-Type': 'application/json; charset=utf-8' });
const options = new RequestOptions({ headers: headers });

const headersOptions = 
{
  headers,
  options
}

@Injectable({
  providedIn: 'root'
})
export class PersonalDataService {

  constructor(private http: Http) { }
  getUsuario(id): Observable<any> {
    return this.http.get(API_URL + id)
      .map((res: Response) => res.json());
  }

  getCedula(id): Observable<any> {
    return this.http.get(CED_URL + id)
      .map((res: Response) => res.json());
  }

  getUsuarioCompleto(id): Observable<any> {
    return this.http.get(API_URL_COM + id)
      .map((res: Response) => res.json());
  }
 
  getUser(id): Observable<any> {
    return this.http.get(USER + id)
      ;
  }

  findDataUser(user: String) {
    return this.http.get('https://servicios.espe.edu.ec:8443/adm_user-0.0.1-SNAPSHOT/adm/id/' + user + '/23', headersOptions).pipe(
      map(
        (res: any) => {
          return res;
        },
        error => {
          console.log('Error: ', error);
        }
      )
    );
  }
  getPidmByUsuario(userName): Observable<any> {
    return this.http.get(USER + userName);
  }
}
