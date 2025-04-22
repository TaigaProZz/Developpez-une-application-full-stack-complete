import {Injectable} from "@angular/core";
import {UpdateUserRequestInterface} from "../interfaces/user/update-user-request.interface";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GetAllThemeResponseInterface} from "../interfaces/theme/get-all-theme-response.interface";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  constructor(private httpClient: HttpClient) { }

  public getAllThemes(): Observable<GetAllThemeResponseInterface> {
    return this.httpClient.get<GetAllThemeResponseInterface>('api/theme/all');
  }

  public getUserSubscribedTheme(): Observable<GetAllThemeResponseInterface> {
    return this.httpClient.get<GetAllThemeResponseInterface>('api/theme/subscribe');
  }

  public subscribeToTheme(themeId: string): Observable<any> {
    return this.httpClient.post(`api/theme/subscribe/${themeId}`, {});
  }

  public unsubscribeFromTheme(themeId: string): Observable<any> {
    return this.httpClient.delete(`api/theme/unsubscribe/${themeId}`);
  }
}
