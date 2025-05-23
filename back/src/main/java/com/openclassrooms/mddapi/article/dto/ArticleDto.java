package com.openclassrooms.mddapi.article.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
  private Long id;
  private String title;
  private String content;
  private String authorName;
  private LocalDateTime createdAt;
}
