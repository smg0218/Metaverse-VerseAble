package com.deeppoem.verseable.api.user.controller;

import com.deeppoem.verseable.api.user.dto.request.LoginRequestDTO;
import com.deeppoem.verseable.api.user.dto.request.RegistResponseDTO;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import com.deeppoem.verseable.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO,
                        BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(
                    result.getAllErrors().get(0).getDefaultMessage()
            ));
        }

        LoginResponseDTO responseDTO = userService.LogIn(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody RegistResponseDTO responseDTO,
                                    BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }

        String registResult = userService.Regist(responseDTO);
        Map<String,String> responseBody = new HashMap<>();
        if(registResult == null || registResult.isEmpty())
            responseBody.put("message", "가입 성공!");
        else
            responseBody.put("message", registResult);

        return ResponseEntity.ok(responseBody);
    }
}
