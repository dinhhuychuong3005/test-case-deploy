package com.example.casestudymodule4.service.like;

import com.example.casestudymodule4.model.entity.LikePost;
import com.example.casestudymodule4.service.IGeneralService;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ILikeService extends IGeneralService<LikePost> {
    @Query("select l from LikePost l where l.postId.id=:idP and l.user.id=:idUs")
    public Optional<LikePost> findAllByPostIdAndUserId(Long idP, Long idUs);

    @Query("select l from LikePost  l where l.postId.id=:id")
    public Iterable<LikePost> findAllByPostId(Long id);
}
