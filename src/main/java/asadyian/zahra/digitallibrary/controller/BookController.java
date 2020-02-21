package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.AttachmentResponse;
import asadyian.zahra.digitallibrary.controller.model.BookResponse;
import asadyian.zahra.digitallibrary.controller.model.library.LibraryResponse;
import asadyian.zahra.digitallibrary.domain.entities.FileEntity;
import asadyian.zahra.digitallibrary.service.BookService;
import asadyian.zahra.digitallibrary.service.FileService;
import asadyian.zahra.digitallibrary.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService service;
    private final LibraryService libraryService;
    private final FileService fileService;

    @GetMapping("/content/{library}/library")
    public List<BookResponse> fetchAllContent(@PathVariable("library") Long library) {
        return service.fetchAllBooks(library);
    }

    @GetMapping("/content/{contentId}")
    public BookResponse content(@PathVariable("contentId") Long contentId) {
        return service.loadContent(contentId);
    }

    @GetMapping("/libraries")
    public List<LibraryResponse> fetchAllLibrary() {
        return libraryService.fetchAllByPagination();
    }

    @GetMapping("/content/{contentId}/attachment")
    public List<AttachmentResponse> fetchAllAttachment(@PathVariable("contentId") Long contentId) {
        return service.fetchAttachment(contentId);
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
