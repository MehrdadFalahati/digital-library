package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.content.ContentRequest;
import asadyian.zahra.digitallibrary.controller.model.content.ContentResponse;
import asadyian.zahra.digitallibrary.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/content")
public class ContentController {
    private final ContentService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-update")
    public ContentResponse saveOrUpdate(@RequestBody ContentRequest request) {
        return service.saveOrUpdate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.removeById(id);
    }

    @GetMapping("/load/{id}")
    public ContentResponse load(@PathVariable("id") Long id) {
        return service.loadById(id);
    }

    @GetMapping("/contents")
    public List<ContentResponse> fetchContents() {
        return service.fetchAllPage();
    }
}
