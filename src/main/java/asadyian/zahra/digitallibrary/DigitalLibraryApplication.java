package asadyian.zahra.digitallibrary;

import asadyian.zahra.digitallibrary.config.AppProperties;
import asadyian.zahra.digitallibrary.domain.entities.RoleEntity;
import asadyian.zahra.digitallibrary.domain.entities.RoleName;
import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.domain.repository.RoleRepository;
import asadyian.zahra.digitallibrary.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        DigitalLibraryApplication.class,
        Jsr310JpaConverters.class
})
@EnableConfigurationProperties(AppProperties.class)
@EnableJpaAuditing
public class DigitalLibraryApplication {

    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DigitalLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner addRoleData(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            addRoles(roleRepository);
            addAdmin(roleRepository, userRepository, encoder);
        };
    }

    private void addRoles(RoleRepository roleRepository) {
        List<RoleEntity> roles = roleRepository.findAll();
        if (CollectionUtils.isEmpty(roles)) {
            roleRepository.save(RoleEntity.builder().name(RoleName.ROLE_USER).build());
            roleRepository.save(RoleEntity.builder().name(RoleName.ROLE_ADMIN).build());
        }
    }

    private void addAdmin(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        List<UserEntity> users = userRepository.findAll();
        if (CollectionUtils.isEmpty(users)) {
            UserEntity admin = UserEntity.builder()
                    .firstName("زهرا")
                    .lastName("اسدیان")
                    .email("zahraasadiyan76@gmail.com")
                    .password(encoder.encode("root1234"))
                    .username("zahraasadiyan76")
                    .build();
            RoleEntity roleAdmin = roleRepository.findByName(RoleName.ROLE_ADMIN);
            RoleEntity roleUser = roleRepository.findByName(RoleName.ROLE_USER);
            admin.addRole(roleAdmin);
            admin.addRole(roleUser);
            userRepository.save(admin);
        }
    }

}
