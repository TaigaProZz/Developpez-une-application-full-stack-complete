package com.openclassrooms.mddapi.comment.dto;

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

    private Long articleId;
}
