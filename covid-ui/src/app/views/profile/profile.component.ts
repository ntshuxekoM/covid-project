import { Component, OnInit } from "@angular/core";
import {  FormGroup, FormControl } from "@angular/forms";
import { AuthServiceService } from "src/app/service/auth-service.service";

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
})
export class ProfileComponent implements OnInit {

  constructor(private authService: AuthServiceService) {}
  public userProfile: FormGroup;

  ngOnInit(): void {
    let user: any;
    user = this.authService.getLoggedUser();
    if (user) {
      this.authService.getUserDetailsById(user.token, user.id).subscribe({
        next:(results) => {
         this.userProfile =  new FormGroup({
          idNumber: new FormControl(results.idNumber),
          name: new FormControl(results.name),
          surname: new FormControl(results.surname),
          cellNumber: new FormControl(results.cellNumber),
          email: new FormControl(results.email)});
        }
      });
    }
  }

  onClickSubmit() {

  }
}
