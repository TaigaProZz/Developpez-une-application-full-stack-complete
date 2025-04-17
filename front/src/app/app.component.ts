import {Component, OnInit} from '@angular/core';
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  title = 'front';
  isLogged: boolean = false;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.$isLogged().subscribe((isLoggedIn: boolean) => {
      this.isLogged = isLoggedIn;
    });
  }

  public displayNavbar(): boolean {
    const currentUrl = window.location.pathname;
    return currentUrl !== '/' && currentUrl !== '/login' && currentUrl !== '/register';
  }
}
