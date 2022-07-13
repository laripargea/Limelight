import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegistrationService } from '../../services/registration.service';
import { User } from '../../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  user = new User();
  msg = '';

  constructor(@Inject(RegistrationService) private _service:RegistrationService, @Inject(Router) private _router:Router) { }

  ngOnInit(): void {
  }

  registerUser() {
    this._service.registerUserFromRemote(this.user).subscribe(
      data => {
        this._router.navigate(['home']);
        localStorage.setItem("User", data.email);
        localStorage.setItem("Role", data.role);
      },
      error => {
        this.msg="User already registered.";
      }
    )
  }
}
