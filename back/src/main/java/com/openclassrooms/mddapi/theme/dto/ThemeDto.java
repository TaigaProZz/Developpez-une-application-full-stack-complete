package com.openclassrooms.mddapi.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThemeDto {
    private Long id;
    private String title;
    private String description;
}
