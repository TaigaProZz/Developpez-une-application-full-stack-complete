import {Component, ElementRef, OnInit, Renderer2} from '@angular/core';

@Component({
  selector: 'app-burger-menu',
  templateUrl: './burger-menu.component.html',
  styleUrls: ['./burger-menu.component.css']
})
export class BurgerMenuComponent implements OnInit {
  public isOpen = false;
  constructor() { }

  ngOnInit(): void {
  }

  public logout() {
    console.log("Logout clicked");
  }

  toggleMenu() {
    this.isOpen = !this.isOpen;
  }
}
