import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, Validators} from "@angular/forms";
import {LoginRequestInterface} from "../../interfaces/login/login-request.interface";
import {UpdateUserRequestInterface} from "../../interfaces/user/update-user-request.interface";
import {UserService} from "../../services/user.service";
import {ThemeInterface} from "../../interfaces/theme/theme.interface";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public subscriptionListMock: Array<ThemeInterface> = [
    {
      id: 1,
      title: 'Theme 1',
      description: 'Description 1',
    },
    {
      id: 2,
      title: 'Theme 1',
      description: 'Description 1',
    },
    {
      id: 3,
      title: 'Theme 1',
      description: 'Description 1',
    },
    {
      id: 4,
      title: 'Theme 1',
      description: 'Description 1',
    },
  ];

  public user: any;

  private passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;

  public form = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.minLength(8), Validators.pattern(this.passwordPattern)]],
  });

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private formBuilder: FormBuilder
    ) { }

  ngOnInit(): void {
    this.user = this.authService.user;

    // autofill the form with user data
    if (this.user) {
      this.form.patchValue({
        username: this.user.username,
        email: this.user.email,
        password: ''
      });
    }
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const loginRequest = this.form.value as UpdateUserRequestInterface;
      this.userService.updateUser(loginRequest).subscribe(
        {
          next: () => {
            console.log('User updated successfully');
          },
          error: (error: any) => {
            console.error('Error updating user', error);
          }
        }
      );
    } else {
      console.error('Form is invalid');
    }
  }

  public handleUnsubscription(subscriptionId: number): void {
    console.log(`Unsubscribing from theme with ID: ${subscriptionId}`);
  }

}
