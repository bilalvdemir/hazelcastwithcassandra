package com.example.bilal.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.bilal.exception.AlreadyExistException;
import com.example.bilal.exception.NotFoundException;
import com.example.bilal.model.dao.UserDAO;
import com.example.bilal.model.dto.UserDto;
import com.example.bilal.repository.UserRepository;
import com.example.bilal.util.Utils;

@Service
public class UserManager implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserManager.class);

    private UserRepository      userRepository;

    private ModelMapper         modelMapper;

    public UserManager(UserRepository userRepository, ModelMapper modelMapper) {
        super();
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDAO saveUser(UserDAO user) throws AlreadyExistException {
        LOG.debug("[saveUser] User Manager saveUser procces started. Params: user ->{}", user);
        UserDAO response = userRepository.findByUsername(user.getUsername());
        if (response == null) {

            response = userRepository.save(user);
        } else {
            throw new AlreadyExistException(Utils.getUsernameAlreadyExistException(user.getUsername()));
        }
        LOG.debug("[saveUser] User Manager saveUser procces finished. response ->{}", response);
        return response;
    }

    @Override
    public UserDAO getUser(String userName) throws NotFoundException {
        LOG.debug("[getUser] User Manager getUser procces started. Params: userName ->{}", userName);
        UserDAO response = userRepository.findByUsername(userName);
        if (response == null) {
            throw new NotFoundException(Utils.getUserNotFoundException(userName));
        }
        LOG.debug("[getUser] User Manager getUser procces finished. response ->{}", response);
        return response;
    }

    @Override
    public UserDAO updateUser(UserDAO user) throws NotFoundException {
        LOG.debug("[updateUser] User Manager updateUser procces started. Params: user ->{}", user);
        UserDAO response = userRepository.findByUsername(user.getUsername());
        if (response == null) {
            throw new NotFoundException(Utils.getUserNotFoundException(user.getUsername()));
        }
        response = userRepository.save(user);
        LOG.debug("[updateUser] User Manager updateUser procces finished. response ->{}", response);
        return response;
    }

    @Override
    public UserDAO deleteUser(String userName) throws NotFoundException {
        LOG.debug("[deleteUser] User Manager deleteUser procces started. Params: userName ->{}", userName);
        UserDAO response = userRepository.findByUsername(userName);
        if (response == null) {
            throw new NotFoundException(Utils.getUserNotFoundException(userName));
        }
        response = userRepository.deleteByUsername(userName);
        LOG.debug("[deleteUser] User Manager deleteUser procces finished. response ->{}", response);
        return response;
    }

    @Override
    public List<UserDto> getAllUser() {
        LOG.debug("[getAllUser] User Manager gelAllUser procces started.");
        Iterable<UserDAO> useDAOList = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        useDAOList.forEach(dao -> {
            UserDto data = modelMapper.map(dao, UserDto.class);
            userDtoList.add(data);
        });
        LOG.debug("[getAllUser] User Manager gelAllUser procces finished. useDAOList ->{}", useDAOList);
        return userDtoList;
    }

}
