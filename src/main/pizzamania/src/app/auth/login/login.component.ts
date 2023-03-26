import { Component } from '@angular/core';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { tap } from 'rxjs';
import { LoginService } from '../service/login.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  constructor(
    private formBuilder: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: '',
    });
  }

  Login() {
    const username = this.loginForm.get('username')?.value;
    const passowrd = this.loginForm.get('password')?.value;

    this.loginService.login(username, passowrd).subscribe({
      next: (data) => {
        console.log(data);
        console.log(data.data.token);
        console.log(data.data.user);
        localStorage.setItem('token', data.data.token);
        localStorage.setItem('fullname', JSON.stringify(data.data.user));
        this.router.navigateByUrl("/");
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
}
