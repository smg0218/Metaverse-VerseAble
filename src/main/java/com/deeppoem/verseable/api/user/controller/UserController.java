package com.deeppoem.verseable.api.user.controller;

import com.deeppoem.verseable.api.user.dto.request.LoginRequestDTO;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import com.deeppoem.verseable.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO,
                        BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(
                    result.getAllErrors().get(0).getDefaultMessage()
            ));
        }

        LoginResponseDTO responseDTO = userService.LogIn(requestDTO);

    }
}
