package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentRequest;
import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentResponse;
import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.domain.repository.ContentAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContentAttachmentService {
    private final ContentAttachmentRepository repository;

    @Transactional
    public ContentAttachmentResponse saveOrUpdate(ContentAttachmentRequest request) {
        ContentAttachmentEntity result = repository.save(request.convert2attachment());
        return ContentAttachmentEntity.convert2response(result);
    }

    @Transactional
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ContentAttachmentResponse> fetchAllByContentId(Long contentId, Pageable pageable) {
        Page<ContentAttachmentEntity> contentAttachmentPage = repository.findAllByContentId(contentId, pageable);
        return contentAttachmentPage.map(ContentAttachmentEntity::convert2response);
    }

    public ContentAttachmentEntity loadById(Long id) {
        return repository.findById(id)
                .orElseThrow(()->new RuntimeException("Can not find entity"));
    }
}
