import { Component, OnInit } from '@angular/core';
import {ThemeService} from "../../services/theme.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.css']
})
export class ThemesComponent implements OnInit {
  subscriptionList = this.themeService.getAllThemes();

  constructor(
    private themeService: ThemeService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
  }

  handleSubscription($id: number) {
    this.themeService.subscribeToTheme($id).subscribe({
      next: (response) => {
         this.snackBar.open('Subscribe réussi', 'Fermer', {});
      },
      error: (error) => {
        this.snackBar.open('Erreur, veuillez réessayer', 'Fermer', {});
      }
    })
  }
}
