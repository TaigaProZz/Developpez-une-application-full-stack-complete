package com.openclassrooms.mddapi.article.service;

import com.openclassrooms.mddapi.article.dto.ArticleWithCommentsDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleResponseDto;
import com.openclassrooms.mddapi.article.dto.ArticleDto;
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

@Data
@Service
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;
  private final CommentRepository commentRepository;

  public ArticleService (ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository, CommentRepository commentRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
    this.commentRepository = commentRepository;
  }

  public CreateArticleResponseDto createArticle(CreateArticleRequestDto createArticleRequestDto, Principal principal) {
    User author = userRepository.findByEmail(principal.getName())
            .orElseThrow(() -> new NotFoundException("User not found"));

    Theme theme = themeRepository.findById(createArticleRequestDto.getThemeId())
            .orElseThrow(() -> new NotFoundException("Theme not found"));

    Article article = new Article();
    article.setTitle(createArticleRequestDto.getTitle());
    article.setContent(createArticleRequestDto.getContent());
    article.setAuthor(author);
    article.setTheme(theme);

    articleRepository.save(article);
    return new CreateArticleResponseDto("Article created successfully");
  }

  public List<ArticleDto> getAllArticles() {
    Iterable<Article> articles = articleRepository.findAll();

    List<ArticleDto> articleDtos = new ArrayList<>();
    for (Article article : articles) {
      ArticleDto articleDto = new ArticleDto();
      articleDto.setId(article.getId());
      articleDto.setTitle(article.getTitle());
      articleDto.setContent(article.getContent());
      articleDto.setAuthorName(article.getAuthor().getUsername());
      articleDto.setCreatedAt(article.getCreatedAt());
      articleDtos.add(articleDto);
    }

    return articleDtos;
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

    ArticleWithCommentsDto articleWithCommentsDto  = new ArticleWithCommentsDto();
    articleWithCommentsDto.setId(article.getId());
    articleWithCommentsDto.setTitle(article.getTitle());
    articleWithCommentsDto.setContent(article.getContent());
    articleWithCommentsDto.setAuthorName(article.getAuthor().getUsername());
    articleWithCommentsDto.setComments(commentDtos);
    articleWithCommentsDto.setThemeName(article.getTheme().getTitle());
    articleWithCommentsDto.setCreatedAt(article.getCreatedAt());

    return articleWithCommentsDto;
  }
}
