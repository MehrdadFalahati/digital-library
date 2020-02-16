package asadyian.zahra.digitallibrary.controller;

import asadyian.zahra.digitallibrary.controller.model.ApiResponse;
import asadyian.zahra.digitallibrary.controller.model.UserModel;
import asadyian.zahra.digitallibrary.controller.model.UserSummery;
import asadyian.zahra.digitallibrary.security.CurrentUser;
import asadyian.zahra.digitallibrary.security.UserPrincipal;
import asadyian.zahra.digitallibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/current")
    public UserSummery getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.loadUserSummery(userPrincipal.getId());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/update-profile")
    public ResponseEntity<ApiResponse> updateProfile(UserPrincipal userPrincipal, UserModel userModel) {
        userService.updateProfile(userPrincipal.getId(), userModel);
        return ResponseEntity.ok()
                .body(new ApiResponse(true, "User updated successfully@"));
    }
}
