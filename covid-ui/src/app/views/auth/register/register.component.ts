import { Component, OnInit } from "@angular/core";
import { AuthServiceService } from "src/app/service/auth-service.service";



@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
})
export class RegisterComponent implements OnInit {
  constructor(private service: AuthServiceService ) {}

  ngOnInit(): void {}

  onClickSubmit(data: any) {
    console.log(JSON.stringify(data));
    this.service.registerUser(data).subscribe({
      next:(results) => {
        console.log(results.message);
      },
      error: (error) => {
        
      }
    })
  }
}
