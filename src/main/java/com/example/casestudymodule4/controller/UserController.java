package com.example.casestudymodule4.controller;

import com.example.casestudymodule4.model.UploadPost;
import com.example.casestudymodule4.model.entity.*;
import com.example.casestudymodule4.service.imageUse.IImageUseService;
import com.example.casestudymodule4.service.user.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")

public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IImageUseService iImageUseService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PutMapping("/editPassword/{id}")
    public ResponseEntity<User> updatePassword(@PathVariable Long id,@RequestBody User user){
        Optional<User> user1 = userService.findById(id);
        user.setId(user1.get().getId());
        user.setUsername(user1.get().getUsername());
        user.setAddress(user1.get().getAddress());
        user.setImgUrl((List<ImageUser>) user1.get().getImgUrl());
        user.setEmail(user1.get().getEmail());
        user.setDateOfBirth(user1.get().getDateOfBirth());
        user.setFullName(user1.get().getFullName());
        user.setGender(user1.get().getGender());
        user.setNumberPhone(user1.get().getNumberPhone());
        user.setRoles(user1.get().getRoles());
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }
    @GetMapping("/search/{value}")
    public ResponseEntity<Iterable<User>> searchUser(@PathVariable String value){
        List<User> users = (List<User>) userService.findAllByFullNameContaining(value);
        if (users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
    }
    private static String UPLOAD_DIR = "/img";
    @PutMapping("/updateImg/{id}")
    public ResponseEntity<?> multiUploadFileModel(@PathVariable Long id,@ModelAttribute UploadImageUser form) {
        Optional<User> user = userService.findById(id);
//        ImageUser imageUser = iImageUseService.findByUserIdAndStatus(id);
//        imageUser.setStatus(3);
        logger.debug("Multiple file upload! With UploadModel");
        String result = null;
        try {

            result = this.saveUploadedFiles(form.getFiles());


            MultipartFile[] files = form.getFiles();
            List<ImageUser> imgs = new ArrayList<>();

            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                ImageUser image = new ImageUser(1, UPLOAD_DIR + "/" + file.getOriginalFilename(),user.get());
                imgs.add(image);
                iImageUseService.save(image);
            }
            user.get().setImgUrl(imgs);

            userService.save(user.get());

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<String>("Uploaded to: <br/>" + result, HttpStatus.OK);

    }

    private String saveUploadedFiles(MultipartFile[] files) throws IOException {

        // Make sure directory exists!
        File uploadDir = new File(UPLOAD_DIR);
        uploadDir.mkdirs();

        StringBuilder sb = new StringBuilder();

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = UPLOAD_DIR + "/" + file.getOriginalFilename();

            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);

            sb.append(uploadFilePath).append("<br/>");
        }
        return sb.toString();
    }
}
