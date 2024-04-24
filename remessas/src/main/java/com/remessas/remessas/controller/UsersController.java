package com.remessas.remessas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remessas.remessas.service.UsersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UsersController {

    final UsersService usersService;

    @GetMapping(value = "/{id}")
    public Long findId(@PathVariable(value = "id") Long id) {
        return usersService.findUser(id);
    }
}
