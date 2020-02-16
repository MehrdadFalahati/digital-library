package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.content.ContentRequest;
import asadyian.zahra.digitallibrary.controller.model.content.ContentResponse;
import asadyian.zahra.digitallibrary.domain.entities.ContentEntity;
import asadyian.zahra.digitallibrary.domain.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository repository;

    @Transactional
    public ContentResponse saveOrUpdate(ContentRequest request) {
        ContentEntity result = repository.save(request.convert2content());
        return ContentEntity.convert2response(result);
    }

    @Transactional(readOnly = true)
    public List<ContentResponse> fetchAllPage() {
        List<ContentEntity> all = repository.findAll();
        return all.stream().map(ContentEntity::convert2response).collect(Collectors.toList());
    }

    @Transactional
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ContentResponse loadById(Long id) {
        return repository.findById(id)
                .map(ContentEntity::convert2response)
                .orElseThrow(() -> new RuntimeException("can not find content"));
    }

    @Transactional(readOnly = true)
    public ContentEntity findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("can not find content"));
    }

}
