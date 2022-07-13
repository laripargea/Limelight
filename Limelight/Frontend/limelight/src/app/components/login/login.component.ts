import { Component, OnInit, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { RegistrationService } from '../../services/registration.service';
import { User } from '../../user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  user = new User();
  msg = '';

  constructor(@Inject(RegistrationService) private _service:RegistrationService, @Inject(Router) private _router:Router) { }

  ngOnInit(): void {
  }

  loginUser() {
    this._service.loginUserFromRemote(this.user).subscribe(
      data => {
        if (data != null) {
          this._router.navigate(['home']);
          localStorage.setItem("User", data.email);
          localStorage.setItem("Role", data.role);
        }
        else {
          this.msg="Bad credentials, please enter valid email and password.";
        }
      }
    )
  }
}
