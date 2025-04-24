package com.openclassrooms.mddapi.article.repository;

import com.openclassrooms.mddapi.article.model.Article;
import com.openclassrooms.mddapi.theme.model.Theme;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
  @Query("""
        SELECT a FROM Article a
        WHERE a.theme IN (
            SELECT t FROM User u
            JOIN u.themes t
            WHERE u.id = :userId
        )
        ORDER BY a.createdAt DESC
    """)
  List<Article> findArticlesByUserSubscribedThemes(@Param("userId") Long userId);
}
