package com.example.bilal.service;

import java.util.List;

import com.example.bilal.exception.AlreadyExistException;
import com.example.bilal.exception.NotFoundException;
import com.example.bilal.model.dao.UserDAO;
import com.example.bilal.model.dto.UserDto;

public interface UserService {

    public UserDAO saveUser(UserDAO user) throws AlreadyExistException;

    public UserDAO getUser(String userId) throws NotFoundException;

    public UserDAO updateUser(UserDAO user) throws NotFoundException;

    public UserDAO deleteUser(String userName) throws NotFoundException;

    public List<UserDto> getAllUser();

}
