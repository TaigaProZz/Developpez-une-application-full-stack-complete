package com.openclassrooms.mddapi.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllThemesDto {
    private List<ThemeDto> themes;
}
