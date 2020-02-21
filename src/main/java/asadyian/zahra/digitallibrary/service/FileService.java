package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.domain.repository.FileRepository;
import asadyian.zahra.digitallibrary.exception.FileStorageException;
import asadyian.zahra.digitallibrary.exception.MyFileNotFoundException;
import asadyian.zahra.digitallibrary.controller.model.MultipartFileModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repository;
    private final UserService userService;

    @Transactional(readOnly = true)
    public FileEntity getFile(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + id));
    }

    @Transactional
    public FileEntity storeImageUser(MultipartFileModel file, String userId) {
        UserEntity user = userService.singleUser(userId);
        if (user == null) {
            log.error("user is null");
            throw new FileStorageException("Sorry! User Not Found!");
        }
        if (file.getFileName().contains("..")) {
            log.error("filename is invalid");
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file.getFileName());
        }
        FileEntity entity = FileEntity.builder()
                .fileName(file.getFileName())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .build();
        FileEntity fileEntity = repository.save(entity);
        log.info("save FileEntity[id={}]", fileEntity.getId());
        user.setImage(fileEntity);
        userService.saveOrUpdate(user);
        log.info("add FileEntity[id={}] to UserEntity[id={}]", fileEntity.getId(), userId);
        return fileEntity;

    }

    @Transactional
    public FileEntity store(MultipartFileModel file) {
        if (file.getFileName().contains("..")) {
            log.error("filename is invalid");
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file.getFileName());
        }
        FileEntity entity = FileEntity.builder()
                .fileName(file.getFileName())
                .fileType(file.getContentType())
                .data(file.getBytes())
                .build();
        FileEntity fileEntity = repository.saveAndFlush(entity);
        log.info("save FileEntity[id={}]", fileEntity.getId());
        return fileEntity;
    }
}
