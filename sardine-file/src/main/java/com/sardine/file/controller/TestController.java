package com.sardine.file.controller;

import com.sardine.common.entity.http.Result;
import com.sardine.common.entity.http.Results;
import com.sardine.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author keith
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {

    @Value("${server.port}")
    String serverPort;

    @PostMapping("upload")
    public Result<Void> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty())
            return Results.failed("File is empty, please choose a file");
        String filename = file.getOriginalFilename();
        String filepath = "D:/";
        File dest = new File(filepath + filename);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw SystemException.of("upload failed");
        }
        return Results.success("upload success");
    }

    @PostMapping("/download")
    public ResponseEntity<byte[]> download(String filename) {
        String fileFullPath = "D:/" + filename;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileFullPath);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "temp.xlsx");
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            throw SystemException.of("文件下载异常");
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                log.error("close file output stream exception", e);
            }
        }
    }

    @GetMapping("hello")
    public Result<String> hello(){
        return Results.success("成功",serverPort);
    }
}
