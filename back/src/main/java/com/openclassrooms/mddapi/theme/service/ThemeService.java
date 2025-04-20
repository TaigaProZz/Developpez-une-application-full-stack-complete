package com.openclassrooms.mddapi.theme.service;

import com.openclassrooms.mddapi.theme.dto.GetAllThemesDto;
import com.openclassrooms.mddapi.theme.dto.ThemeDto;
import com.openclassrooms.mddapi.theme.model.Theme;
import com.openclassrooms.mddapi.theme.repository.ThemeRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ThemeService {
    private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public GetAllThemesDto getAllThemes() {
        Iterable<Theme> themes = this.themeRepository.findAll();
        List<ThemeDto> themesDto = new ArrayList<>();
        for (Theme theme : themes) {
            themesDto.add(new ThemeDto(theme.getId(), theme.getTitle(), theme.getDescription()));
        }

        return new GetAllThemesDto(themesDto);
    }
}
