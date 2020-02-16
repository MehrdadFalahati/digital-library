package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.exception.BadRequestException;
import asadyian.zahra.digitallibrary.controller.model.AuthResponse;
import asadyian.zahra.digitallibrary.controller.model.LoginRequest;
import asadyian.zahra.digitallibrary.controller.model.UserModel;
import asadyian.zahra.digitallibrary.security.TokenProvider;
import asadyian.zahra.digitallibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity registerUser(@Valid @RequestBody UserModel signUpRequest) {
        if(userService.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username is already taken!");
        }

        if(userService.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        userService.registerUser(signUpRequest);

        LoginRequest login = new LoginRequest();
        login.setPassword(signUpRequest.getPassword());
        login.setUsernameOrEmail(Optional.ofNullable(signUpRequest.getUsername()).orElse(signUpRequest.getEmail()));

        return authenticateUser(login);
    }

}
