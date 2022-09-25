import { Component, OnInit } from "@angular/core";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { Router } from "@angular/router";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
})
export class LoginComponent implements OnInit {
  constructor(private service: AuthServiceService, private router: Router) {}

  ngOnInit(): void {}

  
  onClickSubmit(data: any) {
    this.service.login(data).subscribe({
      next:(results) => {
        this.service.saveLoggedUser(results);
        this.router.navigateByUrl("/admin/dashboard");
      },
      error: (error) => {
        console.log('Invalid login details , Please enter the correct details');
      }
    })
  }
}
