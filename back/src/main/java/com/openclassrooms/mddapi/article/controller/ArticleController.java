package com.openclassrooms.mddapi.article.controller;

import com.openclassrooms.mddapi.article.dto.ArticleWithCommentsDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleResponseDto;
import com.openclassrooms.mddapi.article.dto.ArticleDto;
import com.openclassrooms.mddapi.article.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping()
    public ResponseEntity<CreateArticleResponseDto> createArticle(@RequestBody @Valid CreateArticleRequestDto article, Principal principal) {
        // Save the article using the service
        CreateArticleResponseDto savedArticle = articleService.createArticle(article, principal);
        // Return the saved article in the response
        return ResponseEntity.ok(savedArticle);
    }

    @GetMapping()
    public ResponseEntity< List<ArticleDto>> getAllArticles(Principal principal) {
        // Get all articles using the service
        List<ArticleDto> articles = articleService.getArticlesByUserThemes(principal);
        // Return the articles in the response
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleWithCommentsDto> getArticleById(@PathVariable Long id) {
        // Get the article by ID using the service
        ArticleWithCommentsDto article = articleService.getArticleById(id);
        // Return the article in the response
        return ResponseEntity.ok(article);
    }
}
