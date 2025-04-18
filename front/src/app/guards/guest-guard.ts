import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GuestGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    if (!this.authService.isLogged) {
      return this.authService.heartbeat().pipe(
        map(user => {
          this.authService.setLoginStatus(user);
          return this.router.createUrlTree(['/profile']);
        }),
        catchError(() => {
          this.authService.logout();
          return of(true);
        })
      );
    }

    return of(this.router.createUrlTree(['/profile']));
  }
}
