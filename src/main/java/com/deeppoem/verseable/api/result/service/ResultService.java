package com.deeppoem.verseable.api.result.service;

import com.deeppoem.verseable.api.result.dto.request.ResultRequestDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseMessageDTO;
import com.deeppoem.verseable.api.result.repository.ResultRepository;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.Result;
import com.deeppoem.verseable.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return null;
    }

    @Transactional
    public ResultResponseMessageDTO addResult(ResultRequestDTO requestDTO, HttpServletRequest request) {
        Optional<User> findUser = userRepository.findById(requestDTO.getId());
        if (findUser.isPresent()) {
            User user = findUser.get();

            Result result = new Result();
            result.setUser(user);
            result.setResultText(requestDTO.getResultData());

            Result saveResult = resultRepository.save(result);

            ResultResponseDTO responseDTO = new ResultResponseDTO(saveResult);

            ResultResponseMessageDTO resultDTO =  new ResultResponseMessageDTO(responseDTO);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    "/api/result/share?resultId=" + saveResult.getResultId();

            resultDTO.setResultShare(url);

            return resultDTO;
        } else {
            return new ResultResponseMessageDTO("유저를 찾지 못했습니다!");
        }
    }
}
