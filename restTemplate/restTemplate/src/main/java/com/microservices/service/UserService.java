package com.microservices.service;

import com.microservices.dto.ResponseDto;
import com.microservices.model.User;

public interface UserService {
    User saveUser(User user);

    ResponseDto getUser(Long userId);
}
