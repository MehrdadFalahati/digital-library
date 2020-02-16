package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.attachmenttype.AttachmentTypeRequest;
import asadyian.zahra.digitallibrary.controller.model.attachmenttype.AttachmentTypeResponse;
import asadyian.zahra.digitallibrary.service.AttachmentTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attachment-type")
public class AttachmentTypeController {
    private final AttachmentTypeService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-update")
    public AttachmentTypeResponse saveOrUpdate(@RequestBody AttachmentTypeRequest request) {
        return service.saveOrUpdate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.removeById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/load/{id}")
    public AttachmentTypeResponse load(@PathVariable("id") Long id) {
        return service.loadById(id);
    }

    @GetMapping("/attachment-types")
    public List<AttachmentTypeResponse> fetchLibraries() {
        return service.fetchAll();
    }

}
