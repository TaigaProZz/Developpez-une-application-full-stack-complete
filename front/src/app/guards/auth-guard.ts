import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Observable, of } from 'rxjs';
import { catchError, map, switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(): Observable<boolean | UrlTree> {
    if (this.authService.isLogged) {
      return of(true);
    }

    return this.authService.heartbeat().pipe(
      map(user => {
        return true;
      }),
      catchError(() => {
        this.authService.logout();
        return of(this.router.createUrlTree(['/login']));
      })
    );
  }
}
