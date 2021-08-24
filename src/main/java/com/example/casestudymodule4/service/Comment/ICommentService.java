package com.example.casestudymodule4.service.Comment;


import com.example.casestudymodule4.model.entity.Comment;
import com.example.casestudymodule4.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

public interface ICommentService extends IGeneralService<Comment> {
    @Query("select c from Comment c where c.post.id=:id")
    public Iterable<Comment> findAllByPostId(Long id);
}
