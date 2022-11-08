import { Component, OnInit } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { DashboardService } from "src/app/service/dashboard.service";

@Component({
  selector: "app-header-stats",
  templateUrl: "./header-stats.component.html",
})
export class HeaderStatsComponent implements OnInit {
  public dashboardData: any;

  constructor(private service: DashboardService, private authSevice: AuthServiceService, private toastr: ToastrService) { }


  ngOnInit() {
    this.service.getDashboardData(this.authSevice.getLoggedUser()).subscribe({
      next: (results) => {

        this.dashboardData = results;
        console.log("Dashboard Data"+JSON.stringify(this.dashboardData));
      },
      error: (error) => {
        console.log(JSON.stringify(error));
        this.toastr.error('Servie unavailable');
      }
    })
  }

}
