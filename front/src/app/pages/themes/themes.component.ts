import { Component, OnInit } from '@angular/core';
import { ThemeService } from "../../services/theme.service";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ThemeInterface } from "../../interfaces/theme/theme.interface";

@Component({
  selector: 'app-themes',
  templateUrl: './themes.component.html',
  styleUrls: ['./themes.component.css']
})
export class ThemesComponent implements OnInit {
  public allThemes: ThemeInterface[] = [];
  public subscribedThemes: number[] = [];

  constructor(
    private themeService: ThemeService,
    private snackBar: MatSnackBar,
  ) { }

  ngOnInit(): void {
    this.loadThemes();
  }

  loadThemes(): void {
    this.themeService.getAllThemes().subscribe(response => {
      this.allThemes = response.themes;
    });

    this.themeService.getUserSubscribedTheme().subscribe(response => {
      this.subscribedThemes = response.themes.map(t => t.id);
    });
  }

  isSubscribed(themeId: number): boolean {
    return this.subscribedThemes.includes(themeId);
  }

  handleSubscription(themeId: number): void {
    if (this.isSubscribed(themeId)) {
      this.themeService.unsubscribeFromTheme(themeId.toString()).subscribe({
        next: () => {
          this.subscribedThemes = this.subscribedThemes.filter(id => id !== themeId);
          this.snackBar.open('Désabonnement réussi', 'Fermer', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Erreur lors du désabonnement', 'Fermer', { duration: 3000 });
        }
      });
    } else {
      this.themeService.subscribeToTheme(themeId.toString()).subscribe({
        next: () => {
          this.subscribedThemes.push(themeId);
          this.snackBar.open('Abonnement réussi', 'Fermer', { duration: 3000 });
        },
        error: () => {
          this.snackBar.open('Erreur lors de l\'abonnement', 'Fermer', { duration: 3000 });
        }
      });
    }
  }
}
