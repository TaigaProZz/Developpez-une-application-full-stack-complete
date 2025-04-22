package com.openclassrooms.mddapi.article.repository;

import com.openclassrooms.mddapi.article.model.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
}
