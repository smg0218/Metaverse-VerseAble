package com.deeppoem.verseable.api.user.controller;

import com.deeppoem.verseable.api.user.dto.request.LoginRequestDTO;
import com.deeppoem.verseable.api.user.dto.request.RegistRequestDTO;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import com.deeppoem.verseable.api.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO requestDTO,
                        BindingResult result) {
        log.info("/api/user/login : POST");
        log.info("requestDTO : {}", requestDTO);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(
                    result.getAllErrors().get(0).getDefaultMessage()
            ));
        }

        LoginResponseDTO responseDTO = userService.LogIn(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody RegistRequestDTO requestDTO,
                                    BindingResult result) {
        log.info("/api/user/regist : POST");
        log.info("requestDTO : {}", requestDTO);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getAllErrors().get(0).getDefaultMessage());
        }

        String registResult = userService.Regist(requestDTO);
        Map<String,String> responseBody = new HashMap<>();
        if(registResult == null || registResult.isEmpty())
            responseBody.put("message", "가입 성공!");
        else
            responseBody.put("message", registResult);

        return ResponseEntity.ok(responseBody);
    }
}
