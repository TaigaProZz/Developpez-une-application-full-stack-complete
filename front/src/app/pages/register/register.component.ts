import { Component, OnInit } from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {RegisterRequestInterface} from "../../interfaces/register/register-request.interface";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  public onError = false;
  public errorMessage = '';
  public onSuccess = false;
  private passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;

  public form = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(this.passwordPattern)]],
  });

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const registerRequest = this.form.value as RegisterRequestInterface;

      this.authService.register(registerRequest).subscribe(
        {
          next: (response) => {
            this.onSuccess = true;
          },
          error: (error) => {
            this.onError = true;
            if(error.error && error.error.message) {
              this.errorMessage = error.error.message;
            }
          }
        }
      );
    } else {
      console.log('Form is invalid');
    }
  }
}
