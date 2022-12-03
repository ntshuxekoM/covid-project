import { Component, OnInit } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { DashboardService } from "src/app/service/dashboard.service";

@Component({
  selector: "app-admin-navbar",
  templateUrl: "./admin-navbar.component.html",
})
export class AdminNavbarComponent implements OnInit {

  constructor(private service: DashboardService, private authSevice: AuthServiceService, private toastr: ToastrService) { }

  ngOnInit() {
    
  }

  getCurrentUser(){
    return this.authSevice.getLoggedUser();
  }

}
