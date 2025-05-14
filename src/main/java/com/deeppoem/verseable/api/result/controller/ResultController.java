package com.deeppoem.verseable.api.result.controller;

import com.deeppoem.verseable.api.result.dto.request.ResultRequestDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseMessageDTO;
import com.deeppoem.verseable.api.result.service.ResultService;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/api/result")
public class ResultController {
    private static final Logger log = LoggerFactory.getLogger(ResultController.class);
    private final ResultService resultService;

    @Autowired
    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @ResponseBody
    @GetMapping()
    public ResponseEntity<?> getResult(@RequestParam Long resultId,
                                       HttpServletRequest request) {
        log.info("/api/result : GET");
        log.info("userId : {}", resultId);

        ResultResponseMessageDTO responseDTO = resultService.getResult(resultId, request);

        return ResponseEntity.ok().body(responseDTO);
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<?> postResult(@RequestParam("id") String id,
                                        @RequestParam("file") MultipartFile multipartFile,
                                        HttpServletRequest request) {
        log.info("/api/result : POST");
        log.info("requestDTO : {}", id);

        if(multipartFile == null || multipartFile.isEmpty()) {
            return ResponseEntity.badRequest().body(new ResultResponseMessageDTO("파일은 필수입니다!"));
        }

        try {
            ResultResponseMessageDTO resultDTO = resultService.addResult(id, multipartFile, request);
            return ResponseEntity.ok().body(resultDTO);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ResultResponseMessageDTO(e.getMessage()));
        }
    }

    @GetMapping("/share")
    public String getResultSite(@RequestParam Long resultId,
                                Model model, HttpServletRequest request) {
        log.info("/api/result/share : GET");
        log.info("resultId : {}", resultId);

        ResultResponseMessageDTO responseDTO = resultService.getResult(resultId, request);

        model.addAttribute("result", responseDTO);

        return "result";
    }
}