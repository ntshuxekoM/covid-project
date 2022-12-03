import { Component, OnInit } from "@angular/core";
import { AuthServiceService } from "src/app/service/auth-service.service";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ToastrService } from "ngx-toastr";

@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
})
export class ProfileComponent implements OnInit {

  constructor(private authService: AuthServiceService, private toastr: ToastrService,) {}

  public userDetails: any;
  public  currentUser: any;
  public loginResponse: any;


  ngOnInit() {

    this.currentUser = this.authService.getLoggedUser();
    this.authService.getUserDetailsById(this.currentUser.token,this.currentUser.id).subscribe({
      next: (result) => {
        this.userDetails = result;
      },
      error: (error) => {
        console.log("Error: " + error);
        this.toastr.error('Service unavailable');
      }
    })
  }

    
  onUpdateProfile(data: any) {
    console.log(JSON.stringify(data));

     
    this.loginResponse = this.authService.getLoggedUser();
    this.authService.updateProfile(data, this.loginResponse).subscribe({
      next: (results) => {
        console.log(JSON.stringify(results));

        let loginRequest = {
          password: this.loginResponse.password,
          username: data.email
        };


        this.authService.login(loginRequest).subscribe({

          next: (login_results) => {
            this.authService.saveLoggedUser(login_results, this.loginResponse.password);
          },
          error: (error) => {
            console.log("Error: " + JSON.stringify(error));
            this.toastr.error('Unable to refresh token');
          }
        })

        this.toastr.success('User details updated!');


      },
      error: (error) => {
        console.log(JSON.stringify(error));
        this.toastr.error(error.error.message);
      }
    })
  }

  onUpdatePassword(data: any) {
    console.log(JSON.stringify(data));
    if (data.newPassword == data.confirmPassword) {
      let user = this.authService.getLoggedUser();
      this.authService.changePassword(data, user).subscribe({
        next: (results) => {
           this.toastr.success('Password updated successful!');

          this.loginResponse = this.authService.getLoggedUser();
          this.authService.saveLoggedUser(this.loginResponse, data.newPassword);

        },
        error: (error) => {
          console.log(JSON.stringify(error));
          this.toastr.error(error.error.message);
        }
      })
    } else {
      this.toastr.error('New password and confirm password do not match!');
    }

  }
}
