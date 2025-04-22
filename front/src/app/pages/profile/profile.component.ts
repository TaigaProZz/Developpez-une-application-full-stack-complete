import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {FormBuilder, Validators} from "@angular/forms";
import {UpdateUserRequestInterface} from "../../interfaces/user/update-user-request.interface";
import {UserService} from "../../services/user.service";
import {ThemeInterface} from "../../interfaces/theme/theme.interface";
import {Subject} from "rxjs";
import {ThemeService} from "../../services/theme.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {profileTextsConstants} from "../../const/PROFILE_TEXTS";
import {formControlTextsConstants} from "../../const/FORM_CONTROL_TEXTS";
import {buttonTextsConstants} from "../../const/GLOBAL_BUTTON_TEXTS";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  protected readonly profileTextsConstants = profileTextsConstants;

  protected readonly formControlTextsConstants = formControlTextsConstants;
  protected readonly buttonTextsConstants = buttonTextsConstants;

  public userSubscribedThemes: ThemeInterface[] = [];

  private passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>]).{8,}$/;

  public form = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(30)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.minLength(8), Validators.pattern(this.passwordPattern)]],
  });

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private themeService: ThemeService,
    private snakeBar: MatSnackBar
    ) { }

  ngOnInit(): void {
    this.authService.$user().subscribe(user => {
      if (user) {
        this.form.patchValue({
          username: user.username,
          email: user.email,
          password: ''
        });
      }
    });

    // get user subscribed themes
    this.themeService.getUserSubscribedTheme().subscribe(response => {
      this.userSubscribedThemes = response.themes;
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const loginRequest = this.form.value as UpdateUserRequestInterface;
      this.userService.updateUser(loginRequest).subscribe(
        {
          next: () => {
            this.snakeBar.open(profileTextsConstants.ON_SUBMIT_UPDATE_USER_SUCCESS_DESCRIPTION_SNACKBAR, profileTextsConstants.BUTTON_SNACKBAR, {});
          },
          error: (error: any) => {
            this.snakeBar.open(profileTextsConstants.ON_SUBMIT_UPDATE_USER_ERROR_SNACKBAR, profileTextsConstants.BUTTON_SNACKBAR, {});
          }
        }
      );
    } else {
      this.snakeBar.open(profileTextsConstants.ON_SUBMIT_UPDATE_USER_FORM_INVALID_SNACKBAR, profileTextsConstants.BUTTON_SNACKBAR, {});
    }
  }

  public handleUnsubscribe(themeId: number): void {
    this.themeService.unsubscribeFromTheme(themeId.toString()).subscribe({
      next: () => {
        this.userSubscribedThemes = this.userSubscribedThemes.filter(theme => theme.id !== themeId);
        this.snakeBar.open(profileTextsConstants.HANDLE_UNSUBSCRIBE_THEME_SUCCESS_SNACKBAR, profileTextsConstants.BUTTON_SNACKBAR, {});
      },
      error: (error: any) => {
        this.snakeBar.open(profileTextsConstants.HANDLE_UNSUBSCRIBE_THEME_ERROR_SNACKBAR, profileTextsConstants.BUTTON_SNACKBAR, {});
      }
    });
  }

}
