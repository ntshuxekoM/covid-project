import { Component, OnInit } from "@angular/core";
import { FormsModule } from '@angular/forms';


@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
})
export class RegisterComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}

  onClickSubmit(data: any) {
    console.log(JSON.stringify(data));
  }
}
