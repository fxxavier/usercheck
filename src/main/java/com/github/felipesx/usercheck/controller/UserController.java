package com.github.felipesx.usercheck.controller;

import com.github.felipesx.usercheck.dto.VoteStatusResponse;
import com.github.felipesx.usercheck.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<VoteStatusResponse> checkVoteStatus(@PathVariable String cpf) {
        VoteStatusResponse response = userService.checkVoteStatus(cpf);
        return ResponseEntity.ok(response);
    }
}
