package com.example.bilal.model.util.mapper.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.bilal.model.dao.UserDAO;
import com.example.bilal.model.dto.UserDto;
import com.example.bilal.model.util.mapper.provider.UserToUserDtoProvider;

@Configuration
public class ModelMapperConfig {

    @Autowired
    private UserToUserDtoProvider userToUserDtoProvider;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setImplicitMappingEnabled(false);
        modelMapper.createTypeMap(UserDAO.class, UserDto.class).setProvider(userToUserDtoProvider);
        return modelMapper;
    }

}
