package com.openclassrooms.mddapi.theme.service;

import com.openclassrooms.mddapi.errors.UserAlreadySubscribedException;
import com.openclassrooms.mddapi.theme.dto.GetAllThemesDto;
import com.openclassrooms.mddapi.theme.dto.ThemeDto;
import com.openclassrooms.mddapi.theme.model.Theme;
import com.openclassrooms.mddapi.theme.repository.ThemeRepository;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class ThemeService {
    private final UserRepository userRepository;
    private ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository, UserRepository userRepository) {
        this.themeRepository = themeRepository;
        this.userRepository = userRepository;
    }

    public GetAllThemesDto getAllThemes() {
        Iterable<Theme> themes = this.themeRepository.findAll();
        List<ThemeDto> themesDto = new ArrayList<>();
        for (Theme theme : themes) {
            themesDto.add(new ThemeDto(theme.getId(), theme.getTitle(), theme.getDescription()));
        }

        return new GetAllThemesDto(themesDto);
    }

    public void subscribeToTheme(Long themeId, Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new UsernameNotFoundException("Thème non trouvé"));


        if (themeRepository.isUserSubscribedToTheme(user.getId(), theme.getId())) {
            throw new UserAlreadySubscribedException("L'utilisateur est déjà abonné à ce thème");
        }
        themeRepository.addUserToTheme(user.getId(), theme.getId());
    }

    public GetAllThemesDto getSubscribedThemes(Principal principal) {
        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        List<Theme> themes = themeRepository.findSubscribedThemesByUserId(user.getId());
        List<ThemeDto> themesDto = new ArrayList<>();
        for (Theme theme : themes) {
            themesDto.add(new ThemeDto(theme.getId(), theme.getTitle(), theme.getDescription()));
        }

        return new GetAllThemesDto(themesDto);
    }
}
