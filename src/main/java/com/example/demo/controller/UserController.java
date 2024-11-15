package com.example.demo.controller;

import com.example.demo.dto.request.ApiResponse;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;

    @PostMapping()
    private ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(userCreationRequest));
        return apiResponse;
    }

    @GetMapping()
    private ApiResponse<List<UserResponse>> getUsers() {
        // get thông tin hiện tại đang được Authenticate trong 1 request
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        ApiResponse<List<UserResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUsers());
        return apiResponse;
    }

    @GetMapping("/{userId}")
    private ApiResponse<UserResponse> getUserById(@PathVariable String userId){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(userId));
        return apiResponse;
    }

    @PutMapping("/{userId}")
    private ApiResponse<UserResponse> updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest userUpdateRequest){
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(userId, userUpdateRequest));
        return apiResponse;
    }

    @DeleteMapping("/{userId}")
    private ApiResponse<String> deleteUser(@PathVariable String userId){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        try {
            userService.deleteUserById(userId);
            apiResponse.setResult("user has been deleted");

        } catch (Exception e) {
            apiResponse.setCode(400);
            apiResponse.setResult(e.getMessage());

        }

        return apiResponse;
    }

    @GetMapping("/my-info")
    private ApiResponse<UserResponse> getMyUserInfo(@AuthenticationPrincipal Jwt jwt) {
        // cách 1
        String username = jwt.getSubject();

        // cách 2: dùng SecurityContextHolder lấy ra user hiện tại
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String name = authentication.getName();

        UserResponse user = userService.getUserByUserName(username);

        if (!Objects.isNull(user)) {
            return ApiResponse.<UserResponse>builder().result(user).build();
        }


        return null;
    }
}
