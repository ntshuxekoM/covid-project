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

  saveLoggedUser(user: any, password: string) {
    console.log("Save Logged User: " + JSON.stringify(user));

    var lr: any = {
      token: user.token,
      type: user.type,
      id: user.id,
      username: user.username,
      email: user.email,
      roles: user.roles,
      fullName: user.fullName,
      password: password,
    };

    window.sessionStorage.removeItem(USER_SESSION);
    window.sessionStorage.setItem(USER_SESSION, JSON.stringify(lr));

    console.log("Done saving logged in user");
  }

  updateProfile(data: any, user: any): Observable<any> {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + user.token });
    let options = { headers: headers };
    let jsonObject = JSON.stringify(data);
    return this.http.post<any>(this.appUrl + '/user/update_users', jsonObject, options);
  }

  changePassword(data: any, user: any): Observable<any> {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + user.token });
    let options = { headers: headers };
    let jsonObject = JSON.stringify(data);
    return this.http.post<any>(this.appUrl + '/user/change_password', jsonObject, options);
  }

}
