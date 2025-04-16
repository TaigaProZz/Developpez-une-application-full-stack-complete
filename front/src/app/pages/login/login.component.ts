import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {LoginRequestInterface} from "../../interfaces/login/login-request.interface";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public onError = false;

  public form = this.formBuilder.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const loginRequest = this.form.value as LoginRequestInterface;

      this.authService.login(loginRequest).subscribe(
        {
          next: (response) => {
            this.router.navigate(['/profile']);
          },
          error: (error) => {
            this.onError = true;
          }
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }

}
