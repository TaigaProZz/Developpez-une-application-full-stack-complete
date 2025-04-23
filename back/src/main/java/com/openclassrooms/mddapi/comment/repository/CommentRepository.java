package com.openclassrooms.mddapi.comment.repository;

import com.openclassrooms.mddapi.comment.model.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
}
