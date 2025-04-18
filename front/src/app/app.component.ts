import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  constructor() {}

  ngOnInit() {
  }

  public displayNavbar(): boolean {
    const currentUrl = window.location.pathname;
    return currentUrl !== '/' && currentUrl !== '/login' && currentUrl !== '/register';
  }
}
