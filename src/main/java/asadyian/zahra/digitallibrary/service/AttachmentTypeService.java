package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.attachmenttype.AttachmentTypeRequest;
import asadyian.zahra.digitallibrary.controller.model.attachmenttype.AttachmentTypeResponse;
import asadyian.zahra.digitallibrary.domain.entities.AttachmentTypeEntity;
import asadyian.zahra.digitallibrary.domain.repository.AttachmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentTypeService {

    private final AttachmentTypeRepository repository;

    @Transactional
    public AttachmentTypeResponse saveOrUpdate(AttachmentTypeRequest request) {
        AttachmentTypeEntity result = repository.save(request.convert2AttachmentType());
        return AttachmentTypeEntity.convert2response(result);
    }

    @Transactional
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public AttachmentTypeResponse loadById(Long id) {
        return repository.findById(id).map(AttachmentTypeEntity::convert2response)
                .orElseThrow(() -> new RuntimeException("can not find entity"));
    }

    @Transactional(readOnly = true)
    public List<AttachmentTypeResponse> fetchAll() {
        return repository.findAll().stream().map(AttachmentTypeEntity::convert2response).collect(Collectors.toList());
    }
}
