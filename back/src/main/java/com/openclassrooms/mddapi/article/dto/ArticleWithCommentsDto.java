package com.openclassrooms.mddapi.article.dto;

import com.openclassrooms.mddapi.comment.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleWithCommentsDto {
  private Long id;
  private String title;
  private String content;
  private String authorName;
  private LocalDateTime createdAt;
  private List<CommentDto> comments;
}