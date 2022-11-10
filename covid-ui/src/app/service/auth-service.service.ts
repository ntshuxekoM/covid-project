import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { userProfile } from '../views/model/user-profile';

const USER_SESSION = "user-session";

@Injectable({
  providedIn: 'root'
})

export class AuthServiceService {

  private appUrl = environment.appUrl;

  constructor(private http: HttpClient) { }

  registerUser(data: any): Observable<any> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    let jsonObject = JSON.stringify(data);
    return this.http.post<any>(this.appUrl + '/auth/signup', jsonObject, options);
  }

  login(data: any): Observable<any> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    let jsonObject = JSON.stringify(data);
    return this.http.post<any>(this.appUrl + '/auth/signin', jsonObject, options);
  }

  saveLoggedUser(user: any) {
    window.sessionStorage.removeItem(USER_SESSION);
    window.sessionStorage.setItem(USER_SESSION, JSON.stringify(user));
  }

  getUserDetailsById(userToken: string, userId: number): Observable<userProfile> {
      let headers = new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'Bearer ' + userToken});
      let options = { headers: headers };
      return this.http.get<any>(this.appUrl + '/user/find_users/' + userId, options);
  }

  public getLoggedUser(): any | null {
    const user = window.sessionStorage.getItem(USER_SESSION);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  resetPassword(data: any): Observable<any> {
    let headers = new HttpHeaders({'Content-Type': 'application/json' });
    let options = { headers: headers };
    let jsonObject = JSON.stringify(data);
    return this.http.post<any>(this.appUrl + '/auth/forgot-password', jsonObject, options);
  }

}
