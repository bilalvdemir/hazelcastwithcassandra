package com.example.bilal.repository;

import java.util.List;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

import com.example.bilal.model.dao.UserDAO;

public interface UserRepository extends HazelcastRepository<UserDAO, String> {

    public UserDAO findById(String userId);

    public List<UserDAO> findAll();

    public UserDAO deleteByUsername(String userName);

    public UserDAO findByUsername(String userName);
}
