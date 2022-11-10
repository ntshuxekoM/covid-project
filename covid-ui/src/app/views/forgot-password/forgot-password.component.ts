import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthServiceService } from 'src/app/service/auth-service.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {


  constructor(private service: AuthServiceService, private toastr: ToastrService, private router: Router) { }

  ngOnInit(): void {

  }

  onClickSubmit(data: any) {

    console.log("REQUEST: " + JSON.stringify(data));

    this.service.resetPassword(data).subscribe({
      next: (results) => {
        console.log("SUCCESS: " + results.message);
        this.toastr.success(results.message);
        this.router.navigateByUrl("/login");
      },
      error: (error) => {
        console.log("ERROR: " + JSON.stringify(error));
        this.toastr.error(error.error.message);
      }
    })

  }

}
