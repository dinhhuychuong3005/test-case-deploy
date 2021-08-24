package com.example.casestudymodule4.controller;


import com.example.casestudymodule4.model.entity.Comment;
import com.example.casestudymodule4.model.entity.Post;
import com.example.casestudymodule4.model.entity.User;
import com.example.casestudymodule4.service.Comment.ICommentService;
import com.example.casestudymodule4.service.post.IPostService;
import com.example.casestudymodule4.service.user.IUserService;
import com.fasterxml.jackson.databind.node.TextNode;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/api/Comment")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostService postService;

    @PostMapping("/{idUs}/{idPost}")
    public ResponseEntity<Comment> createComment(@PathVariable Long idUs,@PathVariable Long idPost,@RequestParam String comment) {
        User user= userService.findById(idUs).get();
        Post  post = postService.findById(idPost).get();
        long millis = System.currentTimeMillis();
        java.sql.Date dateComment = new java.sql.Date(millis);

        Comment comment1 = new Comment(comment,dateComment,user,post);
        return new ResponseEntity<>(commentService.save(comment1), HttpStatus.CREATED);
    }

    @GetMapping("/showComment/{idPost}")
    public ResponseEntity<Iterable<Comment>> showListComment(@PathVariable Long idPost) {
        return new ResponseEntity<>(commentService.findAllByPostId(idPost), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long id) {
        Optional<Comment> commentOptional = commentService.findById(id);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentService.remove(id);
        return new ResponseEntity<>(commentOptional.get(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> findCommentById(@PathVariable Long id) {
        Optional<Comment> commentOptional = commentService.findById(id);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(commentOptional.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> saveEditComment(@PathVariable Long id, @RequestBody Comment comment) {
        Optional<Comment> commentOptional = commentService.findById(id);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        comment.setId(commentOptional.get().getId());
        return new ResponseEntity<>(commentService.save(comment),HttpStatus.OK);
    }
}