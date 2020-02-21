package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.ItemOption;
import asadyian.zahra.digitallibrary.controller.model.library.LibraryRequest;
import asadyian.zahra.digitallibrary.controller.model.library.LibraryResponse;
import asadyian.zahra.digitallibrary.domain.entities.LibraryEntity;
import asadyian.zahra.digitallibrary.domain.repository.LibraryRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final LibraryRepository repository;

    @Transactional
    public LibraryResponse saveOrUpdate(LibraryRequest request) {
        Assert.notNull(request, "give request is null");
        LibraryEntity result = repository.save(request.convert2Library());
        return LibraryEntity.convert2Responce(result);
    }

    @Transactional
    public void deleteById(Long id) {
        Assert.notNull(id, "id request is null");
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public LibraryEntity loadById(Long id) {
        Assert.notNull(id, "id request is null");
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Library[id=" + id + "] dose not exists"));
    }

    @Transactional(readOnly = true)
    public List<LibraryResponse> fetchAllByPagination() {
        return repository.findAll().stream().map(LibraryEntity::convert2Responce).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ItemOption> searchByTitle(String title) {
        if (title == null || title.isEmpty()) {
            return repository.findAll().stream().map(l -> new ItemOption(l.getId(), l.getTitle())).collect(Collectors.toList());
        }
        return repository.findByTitleContaining(title).stream().map(l -> new ItemOption(l.getId(), l.getTitle())).collect(Collectors.toList());
    }
}
