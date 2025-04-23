package com.openclassrooms.mddapi.comment.repository;

import com.openclassrooms.mddapi.comment.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByArticleId(Long articleId);
}
