package com.yapp.web1.controller;

import com.yapp.web1.common.AuthUtils;
import com.yapp.web1.dto.res.FileUploadResponseDto;
import com.yapp.web1.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * File Controller
 *
 * @author Dakyung Ko
 * @version 1.6
 * @since 0.0.4
 */
@CrossOrigin("*")
@RequestMapping("/api")
@RestController
@Api(tags = "파일 APIs")
public class FileController {

    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 프로젝트 완료용 파일 업로드
     *
     * @param projectIdx 파일업로드 할 프로젝트 idx
     * @param file 업로드할 파일
     *
     * @throws IOException
     * @see /api/file/{projectIdx}
     */
    @PostMapping("/file/{projectIdx}")
    @ApiOperation(value = "파일 업로드", produces = "multipart/form-data")
    public ResponseEntity<?> fileUpload(@PathVariable @ApiParam(value = "완료할 projectIdx", example = "1") final Long projectIdx,
                                        @RequestParam("file") MultipartFile file) {
        try {
            FileUploadResponseDto uploadedFile = fileService.fileUpload(file, projectIdx, AuthUtils.getCurrentAccount().getIdx());
            return new ResponseEntity<>(uploadedFile, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
