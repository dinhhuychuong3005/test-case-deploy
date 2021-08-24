package com.example.casestudymodule4.service.like;

import com.example.casestudymodule4.model.entity.LikePost;
import com.example.casestudymodule4.repository.ILikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService implements ILikeService {
    @Autowired
    private ILikeRepository likeRepository;

    @Override
    public Iterable<LikePost> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Optional<LikePost> findById(Long id) {
        return likeRepository.findById(id);
    }

    @Override
    public LikePost save(LikePost likePost) {
        return likeRepository.save(likePost);
    }

    @Override
    public void remove(Long id) {
        likeRepository.deleteById(id);
    }


    @Override
    public Optional<LikePost> findAllByPostIdAndUserId(Long idP, Long idUs) {
        return likeRepository.findAllByPostIdAndUserId(idP,idUs);
    }

    @Override
    public Iterable<LikePost> findAllByPostId(Long id) {
        return likeRepository.findAllByPostId(id);
    }
}
