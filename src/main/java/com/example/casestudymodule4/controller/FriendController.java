package com.example.casestudymodule4.controller;

import com.example.casestudymodule4.model.entity.Friend;
import com.example.casestudymodule4.model.entity.User;
import com.example.casestudymodule4.service.friend.IFriendService;
import com.example.casestudymodule4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@PropertySource("classpath:application.properties")
@RestController
@CrossOrigin("*")
@RequestMapping("/api/friend")
public class FriendController {
    @Autowired
    private IFriendService friendService;
    @Autowired
    private IUserService userService;

//    @GetMapping("/listFriend")
//    public ResponseEntity<Iterable<Friend>> getAll() {
//        Iterable<Friend> listFriend = friendService.findAll();
//        return new ResponseEntity<>(listFriend, HttpStatus.OK);
//    }

    @GetMapping("/allMutualFriend/{idUser1}/{idUser2}")
    public ResponseEntity<List<User>> getMutualFriend(@PathVariable Long idUser1, @PathVariable Long idUser2) {
        List<User> listMutualFriend = new ArrayList<>();
        List<User> listUserFriend1 = showListFriend(idUser1);
        List<User> listUserFriend2 = showListFriend(idUser2);

        for (User user1 : listUserFriend1) {

            for (User user2 : listUserFriend2) {

                if (user1.getId() == user2.getId()) {
                    listMutualFriend.add(user1);
                }
            }
        }

        return new ResponseEntity<>(listMutualFriend, HttpStatus.OK);
    }

    @GetMapping("/listUser/{idUser}")
    public ResponseEntity<Iterable<User>> listUser(@PathVariable Long idUser) {
        List<User> userUnFriend = new ArrayList<>();
        List<User> users = (List<User>) userService.findAll();
        List<User> users1 = getListUserFriend1(idUser);
        List<User> users2 = getListUserFriendByFr(idUser);
        for (int i = 0; i < users2.size(); i++) {
            users1.add(users2.get(i));
        }
        for (User user : users) {
            if (!users1.contains(user) && (user.getId() != idUser)) {
                userUnFriend.add(user);
            }
        }
        return new ResponseEntity<>(userUnFriend, HttpStatus.OK);
    }

    @GetMapping("/listFriendUser/{id}")
    public ResponseEntity<List<User>> listFriendUser(@PathVariable Long id) {
        List<User> users = getListUserFriend(id);
        List<User> users1 = getListUserFriendByFr(id);
        for (int i = 0; i < users.size(); i++) {
            users1.add(users.get(i));
        }
        return new ResponseEntity<>(users1, HttpStatus.OK);
    }

    public List<User> showListFriend(Long id) {
        List<User> users = getListUserFriend(id);
        List<User> users1 = getListUserFriendByFr(id);
        for (int i = 0; i < users.size(); i++) {
            users1.add(users.get(i));
        }
        return users1;
    }



    public List<User> getListUserFriend1(Long idUser) {
        List<Friend> listFriend = friendService.findAllFriendById(idUser);
        List<com.example.casestudymodule4.model.entity.User> listUser = new ArrayList<>();
        for (Friend friendEntity : listFriend) {
            com.example.casestudymodule4.model.entity.User user = userService.findById(friendEntity.getIdFriendOfUser()).get();
            listUser.add(user);
        }
        return listUser;
    }

    public List<User> getListUserFriend(Long idUser) {
        List<Friend> listFriend = friendService.findAllFriendById(idUser);
        List<com.example.casestudymodule4.model.entity.User> listUser = new ArrayList<>();
        for (Friend friendEntity : listFriend) {

            if (friendEntity.getStatus() == 1) {
                com.example.casestudymodule4.model.entity.User user = userService.findById(friendEntity.getIdFriendOfUser()).get();
                listUser.add(user);
            }
        }
        return listUser;
    }

    public List<User> getListUserFriendByFr(Long idFr) {
        List<Friend> listFriend = friendService.findAllFriendByIdFr(idFr);
        List<com.example.casestudymodule4.model.entity.User> listUser = new ArrayList<>();
        for (Friend friendEntity : listFriend) {

            if (friendEntity.getStatus() == 1) {
                com.example.casestudymodule4.model.entity.User user = userService.findById(friendEntity.getUser().getId()).get();
                listUser.add(user);
            }
        }
        return listUser;
    }

    @GetMapping("/addFriend/{idUs}/{idFr}")
    public ResponseEntity<Friend> addFriend(@PathVariable Long idUs, @PathVariable Long idFr) {
        User user = userService.findById(idUs).get();
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Friend friend = new Friend(date, 2, user, idFr);
        friendService.save(friend);
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    //    @PostMapping("/accept/{idUs}/{idFr}")
//    public ResponseEntity<Friend> acceptFriend(@PathVariable Long idUs,@PathVariable Long idFr) {
//        Friend friend = friendService.findById(id).get();
//        friend.setStatus(1);
//        friendService.save(friend);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    @GetMapping("/add/{idFr}/{idU}")
    public ResponseEntity<Friend> add(@PathVariable Long idFr, @PathVariable Long idU) {
        Friend friend = friendService.findFriendByUserIdAndIdFriendOfUser(idFr, idU);
        friend.setStatus(1);
        friendService.save(friend);
        return new ResponseEntity<>(friend, HttpStatus.OK);
    }

    @DeleteMapping("/deleteFriend/{idU1}/{idU2}")
    public ResponseEntity<Friend> deleteFriend(@PathVariable Long idU1, @PathVariable Long idU2) {
        Friend friend = friendService.findFriendByIdUserAndIdFriendOfUser1(idU1, idU2);
        Friend friend1 = friendService.findFriendByIdUserAndIdFriendOfUser1(idU2, idU1);

        if (friend == null) {
            friendService.remove(friend1.getId());
        } else if (friend1 == null) {
            friendService.remove(friend.getId());
        } else {
            friendService.remove(friend.getId());
            friendService.remove(friend1.getId());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/listAdd/{idFr}")
    public ResponseEntity<List<Friend>> getListFriendAdd(@PathVariable Long idFr) {
        List<Friend> list = friendService.findAllFriendAddById(idFr);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
