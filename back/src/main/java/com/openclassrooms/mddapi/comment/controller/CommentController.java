package com.openclassrooms.mddapi.comment.controller;

import com.openclassrooms.mddapi.comment.dto.CreateCommentRequestDto;
import com.openclassrooms.mddapi.comment.dto.CreateCommentResponseDto;
import com.openclassrooms.mddapi.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping()
    public ResponseEntity<CreateCommentResponseDto> createCommentToArticle(@RequestBody @Valid CreateCommentRequestDto createCommentRequestDto, Principal principal) {
        commentService.createComment(createCommentRequestDto, principal);
        return ResponseEntity.ok(new CreateCommentResponseDto("Comment created successfully"));
    }
}
