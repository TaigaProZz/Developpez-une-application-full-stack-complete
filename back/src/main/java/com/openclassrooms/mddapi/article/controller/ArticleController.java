package com.openclassrooms.mddapi.article.controller;

import com.openclassrooms.mddapi.article.dto.CreateArticleRequestDto;
import com.openclassrooms.mddapi.article.dto.CreateArticleResponseDto;
import com.openclassrooms.mddapi.article.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
