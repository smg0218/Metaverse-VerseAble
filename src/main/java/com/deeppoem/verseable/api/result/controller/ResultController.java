package com.deeppoem.verseable.api.result.controller;

import com.deeppoem.verseable.api.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/result")
public class ResultController {
    private final UserService userService;

    @Autowired
    public ResultController(UserService userService) {
        this.userService = userService;
    }
}
