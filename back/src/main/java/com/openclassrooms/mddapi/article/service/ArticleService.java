package com.openclassrooms.mddapi.article.service;

import com.openclassrooms.mddapi.article.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleResponseDto;
import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.article.repository.ArticleRepository;
import com.openclassrooms.mddapi.errors.NotFoundException;
import com.openclassrooms.mddapi.theme.model.Theme;
import com.openclassrooms.mddapi.theme.repository.ThemeRepository;
import com.openclassrooms.mddapi.user.model.User;
import com.openclassrooms.mddapi.user.repository.UserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Data
@Service
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final ThemeRepository themeRepository;

  public ArticleService (ArticleRepository articleRepository, UserRepository userRepository, ThemeRepository themeRepository) {
    this.articleRepository = articleRepository;
    this.userRepository = userRepository;
    this.themeRepository = themeRepository;
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
}
