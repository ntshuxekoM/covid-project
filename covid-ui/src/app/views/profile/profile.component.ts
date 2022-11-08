import { Component, OnInit } from "@angular/core";
import {  UntypedFormGroup, UntypedFormControl } from "@angular/forms";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
})
export class ProfileComponent implements OnInit {

  constructor(private authService: AuthServiceService) {}
  public userProfile: UntypedFormGroup;

  ngOnInit(): void {
    let user: any;
    user = this.authService.getLoggedUser();
    if (user) {
      this.authService.getUserDetailsById(user.token, user.id).subscribe({
        next:(results) => {
         this.userProfile =  new UntypedFormGroup({
          idNumber: new UntypedFormControl(results.idNumber),
          name: new UntypedFormControl(results.name),
          surname: new UntypedFormControl(results.surname),
          cellNumber: new UntypedFormControl(results.cellNumber),
          email: new UntypedFormControl(results.email)});
        }
      });
    }
  }

    
  onClickSubmit(data: any) {
    console.log("Profile Data: ")+JSON.stringify(data) ;
  }
}
