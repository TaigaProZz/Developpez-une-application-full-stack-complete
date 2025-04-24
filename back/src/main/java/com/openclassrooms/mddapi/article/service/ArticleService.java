package com.openclassrooms.mddapi.article.service;

import com.openclassrooms.mddapi.article.dto.ArticleWithCommentsDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleResponseDto;
import com.openclassrooms.mddapi.article.dto.ArticleDto;
import com.openclassrooms.mddapi.article.mapper.ArticleMapper;
import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.article.repository.ArticleRepository;
import com.openclassrooms.mddapi.comment.dto.CommentDto;
import com.openclassrooms.mddapi.comment.model.Comment;
import com.openclassrooms.mddapi.comment.repository.CommentRepository;
import com.openclassrooms.mddapi.errors.NotFoundException;
import com.openclassrooms.mddapi.theme.model.Theme;
import com.openclassrooms.mddapi.theme.repository.ThemeRepository;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Service
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;
  private final CommentRepository commentRepository;
  private final ArticleMapper articleMapper;

  public ArticleService (ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository, CommentRepository commentRepository, ArticleMapper articleMapper) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
    this.commentRepository = commentRepository;
    this.articleMapper = articleMapper;
  }

  public CreateArticleResponseDto createArticle(CreateArticleRequestDto createArticleRequestDto, Principal principal) {
    User author = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new NotFoundException("User not found"));

    Theme theme = themeRepository.findById(createArticleRequestDto.getThemeId())
            .orElseThrow(() -> new NotFoundException("Theme not found"));

    Article article = Article.builder()
            .title(createArticleRequestDto.getTitle())
            .content(createArticleRequestDto.getContent())
            .author(author)
            .theme(theme)
            .build();

    articleRepository.save(article);
    return new CreateArticleResponseDto("Article created successfully");
  }

  public List<ArticleDto> getArticlesByUserThemes(Principal principal) {
    User user = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new NotFoundException("User not found"));


    List<Article> articles = articleRepository.findArticlesByUserSubscribedThemes(user.getId());

    return articles.stream()
            .map(articleMapper::articleToArticleDto)
            .toList();
  }


  public ArticleWithCommentsDto getArticleById(Long id) {
    Article article = articleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Article not found"));

    List<Comment> comments = commentRepository.findByArticleId(id);

    List<CommentDto> commentDtos = comments.stream()
            .map(comment -> new CommentDto(
                    comment.getId(),
                    comment.getContent(),
                    comment.getUser().getUsername()))
            .toList();

    return articleMapper.articleToArticleWithCommentsDto(article, commentDtos);
  }
}
