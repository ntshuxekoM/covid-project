import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private appUrl = environment.appUrl;

  constructor(private http: HttpClient) { }

  getDashboardData(user: any) {
    console.log("Getting dashboard data");
    let queryParams = new HttpParams();
  
    let headers = new HttpHeaders({ 'Content-Type': 'application/json', 'Authorization': 'Bearer ' + user.token });
    let options = { headers: headers};

    return this.http.get<any>(this.appUrl + '/get-dashboard-data', options);
  }
}
