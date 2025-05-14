package com.deeppoem.verseable.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtil {
    public static String uploadFile(MultipartFile file, String rootFilePath) {
        // 유저명과 유저아이디를 원본 파일에 추가하여 저장
        // 업로드 파일명 예시 : /file/upload/테스터_tester123_asdf.jpg
        String originalFileName = file.getOriginalFilename();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString().replace("-", "") + ext;

        // 파일을 날짜별로 관리하기 위해 날짜별 폴더를 생성
        // String newFilePath = makeDateFormatDirectory(rootFilePath);
        // 파일의 풀 경로를 생성
        // String fullFilePath = newFilePath + "/" + newFileName;

        // 파일 경로 생성
        makeDirectory(rootFilePath);

        // 파일 업로드 수행
        try {
            file.transferTo(new File(rootFilePath, savedName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savedName;
    }

    private static void makeDirectory(String rootPath) {
        File f = new File(rootPath);
        if(!f.exists()) f.mkdirs();
    }
}
