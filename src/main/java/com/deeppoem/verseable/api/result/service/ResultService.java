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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultService {
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

            resultDTO.setResultShare(url);

            return resultDTO;

        } else
            return null;
    }

    @Transactional
    public ResultResponseMessageDTO addResult(String id, MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            User user = findUser.get();

            Result result = new Result();
            result.setUser(user);
            
            // 외부 업로드 디렉터리 생성
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String originalFileName = multipartFile.getOriginalFilename();
            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
            String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
            
            // 파일을 업로드 디렉터리에 저장
            Path targetPath = uploadDir.resolve(savedName);
            
            try {
                multipartFile.transferTo(targetPath.toFile());
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("파일 업로드 실패: " + e.getMessage());
            }

            // 정적 리소스 URL 경로 설정 (/uploads/*)
            result.setResultPath("/uploads/" + savedName);

            Result saveResult = resultRepository.save(result);

            ResultResponseDTO responseDTO = new ResultResponseDTO(saveResult);

            ResultResponseMessageDTO resultDTO = new ResultResponseMessageDTO(responseDTO);
            String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/"
                    + "api/result/share?resultId=" + saveResult.getResultId();

            resultDTO.setResultShare(url);

            return resultDTO;
        } else {
            return new ResultResponseMessageDTO("유저를 찾지 못했습니다!");
        }
    }
}