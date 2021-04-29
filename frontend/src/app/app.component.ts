import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public isLoggedIn = false;
  public userName: string;
  constructor(private authService: AuthService) { }
  ngOnInit() {
    this.isLoggedIn = this.authService.isLoggedIn();
    if (!this.isLoggedIn) {
    this.login();
    }
  }

  login() {
    this.authService.obtainAccessToken();
  }
}
