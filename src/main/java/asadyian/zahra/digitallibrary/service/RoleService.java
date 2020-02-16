package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.domain.entities.RoleEntity;
import asadyian.zahra.digitallibrary.domain.entities.RoleName;
import asadyian.zahra.digitallibrary.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    @Transactional(readOnly = true)
    public RoleEntity singleRoleByName(RoleName roleName) {
        return repository.findByName(roleName);
    }
}
