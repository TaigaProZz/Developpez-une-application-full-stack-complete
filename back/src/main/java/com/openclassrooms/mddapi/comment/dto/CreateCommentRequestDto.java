package com.openclassrooms.mddapi.comment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequestDto {
    @Size(min = 1, max = 255)
    private String content;

    @NotNull
    private Long articleId;
}
