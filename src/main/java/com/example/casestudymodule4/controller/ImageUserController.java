package com.example.casestudymodule4.controller;

import com.example.casestudymodule4.model.entity.ImageUser;
import com.example.casestudymodule4.model.entity.User;
import com.example.casestudymodule4.service.imageUse.IImageUseService;
import com.example.casestudymodule4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/avatar")
@CrossOrigin("*")
@PropertySource("classpath:application.properties")
public class ImageUserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IImageUseService iImageUseService;


    @GetMapping("/{id}")
    public ResponseEntity<ImageUser> findImgByIdUs(@PathVariable Long id){

        return new ResponseEntity<>(iImageUseService.findByUserIdAndStatus(id), HttpStatus.OK);
    }
}
