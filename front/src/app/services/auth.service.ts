import {BehaviorSubject, Observable, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {LoginRequestInterface} from "../interfaces/login/login-request.interface";
import {RegisterRequestInterface} from "../interfaces/register/register-request.interface";
import {Injectable} from "@angular/core";

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

  public login(loginRequestInterface: LoginRequestInterface): Observable<String> {

    return this.httpClient.post<string>('api/auth/login', loginRequestInterface).pipe(
      tap((response: string) => {
        this.isLogged = true;
        this.isLoggedSubject.next(this.isLogged);
        localStorage.setItem('token', response);
      })
    );
  }

  public register(registerRequestInterface: RegisterRequestInterface): Observable<void> {
    return this.httpClient.post<void>('api/auth/register', registerRequestInterface);
  }
}
