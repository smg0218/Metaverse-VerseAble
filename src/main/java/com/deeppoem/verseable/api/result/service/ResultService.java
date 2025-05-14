package com.deeppoem.verseable.api.result.service;

import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseMessageDTO;
import com.deeppoem.verseable.api.result.repository.ResultRepository;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.Result;
import com.deeppoem.verseable.model.entity.User;
import com.deeppoem.verseable.util.FileUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ResultService {
    @Value("${upload.path}")
    private String rootFilePath;

    private final UserRepository userRepository;
    private final ResultRepository resultRepository;
    
    @Value("${file.upload.path:${user.home}/verseable-uploads}")
    private String uploadPath;

    @Autowired
    public ResultService(UserRepository userRepository, ResultRepository resultRepository) {
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    public ResultResponseMessageDTO getResult(Long resultId, HttpServletRequest request) {
        Optional<Result> result = resultRepository.findById(resultId);

        if (result.isPresent()) {
            ResultResponseDTO responseDTO = new ResultResponseDTO(result.get());

            ResultResponseMessageDTO resultDTO = new ResultResponseMessageDTO(responseDTO);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                    "/api/result/share?resultId=" + result.get().getResultId();

            resultDTO.getResultResponse().setResultPath(url);

            resultDTO.setImagePath(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + result.get().getResultPath());

            return resultDTO;

        } else
            return new ResultResponseMessageDTO("올바른 주소를 입력해주세요!");
    }

    @Transactional
    public ResultResponseMessageDTO addResult(String id, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            User user = findUser.get();

            Result result = new Result();
            result.setUser(user);

            String resultPath = FileUtil.uploadFile(multipartFile, rootFilePath);

            result.setResultPath("/local/" + resultPath);

            Result saveResult = resultRepository.save(result);

            ResultResponseDTO responseDTO = new ResultResponseDTO(saveResult);

            ResultResponseMessageDTO resultDTO = new ResultResponseMessageDTO(responseDTO);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/"
                    + "api/result/share?resultId=" + saveResult.getResultId();

            resultDTO.getResultResponse().setResultPath(url);

            return resultDTO;
        } else {
            return new ResultResponseMessageDTO("유저를 찾지 못했습니다!");
        }
    }
}