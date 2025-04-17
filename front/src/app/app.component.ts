import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent {
  title = 'front';

  public displayNavbar(): boolean {
    const currentUrl = window.location.pathname;
    return currentUrl !== '/' && currentUrl !== '/login' && currentUrl !== '/register';
  }
}
