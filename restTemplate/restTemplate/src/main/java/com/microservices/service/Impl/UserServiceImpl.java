package com.microservices.service.Impl;

import com.microservices.dto.DepartmentDto;
import com.microservices.dto.ResponseDto;
import com.microservices.dto.UserDto;
import com.microservices.model.User;
import com.microservices.repository.UserRepository;
import com.microservices.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private RestTemplate restTemplate;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public ResponseDto getUser(Long userId) {
        ResponseDto responseDto = new ResponseDto();
        //Optional<User> user = userRepository.findById(userId);
        User user = userRepository.findById(userId).get();
        UserDto userDto = mapToUser(user);
        ResponseEntity<DepartmentDto> forEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + user.getDepartmentId(),
                DepartmentDto.class);
        DepartmentDto departmentDto =forEntity.getBody();
        System.out.println(forEntity.getStatusCode());

        responseDto.setUser(userDto);
        responseDto.setDepartment(departmentDto);
        return responseDto;

    }

    private UserDto mapToUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        return userDto;
    }
}
