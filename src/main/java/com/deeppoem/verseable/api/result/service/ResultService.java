package com.deeppoem.verseable.api.result.service;

import com.deeppoem.verseable.api.result.dto.request.ResultRequestDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseMessageDTO;
import com.deeppoem.verseable.api.result.repository.ResultRepository;
import com.deeppoem.verseable.api.user.repository.UserRepository;
import com.deeppoem.verseable.model.entity.Result;
import com.deeppoem.verseable.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private static final Logger log = LoggerFactory.getLogger(ResultService.class);
    
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;
    
    @Value("${file.upload.path}")
    private String uploadPath;
    
    @Value("${file.upload.access-path}")
    private String accessPath;

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
                try {
                    Files.createDirectories(uploadDir);
                    log.info("업로드 디렉토리 생성 완료: {}", uploadPath);
                } catch (IOException e) {
                    log.error("업로드 디렉토리 생성 실패: {}", e.getMessage());
                    throw new IOException("업로드 디렉토리 생성 실패: " + e.getMessage());
                }
            }

            // 파일 이름 설정
            String originalFileName = multipartFile.getOriginalFilename();
            String ext = originalFileName != null && originalFileName.contains(".") 
                       ? originalFileName.substring(originalFileName.lastIndexOf(".")) 
                       : ".jpg";
            String savedName = UUID.randomUUID().toString().replace("-", "") + ext;
            
            // 파일을 업로드 디렉터리에 저장
            Path targetPath = uploadDir.resolve(savedName);
            
            try {
                multipartFile.transferTo(targetPath.toFile());
                
                // 파일 권한 설정 (읽기 권한 추가)
                File uploadedFile = targetPath.toFile();
                if (!uploadedFile.canRead()) {
                    uploadedFile.setReadable(true, false);
                }
                
                log.info("파일 업로드 성공: {}", targetPath);
            } catch (Exception e) {
                log.error("파일 업로드 실패: {}", e.getMessage());
                e.printStackTrace();
                throw new IOException("파일 업로드 실패: " + e.getMessage());
            }

            // accessPath에서 와일드카드 제거하여 기본 경로 추출 ("/images/**" -> "/images/")
            String accessPathBase = accessPath.replace("**", "");
            
            // 정적 리소스 URL 경로 설정
            result.setResultPath(accessPathBase + savedName);
            
            // 디버깅을 위한 로그 추가
            log.info("저장된 이미지 경로: {}", result.getResultPath());
            log.info("실제 파일 위치: {}", targetPath.toString());

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