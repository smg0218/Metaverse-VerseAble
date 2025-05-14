package com.deeppoem.verseable.api.user.service;

import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseListDTO;
import com.deeppoem.verseable.api.user.dto.request.LoginRequestDTO;
import com.deeppoem.verseable.api.user.dto.request.RegistRequestDTO;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.Result;
import com.deeppoem.verseable.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public LoginResponseDTO LogIn(LoginRequestDTO requestDTO) {
        Optional<User> findUser = userRepository.findById(requestDTO.getId());

        if(findUser.isPresent()) {
            User user = findUser.get();
            if(encoder.matches(requestDTO.getPw(), user.getPassword())) {
                LoginResponseDTO responseDTO = new LoginResponseDTO();
                responseDTO.setId(user.getUserId());
                return responseDTO;
            }
            else
                return new LoginResponseDTO("ID 혹은 Password가 일치하지 않습니다!");
        } else {
            return new LoginResponseDTO("ID 혹은 Password가 일치하지 않습니다!");
        }
    }

    @Transactional
    public String Regist(RegistRequestDTO responseDTO) {
        Optional<User> findUser = userRepository.findById(responseDTO.getId());

        if(findUser.isPresent()) {
            return "이미 가입된 ID입니다!";
        } else {
            User user = new User(responseDTO, encoder.encode(responseDTO.getPw()));

            userRepository.save(user);

            return null;
        }
    }

    public ResultResponseListDTO getResultList(String userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        if (byUserId.isPresent()) {
            User user = byUserId.get();
            List<Result> resultList = user.getResults();

            List<ResultResponseDTO> resultResponseList = resultList.stream().map(ResultResponseDTO::new).toList();

            return new ResultResponseListDTO(resultResponseList);
        } else {
            return new ResultResponseListDTO("유저를 찾을 수 없습니다!");
        }
    }
}
