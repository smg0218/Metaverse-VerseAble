package com.deeppoem.verseable.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String uploadPath;
    
    @Value("${file.upload.access-path}")
    private String accessPath;

    @PostConstruct
    public void init() {
        try {
            // 업로드 디렉토리가 없으면 생성
            Path dirPath = Paths.get(uploadPath);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
                System.out.println("업로드 디렉토리 생성 완료: " + uploadPath);
            }
            
            // 디렉토리 권한 확인 및 설정
            File dir = dirPath.toFile();
            if (!dir.canRead() || !dir.canWrite()) {
                dir.setReadable(true, false);
                dir.setWritable(true, false);
                System.out.println("업로드 디렉토리 권한 설정 완료");
            }
        } catch (Exception e) {
            System.err.println("업로드 디렉토리 초기화 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 기존 static 리소스 경로 유지
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");

        // 외부 업로드 경로를 정적 리소스로 등록 (accessPath 설정 적용)
        String filePrefix = "file:";
        if (!uploadPath.endsWith("/")) {
            filePrefix += "/";
        }
        
        registry.addResourceHandler(accessPath)
                .addResourceLocations(filePrefix + uploadPath + "/");
        
        // 이전 매핑도 유지 (하위 호환성)
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(filePrefix + uploadPath + "/");
        
        // 디버그 로그 출력
        System.out.println("업로드 경로: " + uploadPath);
        System.out.println("접근 URL 패턴: " + accessPath);
    }
}