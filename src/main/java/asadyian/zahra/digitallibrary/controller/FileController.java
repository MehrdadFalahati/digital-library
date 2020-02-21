package asadyian.zahra.digitallibrary.controller;


import asadyian.zahra.digitallibrary.controller.model.FileResponse;
import asadyian.zahra.digitallibrary.controller.model.MultipartFileModel;
import asadyian.zahra.digitallibrary.controller.model.UploadFileResponse;
import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import asadyian.zahra.digitallibrary.exception.BadRequestException;
import asadyian.zahra.digitallibrary.exception.FileStorageException;
import asadyian.zahra.digitallibrary.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

    @PostMapping("user/{id}/upload-image")
    public UploadFileResponse uploadImageUser(@RequestParam("file") MultipartFile file,@PathVariable("id") String userId) {
        if (userId == null) {
            log.error("user id is null");
            throw new BadRequestException("Please check userId, userId is null");
        }
        try {
            MultipartFileModel fileModel = new MultipartFileModel(file);
            FileEntity entity = fileService.storeImageUser(fileModel, userId);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/downloadFile/")
                    .path(entity.getId())
                    .toUriString();
            return UploadFileResponse.builder()
                    .fileDownloadUri(fileDownloadUri)
                    .fileName(entity.getFileName())
                    .fileType(file.getContentType())
                    .size(file.getSize())
                    .build();
        } catch (IOException e) {
            log.error("throw I/O Exception", e);
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", e);
        }

    }

    @PostMapping("/upload-file")
    public FileResponse upload(@RequestParam("file") MultipartFile file) {
        try {
            MultipartFileModel fileModel = new MultipartFileModel(file);
            FileEntity entity = fileService.store(fileModel);
            return FileResponse.builder()
                    .id(entity.getId())
                    .fileName(entity.getFileName())
                    .build();
        } catch (IOException e) {
            log.error("throw I/O Exception", e);
            throw new FileStorageException("Could not store file " + file.getOriginalFilename() + ". Please try again!", e);
        }

    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        FileEntity dbFile = fileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }


}
