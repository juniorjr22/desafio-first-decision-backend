package com.desafio.firstdecision.mapper;

import com.desafio.firstdecision.dto.UserDTOResponse;
import com.desafio.firstdecision.entity.User;
import com.desafio.firstdecision.dto.UserDTORequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Autowired
    protected UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertDTOToEntity(UserDTORequest userDTORequest) {
        return modelMapper.map(userDTORequest, User.class);
    }

    public UserDTOResponse convertEntityToDTO(User user) {
        return modelMapper.map(user, UserDTOResponse.class);
    }
}
