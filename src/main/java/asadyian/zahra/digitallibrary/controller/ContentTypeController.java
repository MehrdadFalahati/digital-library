package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.ItemOption;
import asadyian.zahra.digitallibrary.controller.model.contenttype.ContentTypeRequest;
import asadyian.zahra.digitallibrary.domain.entities.ContentTypeEntity;
import asadyian.zahra.digitallibrary.service.ContentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content-type")
public class ContentTypeController {
    private final ContentTypeService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-update")
    public ContentTypeEntity saveOrUpdate(@RequestBody ContentTypeRequest request) {
        return service.saveOrUpdate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/load/{id}")
    public ContentTypeEntity load(@PathVariable("id") Long id) {
        return service.loadById(id);
    }

    @GetMapping("/content-types")
    public List<ContentTypeEntity> fetchContentTypes() {
        return service.fetchAllByPagination();
    }

    @GetMapping("/search")
    public List<ItemOption> search(@RequestParam("title") String title) {
        return service.searchByTitle(title);
    }


}
