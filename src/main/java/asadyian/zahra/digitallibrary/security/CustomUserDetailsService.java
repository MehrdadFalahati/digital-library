package asadyian.zahra.digitallibrary.security;

import asadyian.zahra.digitallibrary.domain.entities.UserEntity;
import asadyian.zahra.digitallibrary.exception.ResourceNotFoundException;
import asadyian.zahra.digitallibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        UserEntity user = Optional.ofNullable(userService.searchByUsernameOrEmail(usernameOrEmail, usernameOrEmail))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String id) {
        UserEntity user = Optional.ofNullable(userService.singleUser(id)).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
}
