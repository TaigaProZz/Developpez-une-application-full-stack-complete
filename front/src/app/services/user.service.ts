import {Injectable} from "@angular/core";
import {UpdateUserRequestInterface} from "../interfaces/user/update-user-request.interface";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private httpClient: HttpClient) { }

  public updateUser(updateUserRequestInterface: UpdateUserRequestInterface): Observable<UpdateUserRequestInterface> {
    return this.httpClient.put<UpdateUserRequestInterface>('api/user', updateUserRequestInterface);
  }
}
