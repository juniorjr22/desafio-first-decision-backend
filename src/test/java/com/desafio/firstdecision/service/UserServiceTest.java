package com.desafio.firstdecision.service;

import com.desafio.firstdecision.dto.UserDTORequest;
import com.desafio.firstdecision.dto.UserDTOResponse;
import com.desafio.firstdecision.entity.User;
import com.desafio.firstdecision.exception.PasswordException;
import com.desafio.firstdecision.mapper.UserMapper;
import com.desafio.firstdecision.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserRepository userRepository2;

    @Test
    public void testSaveUser() throws PasswordException {
        UserDTORequest userDTORequest = buildValidUserDTO();

        User savedUser = buildValidUserSaved(userDTORequest);
        UserDTOResponse userDTOResponse = buildValidUserResponseDTO(userDTORequest);

        when(userMapper.convertDTOToEntity(userDTORequest)).thenReturn(savedUser);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.convertEntityToDTO(any(User.class))).thenReturn(userDTOResponse);

        UserDTOResponse response = userService.saveUser(userDTORequest);

        assertEquals(userDTORequest.getName(), response.getName());
        assertEquals(userDTORequest.getEmail(), response.getEmail());
        assertEquals(1L, response.getId());
    }

    @Test
    public void testInvalidPassword() throws PasswordException {
        UserDTORequest userDTORequest = buildInvalidPasswordUserDTO();

        assertThrows(PasswordException.class, () -> userService.saveUser(userDTORequest));
    }

    @Test
    void testSaveUserAndVerifyInDatabase() {
        UserDTORequest userDTORequest = buildValidUserDTO();

        when(userMapper.convertDTOToEntity(userDTORequest)).thenReturn(buildValidUserSaved(userDTORequest));
        userRepository2.saveAndFlush(userMapper.convertDTOToEntity(userDTORequest));

        User savedUser = userRepository2.findById(1L).orElse(null);
        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("Junior Pereira", savedUser.getName());
        assertEquals("junior@gmail.com", savedUser.getEmail());
    }

    private static User buildValidUserSaved(UserDTORequest userDTORequest) {
        User savedUser = User.builder()
                .name(userDTORequest.getName())
                .email(userDTORequest.getEmail())
                .password(userDTORequest.getPassword())
                .build();
        return savedUser;
    }

    private static UserDTOResponse buildValidUserResponseDTO(UserDTORequest userDTORequest) {
        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id(1L)
                .name(userDTORequest.getName())
                .email(userDTORequest.getEmail())
                .build();
        return userDTOResponse;
    }

    private UserDTORequest buildValidUserDTO() {
        return UserDTORequest.builder()
                .name("Junior Pereira")
                .email("junior@gmail.com")
                .password("password123")
                .passwordConfirmation("password123")
                .build();
    }

    private UserDTORequest buildInvalidPasswordUserDTO() {
        return UserDTORequest.builder()
                .name("Junior Pereira")
                .email("junior@gmail.com")
                .password("password123")
                .passwordConfirmation("password")
                .build();
    }

}