package com.openclassrooms.mddapi.theme.controller;

import com.openclassrooms.mddapi.theme.dto.GetAllThemesDto;
import com.openclassrooms.mddapi.theme.service.ThemeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
}
