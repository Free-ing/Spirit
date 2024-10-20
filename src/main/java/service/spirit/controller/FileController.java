package service.spirit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/v1/file")
public class FileController {

    @PostMapping(value = "upload")
    public Object upload(@RequestParam(value = "file") MultipartFile file,
                         @RequestPart(value = "dto") dto.testDto dto) throws Exception {

        System.out.println(dto.getName());
        log.info("file => {}", file.getOriginalFilename());
        return "성공이다.";
    }
}