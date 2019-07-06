package com.example.bilal.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bilal.exception.AlreadyExistException;
import com.example.bilal.exception.NotFoundException;
import com.example.bilal.model.dao.UserDAO;
import com.example.bilal.model.dto.UserDto;
import com.example.bilal.service.UserManager;
import com.example.bilal.service.UserService;

@RestController
@RequestMapping("/{hazelcastWithCassandra.path}/{hazelcastWithCassandra.api.version}")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserManager.class);

    UserService                 userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public List<UserDto> handleGetAllUser() {
        LOG.debug("[handleGetAllUser] User Controller Retrieve All User procces started.");
        List<UserDto> result = userService.getAllUser();
        LOG.debug("[handleGetAllUser] User Manager Retrieve All User procces finished.", result);
        return result;
    }

    @GetMapping(value = "/user/{userId}")
    public UserDAO handleGetUser(@PathVariable("userId") String userId) throws NotFoundException {
        LOG.debug("[handleGetUser] User Controller Retrieve User procces started. Params: userId ->{}", userId);
        UserDAO result = userService.getUser(userId);
        LOG.debug("[handleGetUser] User Manager Retrieve User procces finished. Params: result ->{}", result);
        return result;
    }

    @PostMapping(value = "/user")
    public UserDAO handleSaveUser(@Valid @RequestBody UserDAO user) throws AlreadyExistException {
        LOG.debug("[handleSaveUser] User Controller Save User procces started. Params: user ->{}", user);
        UserDAO result = userService.saveUser(user);
        LOG.debug("[handleSaveUser] User Manager Save User procces finished. Params: result ->{}", result);
        return result;
    }

    @PutMapping(value = "/user")
    public UserDAO handleUpdateUser(@Valid @RequestBody UserDAO user) throws NotFoundException {
        LOG.debug("[handleUpdateUser] User Controller Update User procces started. Params: user ->{}", user);
        UserDAO result = userService.updateUser(user);
        LOG.debug("[handleUpdateUser] User Manager Update User procces finished. Params: result ->{}", result);
        return result;
    }

    @DeleteMapping(value = "/user/{userId}")
    public UserDAO handleDeleteUser(@PathVariable("userId") String userName) throws NotFoundException {
        LOG.debug("[handleDeleteUser] User Controller Update User procces started. Params: userName ->{}", userName);
        UserDAO result = userService.deleteUser(userName);
        LOG.debug("[handleDeleteUser] User Manager Update User procces finished. Params: result ->{}", result);
        return result;
    }
}
