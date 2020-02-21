package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.AttachmentResponse;
import asadyian.zahra.digitallibrary.controller.model.BookResponse;
import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.entities.feedback.EmotionType;
import asadyian.zahra.digitallibrary.domain.repository.ContentAttachmentRepository;
import asadyian.zahra.digitallibrary.domain.repository.ContentRepository;
import asadyian.zahra.digitallibrary.service.feedback.EmotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final EmotionHandler emotionHandler;
    private final ContentRepository contentRepository;
    private final ContentAttachmentRepository attachmentRepository;

    @Transactional(readOnly = true)
    public List<BookResponse> fetchAllBooks(Long libraryId) {
        if (libraryId == null) {
            List<ContentEntity> contents = contentRepository.findAll();
            return createBookResponse(contents);

        }
        List<ContentEntity> contents = contentRepository.findAllByLibraryId(libraryId);
        return createBookResponse(contents);
    }

    private List<BookResponse> createBookResponse(List<ContentEntity> contents) {
        List<BookResponse> responses = new LinkedList<>();
        for (ContentEntity content : contents) {
            BookResponse book = getBookResponse(content);
            responses.add(book);
        }
        return responses;
    }

    private BookResponse getBookResponse(ContentEntity content) {
        BookResponse book = new BookResponse();
        book.setId(content.getId());
        book.setName(content.getName());
        book.setDescription(content.getDescription());
        String fileDownloadUri = getFileDownloadUri(content.getContentData().getId());
        book.setImageUrl(fileDownloadUri);
        long countLike = emotionHandler.countByType(content.getId(), EmotionType.LIKE);
        book.setCountLike(countLike);
        book.setRate(content.getRate());
        return book;
    }

    private String getFileDownloadUri(String file) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/books/downloadFile/")
                .path(file)
                .toUriString();
    }

    @Transactional(readOnly = true)
    public BookResponse loadContent(Long contentId) {
        return contentRepository.findById(contentId).map(this::getBookResponse)
                .orElseThrow(()-> new RuntimeException("Can not Find Content"));
    }

    @Transactional(readOnly = true)
    public List<AttachmentResponse> fetchAttachment(Long contentId) {
        List<AttachmentResponse> responses = new LinkedList<>();
        List<ContentAttachmentEntity> attachments = attachmentRepository.findAllByContentId(contentId);
        for (ContentAttachmentEntity attachment: attachments) {
            AttachmentResponse response = new AttachmentResponse();
            response.setId(attachment.getId());
            response.setName(attachment.getName());
            response.setFileUrl(getFileDownloadUri(attachment.getAttachmentData().getId()));
            responses.add(response);
        }
        return responses;
    }
}
