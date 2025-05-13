package com.deeppoem.verseable.api.user.service;

import com.deeppoem.verseable.api.user.dto.request.LoginRequestDTO;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDTO LogIn(LoginRequestDTO requestDTO) {
        Optional<User> findUser = userRepository.findById(requestDTO.getId());

        if(findUser.isPresent()) {
            User user = findUser.get();
            if(user.getPassword().equals(requestDTO.getPw()))
                return new LoginResponseDTO(user.getUserId(), user.getUserName());
            else
                return new LoginResponseDTO("ID 혹은 Password가 일치하지 않습니다!");
        } else {
            return new LoginResponseDTO("ID 혹은 Password가 일치하지 않습니다!");
        }
    }
}
