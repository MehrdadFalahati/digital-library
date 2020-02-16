package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.ItemOption;
import asadyian.zahra.digitallibrary.controller.model.library.LibraryRequest;
import asadyian.zahra.digitallibrary.controller.model.library.LibraryResponse;
import asadyian.zahra.digitallibrary.domain.entities.LibraryEntity;
import asadyian.zahra.digitallibrary.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-update")
    public LibraryResponse saveOrUpdate(@RequestBody LibraryRequest request) {
        return service.saveOrUpdate(request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/remove/{id}")
    public void remove(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/load/{id}")
    public LibraryEntity load(@PathVariable("id") Long id) {
        return service.loadById(id);
    }

    @GetMapping("/libraries")
    public List<LibraryResponse> fetchLibraries() {
        return service.fetchAllByPagination();
    }

    @GetMapping("/search")
    public List<ItemOption> search(@RequestParam("title") String title) {
        return service.searchByTitle(title);
    }


}
