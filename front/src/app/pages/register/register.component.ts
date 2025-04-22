import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {RegisterRequestInterface} from "../../interfaces/register/register-request.interface";
import {registerTextsConstants} from "../../const/REGISTER_TEXTS";
import {formControlTextsConstants} from "../../const/FORM_CONTROL_TEXTS";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public errorMessage: { field: string; message: string }[] = [];
  private passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;

  public form = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordPattern)]],
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
      const registerRequest = this.form.value as RegisterRequestInterface;

      this.authService.register(registerRequest).subscribe(
        {
          next: (response) => {
            this.errorMessage = [];
            this.router.navigate(["profile"]);
          },
          error: (error) => {
            this.errorMessage = error.error?.errorMessage ?? [];
            if (this.errorMessage.length < 0) {
              this.snackBar.open(registerTextsConstants.REGISTER_ERROR_SNACKBAR, registerTextsConstants.REGISTER_SNACKBAR_BUTTON, {})
            }
          }
        }
      );
    } else {
      this.snackBar.open(registerTextsConstants.REGISTER_ERROR_SNACKBAR, registerTextsConstants.REGISTER_SNACKBAR_BUTTON, {})
    }
  }

  protected readonly registerTextsConstants = registerTextsConstants;
  protected readonly formControlTextsConstants = formControlTextsConstants;
}
