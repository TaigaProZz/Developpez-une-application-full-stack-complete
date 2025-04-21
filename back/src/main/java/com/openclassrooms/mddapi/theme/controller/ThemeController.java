package com.openclassrooms.mddapi.theme.controller;

import com.openclassrooms.mddapi.theme.dto.GetAllThemesDto;
import com.openclassrooms.mddapi.theme.dto.SubscribingThemeDto;
import com.openclassrooms.mddapi.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/theme")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllThemesDto> getAllThemes() {
        GetAllThemesDto themes = this.themeService.getAllThemes();
        return ResponseEntity.ok(themes);
    }

    @GetMapping("/subscribe")
    public ResponseEntity<GetAllThemesDto> getSubscribedThemes(Principal principal) {
        GetAllThemesDto themes = this.themeService.getSubscribedThemes(principal);
        return ResponseEntity.ok(themes);
    }

    @PostMapping("/subscribe/{themeId}")
    public ResponseEntity<SubscribingThemeDto> subscribeToTheme(@PathVariable Long themeId, Principal principal) {
        this.themeService.subscribeToTheme(themeId, principal);
        return ResponseEntity.ok(new SubscribingThemeDto("You have successfully subscribed to the theme."));
    }
}
