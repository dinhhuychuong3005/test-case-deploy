package com.example.casestudymodule4.service.post;

import com.example.casestudymodule4.model.entity.Post;
import com.example.casestudymodule4.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

public interface IPostService extends IGeneralService<Post> {
    @Query("select p from Post p where p.user.id=:id")
    public Iterable<Post> findAllByUserId(Long id);

    @Query("select p from Post p where p.user.id=:id and p.status =1")
    public Iterable<Post> findAllByUserIdAndByStatus(Long id);
}
