package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.ItemOption;
import asadyian.zahra.digitallibrary.controller.model.contenttype.ContentTypeRequest;
import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import asadyian.zahra.digitallibrary.domain.repository.ContentTypeRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentTypeService {
    private final ContentTypeRepository repository;

    @Transactional
    public ContentTypeEntity saveOrUpdate(ContentTypeRequest request) {
        Assert.notNull(request, "send request is null");
        return repository.save(request.convert2ContentType());
    }

    @Transactional(readOnly = true)
    public ContentTypeEntity loadById(Long id) {
        Assert.notNull(id, "id request is null");
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ContentType[id=" + id + "] dose not exists"));
    }

    public List<ItemOption> searchByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return repository.findAll().stream().map(c -> new ItemOption(c.getId(), c.getTitle())).collect(Collectors.toList());
        }
        return repository.findByTitleContaining(title).stream().map(c -> new ItemOption(c.getId(), c.getTitle())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ContentTypeEntity> fetchAllByPagination() {
        return repository.findAll();
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
