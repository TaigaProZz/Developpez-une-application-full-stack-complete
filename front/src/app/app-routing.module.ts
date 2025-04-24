import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import {LoginComponent} from "./pages/login/login.component";
import {RegisterComponent} from "./pages/register/register.component";
import {ProfileComponent} from "./pages/profile/profile.component";
import {AuthGuard} from "./guards/auth-guard";
import {GuestGuard} from "./guards/guest-guard";
import {ThemesComponent} from "./pages/themes/themes.component";
import {CreateArticleComponent} from "./pages/create-article/create-article.component";
import {FeedComponent} from "./pages/feed/feed.component";
import {ArticleDetailComponent} from "./pages/article-detail/article-detail.component";

// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [GuestGuard] },
  { path: 'login', component: LoginComponent, canActivate: [GuestGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [GuestGuard]},
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: 'themes', component: ThemesComponent, canActivate: [AuthGuard] },
  { path: 'create-article', component: CreateArticleComponent, canActivate: [AuthGuard] },
  { path: 'feed', component: FeedComponent, canActivate: [AuthGuard] },
  { path: 'article-detail/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
