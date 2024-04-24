package com.remessas.remessas.service;

import org.springframework.stereotype.Service;

import com.remessas.remessas.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

    final UsersRepository usersRepository;

    public Long findUser(Long id) {
        var user = usersRepository.findById(id).get();
        return user.getId();
    }

}
