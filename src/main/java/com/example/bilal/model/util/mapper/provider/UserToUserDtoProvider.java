package com.example.bilal.model.util.mapper.provider;

import org.modelmapper.Provider;
import org.springframework.stereotype.Component;

import com.example.bilal.model.dao.UserDAO;
import com.example.bilal.model.dto.UserDto;
import com.example.bilal.model.dto.UserDtoBuilder;

@Component
public class UserToUserDtoProvider implements Provider<UserDto> {

    @Override
    public UserDto get(ProvisionRequest<UserDto> request) {
        UserDAO sourceUser = UserDAO.class.cast(request.getSource());
        UserDtoBuilder userDtoBuilder = new UserDtoBuilder();
        if (sourceUser.getId() == null || sourceUser.getEmail() == null || sourceUser.getLastname() == null || sourceUser.getName() == null || sourceUser.getUsername() == null) {
            return new UserDto();
        }
        userDtoBuilder.setId(sourceUser.getId()).setEmail(sourceUser.getEmail()).setLastname(sourceUser.getLastname()).setName(sourceUser.getName()).setUsername(sourceUser.getUsername());
        UserDto userDto = userDtoBuilder.build();
        return userDto;
    }

}
