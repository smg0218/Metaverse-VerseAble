package com.deeppoem.verseable.api.result.controller;

import com.deeppoem.verseable.api.result.dto.request.ResultRequestDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseDTO;
import com.deeppoem.verseable.api.result.dto.response.ResultResponseMessageDTO;
import com.deeppoem.verseable.api.result.service.ResultService;
import com.deeppoem.verseable.api.user.dto.response.LoginResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getResultList(@RequestParam Long resultId) {
        log.info("/api/result : GET");
        log.info("userId : {}", resultId);

        ResultResponseDTO responseDTO = resultService.getResult(resultId);

        return ResponseEntity.ok().body(responseDTO);
    }

    @ResponseBody
    @PostMapping()
    public ResponseEntity<?> postResult(@RequestBody ResultRequestDTO requestDTO, BindingResult result) {
        log.info("/api/result : POST");
        log.info("requestDTO : {}", requestDTO);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(new LoginResponseDTO(
                    result.getAllErrors().get(0).getDefaultMessage()
            ));
        }

        ResultResponseMessageDTO resultDTO = resultService.addResult(requestDTO);

        return ResponseEntity.ok().body(resultDTO);
    }
}
