package com.example.javademo.part.com.controller;

import com.example.javademo.entity.UserMst;
import com.example.javademo.repo.jpa.UserRepo;
import com.example.javademo.fwk.base.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    @Autowired
    UserRepo userRepo;
    // CrudRepository 내 save()는 insert / update를 확인하기 위해 select를 먼저 수행한다.

    @GetMapping
    public ArrayList<UserMst> getAll() {
        Iterable<UserMst> it = userRepo.findAll();
        Iterator<UserMst> iterator = it.iterator();

        ArrayList<UserMst> list = new ArrayList<>();
        while(iterator.hasNext()){
            list.add(iterator.next());
        }

        return list;
    }

    @PostMapping
    public UserMst createUser(@RequestBody String name) {
        UserMst inUser = new UserMst();
        inUser.setName(name);

        UserMst result = userRepo.save(inUser);

        return result;
    }

    @GetMapping("/{id}")
    public UserMst getUser(@PathVariable("id") Integer id) {
        Optional<UserMst> byId = userRepo.findById(id);
        UserMst result = byId.get();

        return result;
    }

    @PutMapping("/{id}")
    public UserMst updateUser(@PathVariable("id") Integer id, @RequestBody UserMst updateUser) throws Exception {

        Optional<UserMst> byId = userRepo.findById(id);
        if(!byId.isPresent()) {
            throw new Exception("...");
        }

        UserMst existUser = byId.get();

        existUser.setName(updateUser.getName());

        userRepo.save(existUser);

        return existUser;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) throws Exception {
        Optional<UserMst> byId = userRepo.findById(id);
        if(!byId.isPresent()) {
            throw new Exception("...");
        }

        userRepo.deleteById(id);
    }

}