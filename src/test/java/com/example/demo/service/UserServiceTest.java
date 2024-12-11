package com.example.demo.service;

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/test.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private User user;

    @BeforeEach
    void initData() {
        LocalDate dob = LocalDate.of(1990,3,20);

        userCreationRequest = UserCreationRequest.builder()
                .username("quang")
                .firstName("Quang")
                .lastName("Vo")
                .password("123456789")
                .dob(dob)
                .build();

        userResponse = UserResponse.builder()
                .id("123124567")
                .username("quang")
                .firstName("Quang")
                .lastName("Vo")
                .dob(dob)
                .build();

        user = User.builder()
                .id("123124567")
                .username("quang")
                .firstName("Quang")
                .lastName("Vo")
                .dob(dob)
                .build();
    }

    @Test
    void createUser_validRequest_success() {
        // GIVEN

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN

        UserResponse response = userService.createUser(userCreationRequest);

        // THEN

        Assertions.assertThat(response.getUsername()).isEqualTo("quang");

    }

    @Test
    void createUser_userExisted_fail() {
        // GIVEN

        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // WHEN
       AppException exception = assertThrows(AppException.class, () -> {
           userService.createUser(userCreationRequest);
       });

       Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);

    }

    @Test
    @WithMockUser(username = "quang")
    void getMyInfo_valid_success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        UserResponse response = userService.getMyUserInfo();

        Assertions.assertThat(response.getUsername()).isEqualTo("quang");
        Assertions.assertThat(response.getId()).isEqualTo("123124567");
    }

    @Test
    @WithMockUser(username = "quang")
    void getMyInfo_userNotFound_error() {
        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.ofNullable(null));
        // WHEN
        var exception = assertThrows(AppException.class,
                () -> userService.getMyUserInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1005);
        Assertions.assertThat(exception.getMessage()).isEqualTo("User not existed");
    }
}
