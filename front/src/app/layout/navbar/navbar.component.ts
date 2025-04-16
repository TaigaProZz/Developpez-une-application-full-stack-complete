import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  public isLoggedIn: boolean = true;

  constructor() { }

  ngOnInit(): void {
  }

  public logout(): void {
    // Implement your logout logic here
    this.isLoggedIn = false;
  }

}
