package com.desafio.firstdecision.service;

import com.desafio.firstdecision.dto.UserDTORequest;
import com.desafio.firstdecision.dto.UserDTOResponse;
import com.desafio.firstdecision.entity.User;
import com.desafio.firstdecision.exception.UserNotFoundException;
import com.desafio.firstdecision.mapper.UserMapper;
import com.desafio.firstdecision.repository.UserRepository;
import com.desafio.firstdecision.util.ConstantUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserMapper userMapper;
    private UserRepository userRepository;

    protected UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Page<UserDTOResponse> getUsers(Pageable pageable) {
        return this.userRepository.findAll(pageable).map(this.userMapper::convertEntityToDTO);
    }

    @Transactional
    public UserDTOResponse saveUser(UserDTORequest userDTORequest) {
        userDTORequest.validatePassword();
        User user = this.userMapper.convertDTOToEntity(userDTORequest);
        return this.userMapper.convertEntityToDTO(userRepository.save(user));
    }

    @Transactional
    public void updateUser(Long id, UserDTORequest userDTORequest) {
        userDTORequest.validatePassword();

        User userSaved = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ConstantUtil.MSG_USER_NOT_FOUND, id)));

        userSaved.setName(userDTORequest.getName());
        userSaved.setEmail(userDTORequest.getEmail());
        userSaved.setPassword(userDTORequest.getPassword());

        this.userMapper.convertEntityToDTO(userRepository.save(userSaved));
    }


    @Transactional
    public void deleteUser(Long id) {
        User userSaved = this.userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(ConstantUtil.MSG_USER_NOT_FOUND, id)));
        userRepository.deleteById(userSaved.getId());
    }
}
