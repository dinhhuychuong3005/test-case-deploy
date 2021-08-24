package com.example.casestudymodule4.controller;

import com.example.casestudymodule4.model.entity.LikePost;
import com.example.casestudymodule4.model.entity.Post;
import com.example.casestudymodule4.model.entity.User;
import com.example.casestudymodule4.service.like.ILikeService;
import com.example.casestudymodule4.service.post.IPostService;
import com.example.casestudymodule4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/like")
public class LikeController {
    @Autowired
    private ILikeService likeService;
    @Autowired
    private IPostService postService;
    @Autowired
    private IUserService userService;

    @PostMapping("/create/{idP}/{idUs}")
    public ResponseEntity<Optional<LikePost>> createLike(@PathVariable Long idP, @PathVariable Long idUs) {
        Optional<User> user = userService.findById(idUs);
        Optional<Post> post = postService.findById(idP);
        Optional<LikePost> likePost = likeService.findAllByPostIdAndUserId(idP, idUs);
        if (likePost.isPresent()){
            likeService.remove(likePost.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            long millis = System.currentTimeMillis();
            java.sql.Date date = new java.sql.Date(millis);
           likePost = Optional.of(new LikePost(date, post.get(), user.get()));
            likeService.save(likePost.get());
            return new ResponseEntity<>(likePost,HttpStatus.CREATED);
        }

    }
    @GetMapping("/{idP}")
    public ResponseEntity<Iterable<LikePost>> getAllLikeByPost(@PathVariable Long idP){
        return new ResponseEntity<>(likeService.findAllByPostId(idP),HttpStatus.OK);
    }
}
