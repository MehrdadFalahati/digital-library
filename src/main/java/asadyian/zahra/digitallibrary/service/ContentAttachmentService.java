package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentRequest;
import asadyian.zahra.digitallibrary.controller.model.contentAttachment.ContentAttachmentResponse;
import asadyian.zahra.digitallibrary.domain.entities.ContentAttachmentEntity;
import asadyian.zahra.digitallibrary.domain.repository.ContentAttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<ContentAttachmentResponse> fetchAllByContentId(Long contentId) {
        List<ContentAttachmentEntity> contentAttachmentPage = repository.findAllByContentId(contentId);
        return contentAttachmentPage.stream().map(ContentAttachmentEntity::convert2response).collect(Collectors.toList());
    }

    public ContentAttachmentResponse loadById(Long id) {
        return repository.findById(id)
                .map(ContentAttachmentEntity::convert2response)
                .orElseThrow(()->new RuntimeException("Can not find entity"));
    }
}
