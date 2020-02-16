package asadyian.zahra.digitallibrary.service;

import asadyian.zahra.digitallibrary.controller.model.UserModel;
import asadyian.zahra.digitallibrary.controller.model.UserSummery;
import asadyian.zahra.digitallibrary.domain.entities.RoleEntity;
import asadyian.zahra.digitallibrary.domain.entities.RoleName;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    @Transactional
    public UserEntity saveOrUpdate(UserEntity user) {
        Assert.notNull(user, "The UserEntity must not be null!");
        if (CollectionUtils.isEmpty(user.getRoles())) {
            RoleEntity userRole = roleService.singleRoleByName(RoleName.ROLE_USER);
            user.addRole(userRole);
            log.debug("add Role:{}  to UserEntity", userRole);
        }
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public UserEntity singleUser(String id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public UserEntity registerUser(UserModel user) {
        UserEntity entity = UserEntity.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return this.saveOrUpdate(entity);
    }

    @Transactional(readOnly = true)
    public UserEntity searchByUsernameOrEmail(String username, String email) {
        return repository.findByEmailOrUsername(email, username);
    }

    @Transactional(readOnly = true)
    public Boolean existsByUsername(String username) {
        Assert.notNull(username, "The Username must not be null!");
        Assert.hasText(username, "The Username must be Text!");
        return repository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email) {
        Assert.notNull(email, "The email must not be null!");
        Assert.hasText(email, "The email must be Text!");
        return repository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserSummery loadUserSummery(String id)  {
        UserEntity userEntity = singleUser(id);
        return UserSummery.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .build();
    }

    @Transactional
    public void updateProfile(String id, UserModel userModel) {
        UserEntity user = singleUser(id);
        user.setEmail(userModel.getEmail());
        user.setUsername(userModel.getUsername());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setPassword(userModel.getPassword());
        saveOrUpdate(user);
    }

    @Transactional(readOnly = true)
    public List<UserEntity> loadByIds(Set<String> ids) {
        return repository.findAllByIdIn(ids);
    }
}
