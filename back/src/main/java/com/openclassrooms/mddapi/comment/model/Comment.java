package com.openclassrooms.mddapi.comment.model;

import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(min = 1, max = 255)
  private String content;

  @ManyToOne
  @JoinColumn(name = "article_id", nullable = false)
  private Article article;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
