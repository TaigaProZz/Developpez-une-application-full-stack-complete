package com.openclassrooms.mddapi.article.mapper;

import com.openclassrooms.mddapi.article.dto.ArticleDto;
import com.openclassrooms.mddapi.article.dto.ArticleWithCommentsDto;
import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.comment.dto.CommentDto;
import com.openclassrooms.mddapi.comment.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

  ArticleDto articleToArticleDto(Article article);
  Article articleDtoToArticle(ArticleDto articleDto);

  @Mappings({
          @Mapping(target = "id", source = "article.id"),
          @Mapping(target = "title", source = "article.title"),
          @Mapping(target = "content", source = "article.content"),
          @Mapping(target = "authorName", source = "article.author.username"),
          @Mapping(target = "themeName", source = "article.theme.title"),
          @Mapping(target = "createdAt", source = "article.createdAt"),
          @Mapping(target = "comments", source = "comments")
  })
  ArticleWithCommentsDto articleToArticleWithCommentsDto(Article article, List<CommentDto> comments);

  CommentDto commentToCommentDto(Comment comment);

  List<CommentDto> commentsToCommentDtos(List<Comment> comments);

  Article articleDtoToArticle(ArticleWithCommentsDto articleDto);

  Comment commentDtoToComment(CommentDto commentDto);
}
