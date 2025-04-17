import {BehaviorSubject, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequestInterface} from "../interfaces/login/login-request.interface";
import {RegisterRequestInterface} from "../interfaces/register/register-request.interface";
import {Injectable} from "@angular/core";
import {LoginResponseInterface} from "../interfaces/login/login-response.interface";
import {RegisterResponseInterface} from "../interfaces/register/register-response.interface";

@Injectable( {
  providedIn: 'root'
})
export class AuthService {
  public isLogged = false;

  private isLoggedSubject = new BehaviorSubject<boolean>(this.isLogged);

  public $isLogged(): Observable<boolean> {
    return this.isLoggedSubject.asObservable();
  }
  constructor(private httpClient: HttpClient) { }

  public login(loginRequestInterface: LoginRequestInterface): Observable<LoginResponseInterface> {

    return this.httpClient.post<LoginResponseInterface>('api/auth/login', loginRequestInterface).pipe(
      tap((response: LoginResponseInterface) => {
        this.isLogged = true;
        this.isLoggedSubject.next(this.isLogged);
        if(response && response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }

  public register(registerRequestInterface: RegisterRequestInterface): Observable<RegisterResponseInterface> {
    return this.httpClient.post<RegisterResponseInterface>('api/auth/register', registerRequestInterface).pipe(
      tap((response: RegisterResponseInterface) => {
        this.isLogged = true;
        this.isLoggedSubject.next(this.isLogged);
        if(response && response.token) {
          localStorage.setItem('token', response.token);
        }
      })
    );
  }
}
