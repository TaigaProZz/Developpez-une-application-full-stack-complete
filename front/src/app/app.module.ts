import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {NgOptimizedImage} from "@angular/common";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import {MatDividerModule} from "@angular/material/divider";
import {MatFormFieldModule} from "@angular/material/form-field";
import { PlainButtonComponent } from './component/plain-button/plain-button.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "./services/auth.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatSidenavModule} from "@angular/material/sidenav";
import {BurgerMenuComponent} from "./layout/burger-menu/burger-menu.component";
import {NavbarComponent} from "./layout/navbar/navbar.component";
import {RegisterComponent} from "./pages/register/register.component";
import { ProfileComponent } from './pages/profile/profile.component';
import {JwtInterceptor} from "./interceptors/jwt.interceptor";
import { SubscriptionCardComponent } from './component/subscription-card/subscription-card.component';
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [AppComponent, HomeComponent, LoginComponent, RegisterComponent, PlainButtonComponent, BurgerMenuComponent, NavbarComponent, ProfileComponent, SubscriptionCardComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    NgOptimizedImage,
    MatButtonToggleModule,
    MatDividerModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatSidenavModule,
    MatCardModule
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    }],
  bootstrap: [AppComponent],
})
export class AppModule {}
