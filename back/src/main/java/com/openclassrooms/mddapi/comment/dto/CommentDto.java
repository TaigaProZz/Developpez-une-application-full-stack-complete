package com.openclassrooms.mddapi.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private Long id;
  private String content;
  private String authorName;
}
