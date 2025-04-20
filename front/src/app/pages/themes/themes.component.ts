import { Component, OnInit } from '@angular/core';
import {ThemeService} from "../../services/theme.service";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.css']
})
export class ThemesComponent implements OnInit {
  public subscriptionList = this.themeService.getAllThemes()

  constructor(
    private themeService: ThemeService
  ) { }

  ngOnInit(): void {
  }

  handleSubscription($id: number) {
    console.log('id', $id);
  }
}
