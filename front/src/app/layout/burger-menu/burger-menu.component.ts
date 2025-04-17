import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-burger-menu',
  templateUrl: './burger-menu.component.html',
  styleUrls: ['./burger-menu.component.css']
})
export class BurgerMenuComponent implements OnInit {
  public isOpen = false;

  constructor(
    private authService: AuthService,
    private router: Router,
    ) { }

  ngOnInit(): void {
  }

  public logout() {
    this.authService.logout()
    this.router.navigate(['/']);
  }

  toggleMenu() {
    this.isOpen = !this.isOpen;
  }
}
