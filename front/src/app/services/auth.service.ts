import {BehaviorSubject, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequestInterface} from "../interfaces/login/login-request.interface";
import {RegisterRequestInterface} from "../interfaces/register/register-request.interface";
import {Injectable} from "@angular/core";
import {LoginResponseInterface} from "../interfaces/login/login-response.interface";
import {RegisterResponseInterface} from "../interfaces/register/register-response.interface";
import {UserInterface} from "../interfaces/user/user.interface";

@Injectable( {
  providedIn: 'root'
})
export class AuthService {
  public isLogged = false;
  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);
  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }

  private userSubject = new BehaviorSubject<UserInterface | undefined>(undefined);
  public $user(): Observable<UserInterface | undefined> {
    return this.userSubject.asObservable();
  }

  constructor(private httpClient: HttpClient) { }

  public login(loginRequestInterface: LoginRequestInterface): Observable<LoginResponseInterface> {
    return this.httpClient.post<LoginResponseInterface>('api/auth/login', loginRequestInterface).pipe(
      tap((response: LoginResponseInterface) => {
          localStorage.setItem('token', response.token);
          this.heartbeat().subscribe();
      })
    );
  }

  public register(registerRequestInterface: RegisterRequestInterface): Observable<RegisterResponseInterface> {
    return this.httpClient.post<RegisterResponseInterface>('api/auth/register', registerRequestInterface).pipe(
      tap((response: RegisterResponseInterface) => {
        localStorage.setItem('token', response.token);
        this.heartbeat().subscribe();
      })
    );
  }

  public logout(): void {
    localStorage.removeItem('token');
    this.userSubject.next(undefined);
    this.isLoggedSubject.next(false);
  }

  public heartbeat(): Observable<UserInterface> {
    return this.httpClient.get<UserInterface>('api/auth/me').pipe(
      tap(user => {
        this.userSubject.next(user);
        this.isLoggedSubject.next(true);
      })
    );
  }
}
