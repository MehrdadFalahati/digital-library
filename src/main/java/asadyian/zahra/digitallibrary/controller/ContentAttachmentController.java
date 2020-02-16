package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentRequest;
import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentResponse;
import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.service.ContentAttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content-attachment")
public class ContentAttachmentController {
    private final ContentAttachmentService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-update")
    public ContentAttachmentResponse saveOrUpdate(@RequestBody ContentAttachmentRequest request) {
        return service.saveOrUpdate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.removeById(id);
    }

    @GetMapping("/load/{id}")
    public ContentAttachmentEntity load(@PathVariable("id") Long id) {
        return service.loadById(id);
    }

    @GetMapping("/content-types/{contentId}/content")
    public Page<ContentAttachmentResponse> fetchContentTypes(@PathVariable("contentId") Long contentId,
                                                   @PageableDefault Pageable pageable) {
        return service.fetchAllByContentId(contentId, pageable);
    }
}
