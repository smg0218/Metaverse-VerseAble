package com.deeppoem.verseable.api.result.service;

import com.deeppoem.verseable.api.result.dto.request.ResultRequestDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.repository.ResultRepository;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.Result;
import com.deeppoem.verseable.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResultService {
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public ResultService(UserRepository userRepository, ResultRepository resultRepository) {
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    public ResultResponseDTO getResult(Long resultId) {
        Optional<Result> result = resultRepository.findById(resultId);

        if (result.isPresent()) {
            return new ResultResponseDTO(result.get());
        } else
            throw new IllegalArgumentException("존재하지 않는 ID값 입니다!");
    }

    public String addResult(ResultRequestDTO requestDTO) {
        Optional<User> findUser = userRepository.findById(requestDTO.getId());
        if (findUser.isPresent()) {
            User user = findUser.get();

            Result result = new Result();
            result.setUser(user);
            result.setResultText(requestDTO.getResultData());

            resultRepository.save(result);

            return null;
        } else {
            return "유저를 찾지 못했습니다!";
        }
    }
}
