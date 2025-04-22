import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {LoginRequestInterface} from "../../interfaces/login/login-request.interface";
import {loginTextsConstants} from "../../const/LOGIN_TEXTS";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public form = this.formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required]]
  });

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
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
            this.snackBar.open(loginTextsConstants.ERROR_LOGIN_FAILED, loginTextsConstants.BUTTON_SNACKBAR, {})
          }
        }
      );
    } else {
      this.snackBar.open(loginTextsConstants.ERROR_LOGIN_FAILED, loginTextsConstants.BUTTON_SNACKBAR, {});
    }
  }

    protected readonly loginTextsConstants = loginTextsConstants;
}
