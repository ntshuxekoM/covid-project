import { Component, OnInit } from "@angular/core";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
})
export class LoginComponent implements OnInit {
  constructor(private service: AuthServiceService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void { }


  onClickSubmit(data: any) {
    this.service.login(data).subscribe({
      next: (results) => {
        this.service.saveLoggedUser(results,data.password);
        this.toastr.success('Login successful');
        this.router.navigateByUrl("/admin/dashboard");
      },
      error: (error) => {
        console.log('Invalid login details , Please enter the correct details');
        console.log(JSON.stringify(error));
        this.toastr.error('Invalid login details , Please enter the correct details');
      }
    })
  }
}
