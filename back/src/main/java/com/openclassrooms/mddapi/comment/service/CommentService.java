package com.openclassrooms.mddapi.comment.service;

import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.article.repository.ArticleRepository;
import com.openclassrooms.mddapi.comment.dto.CreateCommentRequestDto;
import com.openclassrooms.mddapi.comment.model.Comment;
import com.openclassrooms.mddapi.comment.repository.CommentRepository;
import com.openclassrooms.mddapi.errors.NotFoundException;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Data
@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final ArticleRepository articleRepository;

  public CommentService(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.articleRepository = articleRepository;
  }

  public void createComment(CreateCommentRequestDto createCommentRequestDto, Principal principal) {
    String email = principal.getName();
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new NotFoundException("Utilisateur non trouvé"));

    Article article = articleRepository.findById(createCommentRequestDto.getArticleId())
        .orElseThrow(() -> new NotFoundException("Article non trouvé"));

    Comment comment = Comment.builder()
        .content(createCommentRequestDto.getContent())
        .user(user)
        .article(article)
        .build();

    commentRepository.save(comment);
  }
}
