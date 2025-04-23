package com.openclassrooms.mddapi.article.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateArticleRequestDto {
    @Size(min = 3, max = 50)
    private String title;

    @Size(min = 3, max = 255)
    private String content;

    @NotNull
    private Long themeId;
}
